package com.mg.Association_Flows.associationSlot.service;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.service.AssociationService;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.domain.repo.AssociationSlotRepository;
import com.mg.Association_Flows.associationSlot.enums.AssociationSlotStatus;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssociationSlotService {

    private final AssociationSlotRepository associationSlotRepository;
    private final UserService userService;
    private final AssociationSlotMapper associationSlotMapper;

    public void generateInitialSlots(Association association) {
        List<AssociationSlot> associationSlots = new ArrayList<>();
        for (int i = 1; i <= association.getTotalShares(); i++) {
            AssociationSlot associationSlot = new AssociationSlot();
            associationSlot.setAssociation(association);
            associationSlots.add(associationSlot);
        }
        associationSlotRepository.saveAll(associationSlots);
    }

    public List<AssociationSlotDto> getAllAssociationSlotsByAssociationId(UUID associationId) {
        // if there is no any associationId then will retrieve empty list
        List<AssociationSlot> associationSlots = associationSlotRepository.findByAssociationId(associationId);
        return associationSlots.stream()
                .map(associationSlotMapper::mapToDTO)
                .toList();
    }

    public boolean checkIfAnyUserAssignToOrder(UUID associationId) {
        Boolean afterChecked = associationSlotRepository.checkIfAnyUserAssignToOrder(associationId);
        return afterChecked != null;
    }

    public AssociationSlot assignOrderOnUserToAssociation(UUID userId, int orderId) {
        // check if this order open or closed

        Boolean checkOrder = associationSlotRepository.existsByTurnOrder(orderId);
        if (checkOrder != null && checkOrder)
            throw new RuntimeException("Order already exists");

        // check if user found or not
        User user = userService.findUser(userId);

        // get all association slots and pick on of them to assign order and user to it
        List<AssociationSlot> associationSlots = associationSlotRepository.getAllAssociationSlotsThatNotAssignToUser();

        AssociationSlot associationSlot = associationSlots.getFirst();
        associationSlot.setUser(user);
        associationSlot.setTurnOrder(orderId);
        associationSlot.setStatus(AssociationSlotStatus.ACTIVE);
        associationSlot.setIsVacant(true);
        // here who log in with manager (manager of association)
        associationSlot.setCreatedBy("owner");
        associationSlotRepository.save(associationSlot);
        return associationSlot;
    }
}
