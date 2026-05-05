package com.mg.Association_Flows.payment.service;

import com.mg.Association_Flows.association.service.AssociationService;
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

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AssociationSlotService  associationSlotService;
    private final AssociationService associationService;

    public PaymentDto requestToPay(PaymentDto paymentDto) {
        Payment payment = paymentMapper.mapToEntity(paymentDto);
        paymentRepository.save(payment);
        return paymentDto;
    }

    public PaymentDto findPaymentByAssociationSlotId(UUID associationSlotId) {
        Optional<Payment> payment = paymentRepository.findByAssociationSlotId(associationSlotId);
        if (payment.isPresent()) {
            return paymentMapper.mapToDTO(payment.get());
        }
        throw new RuntimeException("There is no payment with association slot id " + associationSlotId);
    }

    public List<PaymentDto> getAllPaymentToConfirmedByAssociationId(UUID associationId) {
        Optional<List<Payment>> payments = paymentRepository.getAllPaymentByAssociationId(associationId);
        if (payments.isPresent()) {
            return payments.get().stream()
                    .map(paymentMapper::mapToDTO)
                    .toList();
        }
        throw new RuntimeException("There is no payment with association id " + associationId);
    }

    @Transactional
    public boolean confirmPaymentFromManager(UUID paymentId,PaymentDto paymentDto) {
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
        }
        throw new RuntimeException("There is no payment with this id " + paymentId);
    }
}
