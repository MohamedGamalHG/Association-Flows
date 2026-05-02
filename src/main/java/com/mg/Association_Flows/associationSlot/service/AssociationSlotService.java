package com.mg.Association_Flows.associationSlot.service;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.service.AssociationService;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.domain.repo.AssociationSlotRepository;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssociationSlotService {

    private final AssociationSlotRepository associationSlotRepository;
    //    private final AssociationService associationService;
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
}
