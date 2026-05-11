package com.mg.Association_Flows.payment.service;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.association.exception.AssociationNotActiveException;
import com.mg.Association_Flows.association.exception.AssociationNotFoundException;
import com.mg.Association_Flows.association.service.AssociationService;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.exception.AssociationSlotNotFoundException;
import com.mg.Association_Flows.associationSlot.exception.AssociationSlotPayoutOrderException;
import com.mg.Association_Flows.associationSlot.service.AssociationSlotService;
import com.mg.Association_Flows.installment.service.InstallmentService;
import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.payment.domain.repo.PaymentRepository;
import com.mg.Association_Flows.payment.enums.PaymentStatus;
import com.mg.Association_Flows.payment.exception.*;
import com.mg.Association_Flows.payment.mapper.PaymentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final InstallmentService  installmentService;

    public PaymentDto requestToPay(PaymentDto paymentDto) {
        UUID associationId = null;
        if (paymentDto.getAssociationSlot().getId() != null) {
            associationId = associationSlotService.getAssociationSlotById(paymentDto.getAssociationSlot().getId()).getAssociation().getId();
        }else
            throw new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found");

        AssociationDto foundedAssociation = associationService.findAssociationById(associationId);
        BigDecimal monthlyAmountOfAssociation = foundedAssociation.getMonthlyAmount();

        // if the amount that come in request after divid it  to monthlyAmountOfAssociation it has any fraction
        // then it pay number less than the monthlyAmountOfAssociation or if pay 3 month for example
        // then it will pay it well because if i divid it then the number will be good no fraction

        // i need to check if amount that come not less than , so it should pay the
        // monthly amount or multiplies of monthly amount
        boolean dividesEvenly = paymentDto.getAmount().remainder(monthlyAmountOfAssociation).compareTo(BigDecimal.ZERO) == 0;
        if (!dividesEvenly)
            throw new PayMonthlyAmountException("you should pay the monthly amount or multiplies",HttpStatus.BAD_REQUEST,"Payment Amount Not Eligible");

        boolean isAssociationActive = foundedAssociation.getStatus() == AssociationStatus.ACTIVE;

        if(!isAssociationActive)
            throw new AssociationNotActiveException("this association is not active",HttpStatus.BAD_REQUEST,"Association Not Active");


        LocalDate dateOfPay = paymentDto.getPaymentDate().toLocalDateTime().toLocalDate();
        LocalDate startDataOfAssociation = foundedAssociation.getStartDate();
        LocalDate endDataOfAssociation = foundedAssociation.getEndDate();

        if(dateOfPay.isEqual(startDataOfAssociation));

        else if(!dateOfPay.isAfter(startDataOfAssociation) || !dateOfPay.isBefore(endDataOfAssociation))
            throw  new PaymentDateException("date of payment request should be between start date & end date of association",HttpStatus.BAD_REQUEST,"Payment Date Not Eligible");


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
                throw new AssociationSlotPayoutOrderException("this order is already payout",HttpStatus.BAD_REQUEST,"Payment Order Already Payout");
            }

            AssociationDto foundedAssociation = associationService.findAssociationById(associationId);


            BigDecimal totalAmountOfAssociation = foundedAssociation.getTotalPoolAmount();
            /*
             we replace this by the new column in association called currentCollectBalance
             that will accumulate in it the balance paid until now and after make payout
             then will reset this row by find the association record and reset it to zero again
            BigDecimal totalAmountPayedUntilNow = paymentRepository.calculateAvailablePool(associationId);
            */

            BigDecimal currentCollectBalanceUntilNow = foundedAssociation.getCurrentCollectedBalance();
            if (currentCollectBalanceUntilNow.compareTo(totalAmountOfAssociation) < 0)
                throw new CollectedBalanceException("the total number of payed until now not covered to payout","Collected Balance Not Available");


            int updatedRows = associationService.deductPayoutFromBalance(associationId, totalAmountOfAssociation);

            if (updatedRows == 0) {
                throw new RuntimeException("there is something went wrong");
            }

            associationSlotService.markSlotAsPaidOut(targetSlot.getId());

            // here we reset again the current balance to be zero
            //associationService.reAccumulateCurrentBalanceCollected(associationId);

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
        throw new AssociationNotFoundException("There is no payment with association id " + associationId,HttpStatus.BAD_REQUEST,"Payment Not Found");
    }

    @Transactional
    public boolean confirmPayment(UUID paymentId,PaymentDto paymentDto) {
        // should update confirmation_date , status of payment , optional to add note
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            if(paymentDto.getStatus().equals(PaymentStatus.CONFIRMED)) {
                paymentDto.setStatus(PaymentStatus.CONFIRMED); // if not sent, but i think it should send it
                paymentMapper.updatePaymentFromDto(paymentDto, payment.get());

                UUID assocId = payment.get()
                        .getAssociationSlot().
                        getAssociation().
                        getId();

                UUID assocSlotId = payment.get().getAssociationSlot().getId();
                BigDecimal amountReceived = payment.get().getAmount();
                BigDecimal numberOfMonthsPay = payment.get().getAssociationSlot().getAssociation().getMonthlyAmount();

                Integer numberOfMonthsPaidToSubtractFromRemaining = amountReceived
                        .divide(numberOfMonthsPay, RoundingMode.DOWN).toBigInteger().intValue();

                 associationSlotService.
                        updateAssociationSlot(assocSlotId,amountReceived, numberOfMonthsPaidToSubtractFromRemaining);


                // update the current collected balance in association table by
                // the amount that confirmed
                // when user payout then it should subtract from this record the total pool amount
                // that user will pay again to start from beginning

                associationService.updateCurrentBalanceCollected(assocId, amountReceived);


                installmentService.allocateMonthsPaid(assocSlotId,payment.get().getAmount(),payment.get().getTargetMonth());


                paymentRepository.save(payment.get());
                return true;
            }else
                throw new PaymentConfirmedException("something went wrong payment not confirmed","Payment Not Confirmed");
        }

        throw new PaymentNotFoundException("There is no payment with this id " + paymentId,"Payment Not Found");
    }
}
