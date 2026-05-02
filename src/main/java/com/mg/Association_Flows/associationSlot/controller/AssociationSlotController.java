package com.mg.Association_Flows.associationSlot.controller;

import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.service.AssociationSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/association-slot")
@RequiredArgsConstructor
public class AssociationSlotController {

    private final AssociationSlotService associationSlotService;

    @GetMapping("/allAssociationSlotByAssId/{association_id}")
    public ResponseEntity<List<AssociationSlotDto>> getAllAssociationSlotByAssociationId(@PathVariable UUID association_id) {
        return ResponseEntity.ok().body(associationSlotService.getAllAssociationSlotsByAssociationId(association_id));
    }

    @PostMapping("/assignUserToAssociation/{userId}/{orderId}")
    public ResponseEntity<AssociationSlot>  assignUserToAssociation(@PathVariable UUID userId,@PathVariable  int orderId) {
        return ResponseEntity.ok().body(associationSlotService.assignOrderOnUserToAssociation(userId,orderId));
    }
}
