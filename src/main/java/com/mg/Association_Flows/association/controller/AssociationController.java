package com.mg.Association_Flows.association.controller;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.service.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/associations")
@RequiredArgsConstructor
public class AssociationController {

    private final AssociationService associationService;

    @GetMapping("/getAllAssociation")
    public ResponseEntity<List<AssociationDto>> getAllAssociation(){
        return ResponseEntity.ok().body(associationService.getAllAssociation());
    }

    @GetMapping("/getAssociation/{id}")
    public ResponseEntity<AssociationDto> getAssociationById(@PathVariable UUID id){
        return ResponseEntity.ok().body(associationService.getAssociationById(id));
    }

    @PostMapping
    public ResponseEntity<AssociationDto> createAssociation(@RequestBody AssociationDto associationDto){
        return ResponseEntity.ok().body(associationService.createAssociation(associationDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AssociationDto> updateAssociation(@PathVariable UUID id, @RequestBody AssociationDto associationDto){
        return ResponseEntity.ok().body(associationService.updateAssociation(id,associationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAssociation(@PathVariable UUID id){
        return ResponseEntity.ok().body(associationService.deleteAssociation(id));
    }
}
