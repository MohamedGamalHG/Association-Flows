package com.mg.Association_Flows.associationSlot.controller;

import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.service.AssociationSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
