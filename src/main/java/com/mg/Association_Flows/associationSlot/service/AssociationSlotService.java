package com.mg.Association_Flows.associationSlot.service;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.domain.repo.AssociationSlotRepository;
import com.mg.Association_Flows.associationSlot.enums.AssociationSlotStatus;
import com.mg.Association_Flows.associationSlot.exception.AssociationSlotNotFoundException;
import com.mg.Association_Flows.associationSlot.exception.AssociationSlotPayoutException;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AssociationSlotService {

    private final AssociationSlotRepository associationSlotRepository;
    private final UserService userService;
    private final AssociationSlotMapper associationSlotMapper;

    public List<AssociationSlotDto> generateInitialSlots(Association association) {
        //long totalMonth = ChronoUnit.MONTHS.between(association.getStartDate(),association.getEndDate());
        Period totalPeriod = Period.between(association.getStartDate(), association.getEndDate());
        int totalMonths = totalPeriod.getYears()* 12 + totalPeriod.getMonths();

        // for simplicity
        // i can remove about and just get the total month from association.getTotalShares()
        // int totalMonths = totalPeriod.getYears()* 12 + totalPeriod.getMonths();



        List<AssociationSlot> associationSlots = new ArrayList<>();
        for (int i = 1; i <= association.getTotalShares(); i++) {
            AssociationSlot associationSlot = new AssociationSlot();
            associationSlot.setAssociation(association);
            associationSlot.setTurnOrder(i);
            associationSlot.setIsReserved(true);
            associationSlot.setRemainingInstallments(totalMonths);
            associationSlots.add(associationSlot);
        }
        List<AssociationSlot> associationSlotSaved = associationSlotRepository.saveAll(associationSlots);

        return associationSlotSaved.stream()
                .map(associationSlotMapper::mapToDTO)
                .toList();
    }

    public List<AssociationSlotDto> getAllAssociationSlotsByAssociationId(UUID associationId) {
        // if there is no any associationId then will retrieve empty list
        List<AssociationSlot> associationSlots = associationSlotRepository.findByAssociationId(associationId);
        return associationSlots.stream()
                .map(associationSlotMapper::mapToDTO)
                .toList();
    }

    public boolean checkIfAnyUserAssignToOrder(UUID associationId) {
        AssociationSlot afterChecked = associationSlotRepository.checkIfAnyUserAssignToOrder(associationId);
        return afterChecked != null;
    }

    public AssociationSlot assignOrderOnUserToAssociation(UUID associationId,UUID userId, int orderId) {

        // check if user found or not
        User user = userService.findUser(userId);

        // change the logic to filter by orderId and associationId where user is null not assign to this
        // order if assign then i will throw exception below
        Optional<AssociationSlot> specificAssociationSlot = associationSlotRepository.findSpecificSlotToAssign(associationId,orderId);

        if (specificAssociationSlot.isPresent()) {
            AssociationSlot associationSlot = specificAssociationSlot.get();
            associationSlot.setUser(user);
            associationSlot.setStatus(AssociationSlotStatus.ACTIVE);
            // here who log in with manager (manager of association)
            associationSlot.setCreatedBy("owner");
            associationSlot.setJoinedAt(new Timestamp(System.currentTimeMillis()));
            associationSlotRepository.save(associationSlot);
            return associationSlot;
        }
        throw new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found");
    }

    public AssociationSlotDto getAssociationSlotById(UUID associationSlotId) {
        Optional<AssociationSlot> associationSlot = associationSlotRepository.findById(associationSlotId);
        if (associationSlot.isPresent()) {
            return  associationSlotMapper.mapToDTO(associationSlot.get());
        }
        throw new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found");
    }

    public void updateAssociationSlot(UUID associationSlotId, BigDecimal paidAfterConfirmed,Integer numberOfMonthsPaid) {
        Optional<AssociationSlot> associationSlot = associationSlotRepository.findById(associationSlotId);
        if (associationSlot.isPresent()) {
            associationSlot.get().setTotalPaidSoFar(paidAfterConfirmed);
            associationSlot.get().setRemainingInstallments(associationSlot.get().getRemainingInstallments() - numberOfMonthsPaid);
            associationSlotRepository.save(associationSlot.get());
        }
        throw new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found");
    }

    public void markSlotAsPaidOut(UUID slotId) {
        AssociationSlot slot = associationSlotRepository.findById(slotId)
                .orElseThrow(() -> new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found"));

        if (slot.getUser() == null) {
            throw new AssociationSlotPayoutException("Cannot payout an empty slot",HttpStatus.BAD_REQUEST,"Slot Not Found");
        }

        slot.setIsPayoutDone(true);
        slot.setPayoutDate(LocalDate.now());
        associationSlotRepository.save(slot);
    }

    public AssociationSlotDto findByAssociationIdAndTurnOrder(UUID associationId, int turnOrder) {
        Optional<AssociationSlot> associationSlot = associationSlotRepository.findByAssociationIdAndTurnOrder(associationId,turnOrder);
        if(associationSlot.isPresent()) {
            return associationSlotMapper.mapToDTO(associationSlot.get());
        }
        throw new AssociationSlotNotFoundException("No such association slot", HttpStatus.NOT_FOUND,"Association Slot Not Found");
    }
}
