package com.mg.Association_Flows.association.controller;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.service.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/associations")
@RequiredArgsConstructor
public class AssociationController {

    private final AssociationService associationService;

    @PostMapping
    public Association createAssociation(@RequestBody Association association){
        return associationService.createAssociation(association);
    }
}
