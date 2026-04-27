package com.mg.Association_Flows.association.service;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.domain.repo.AssociationRepository;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final UserRepository userRepository;

    public Association createAssociation(Association association){
        User owner = userRepository.findById(association.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        association.setOwner(owner);

        return associationRepository.save(association);
    }
}
