package com.mg.Association_Flows.payment.service;

import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.association.service.AssociationService;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.service.AssociationSlotService;
import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.payment.domain.repo.PaymentRepository;
import com.mg.Association_Flows.payment.enums.PaymentStatus;
import com.mg.Association_Flows.payment.mapper.PaymentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AssociationSlotService associationSlotService;
    private final AssociationService associationService;

    public PaymentDto requestToPay(PaymentDto paymentDto) {
        UUID associationId = null;
        if (paymentDto.getAssociationSlot().getId() != null) {
            associationId = associationSlotService.getAssociationSlotById(paymentDto.getAssociationSlot().getId()).getAssociation().getId();
        }else
            throw new IllegalArgumentException("Association slot not found");

        BigDecimal numberOfMonthPay = associationService.monthlyAmountOfAssociation(associationId);

        // if the amount that come in request after divid it  to numberOfMonthPay it has any fraction
        // then it pay number less than the numberOfMonthPay or if pay 3 month for example
        // then it will pay it well because if i divid it then the number will be good no fraction

        // i need to check if amount that come not less than , so it should pay the
        // monthly amount or multiplies of monthly amount
        boolean dividesEvenly = paymentDto.getAmount().remainder(numberOfMonthPay).compareTo(BigDecimal.ZERO) == 0;
        if (!dividesEvenly)
            throw new RuntimeException("you should pay the monthly amount or multiplies");

        boolean isAssociationActive = associationService.statusOfAssociation(associationId) == AssociationStatus.ACTIVE;

        if(!isAssociationActive)
            throw new RuntimeException("this association is not active");


        LocalDate dateOfPay = paymentDto.getPaymentDate().toLocalDateTime().toLocalDate();
        LocalDate startDataOfAssociation = associationService.startDateOfAssociation(associationId);
        LocalDate endDataOfAssociation = associationService.endDateOfAssociation(associationId);

        if(!dateOfPay.isAfter(startDataOfAssociation) || !dateOfPay.isBefore(endDataOfAssociation))
            throw  new RuntimeException("date of payment request should be between start date & end date of association");


        Payment payment = paymentMapper.mapToEntity(paymentDto);
        paymentRepository.save(payment);
        return paymentDto;
    }

    @Transactional
    public boolean distributeMonthlyPayout(UUID associationId, int turnOrder,LocalDate currentDataOfMonth) {

        try {
            AssociationSlotDto targetSlot = associationSlotService
                    .findByAssociationIdAndTurnOrder(associationId, turnOrder);

            if (targetSlot.getIsPayoutDone()) {
                throw new RuntimeException("this order is already payout");
            }

            BigDecimal totalAmountOfAssociation = associationService.totalPoolAmountOfAssociation(associationId);
            BigDecimal totalAmountPayedUntilNow = paymentRepository.calculateAvailablePool(associationId);

            if (totalAmountOfAssociation.compareTo(totalAmountPayedUntilNow) < 0)
                throw new RuntimeException("the total number of payed until now not covered to payout");

            associationSlotService.markSlotAsPaidOut(targetSlot.getId());

            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<PaymentDto> getListOfPaymentToConfirmedByAssociationId(UUID associationId) {
        Optional<List<Payment>> payments = paymentRepository.getAllPaymentByAssociationId(associationId);
        if (payments.isPresent()) {
            return payments.get().stream()
                    .map(paymentMapper::mapToDTO)
                    .toList();
        }
        throw new RuntimeException("There is no payment with association id " + associationId);
    }

    @Transactional
    public boolean confirmPayment(UUID paymentId,PaymentDto paymentDto) {
        // should update confirmation_date , status of payment , optional to add note
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            paymentDto.setStatus(PaymentStatus.CONFIRMED); // if not sent but i think it should send it
            paymentMapper.updatePaymentFromDto(paymentDto, payment.get());

            BigDecimal numberOfMonthsPay = associationService.monthlyAmountOfAssociation(payment.get()
                    .getAssociationSlot().
                    getAssociation().
                    getId());

            Integer numberOfMonthsPaidToSubtractFromRemaining = payment.get().getAmount().divide(numberOfMonthsPay, RoundingMode.DOWN).toBigInteger().intValue();

            boolean slotUpdated = associationSlotService.
                    updateAssociationSlot(payment.get().getAssociationSlot().getId(),
                            payment.get().getAmount(),
                            numberOfMonthsPaidToSubtractFromRemaining);


            paymentRepository.save(payment.get());
            return true;
        }
        throw new RuntimeException("There is no payment with this id " + paymentId);
    }
}
