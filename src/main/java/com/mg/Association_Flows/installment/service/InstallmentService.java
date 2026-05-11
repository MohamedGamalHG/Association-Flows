package com.mg.Association_Flows.installment.service;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import com.mg.Association_Flows.installment.domain.entity.Installment;
import com.mg.Association_Flows.installment.domain.repo.InstallmentRepository;
import com.mg.Association_Flows.installment.enums.InstallmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final AssociationSlotMapper associationSlotMapper;
    public void generateInstallments(Association association, List<AssociationSlotDto> slots) {
        List<Installment> installments = new ArrayList<>();
        List<AssociationSlot> associationSlots = slots.stream().map(associationSlotMapper::mapToEntity).toList();

        for (AssociationSlot slot : associationSlots) {
            for (int i = 0; i < association.getTotalShares(); i++) {
                Installment installment = new Installment();
                installment.setAssociationSlot(slot);
                installment.setAmount(association.getMonthlyAmount());
                installment.setStatus(InstallmentStatus.PENDING);

                // تاريخ الاستحقاق: أول شهر هو تاريخ البداية، ثم نزيد شهر لكل قسط
                installment.setDueDate(association.getStartDate().plusMonths(i));

                installments.add(installment);
            }
        }
        installmentRepository.saveAll(installments);
    }

    public void allocateMonthsPaid(UUID slotId, BigDecimal amountPaidByUser,Integer targetMonthToPay) {
//        Optional<List<Installment>> installments = installmentRepository.findBySlotId(slotId);
//        if (installments.isPresent()) {
//            BigDecimal amountPerMonth = installments.get().getFirst().getAmount();
//            int numberOfMonthsToPay = targetMonthToPay != null ? targetMonthToPay : amountPaidByUser.divide(amountPerMonth, RoundingMode.DOWN).intValue();
//
//            if(amountPaidByUser.divide(amountPerMonth, RoundingMode.DOWN).intValue() != numberOfMonthsToPay){
//                throw new RuntimeException("number of months you enter not match you pay");
//            }
//
//
//            List<LocalDate> dueDates = new ArrayList<>();
//            for (int i = 0; i < numberOfMonthsToPay; i++) {
//                dueDates.add(installments.get().get(i).getDueDate());
//            }
//            List<Installment> installmentsWillBeUpdated = installmentRepository.findByAssociationSlotIdAndDueDateIn(slotId,dueDates);
//            List<Installment> updatedInstallments = new ArrayList<>();
//
//            for (Installment installment : installmentsWillBeUpdated) {
//                installment.setStatus(InstallmentStatus.PAID);
//                installment.setPaidAt(Timestamp.valueOf(LocalDateTime.now()));
//                updatedInstallments.add(installment);
//            }
//            installmentRepository.saveAll(updatedInstallments);
//
//        }



        // 1. هات الأقساط الـ PENDING فقط ومرتبة بالأقدم
        List<Installment> pendingInstallments = installmentRepository
                .findBySlotId(slotId);

        if (pendingInstallments.isEmpty()) {
            throw new RuntimeException("there is no payment for this user");
        }

        BigDecimal amountPerMonth = pendingInstallments.get(0).getAmount();

        int numberOfMonthsToCover = amountPaidByUser.divide(amountPerMonth, RoundingMode.DOWN).intValue();

        if (numberOfMonthsToCover > pendingInstallments.size()) {
            throw new RuntimeException("the amount is greater than the remaining installments");
        }

        for (int i = 0; i < numberOfMonthsToCover; i++) {
            Installment installment = pendingInstallments.get(i);
            installment.setStatus(InstallmentStatus.PAID);
            installment.setPaidAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        installmentRepository.saveAll(pendingInstallments.subList(0, numberOfMonthsToCover));
    }
}
