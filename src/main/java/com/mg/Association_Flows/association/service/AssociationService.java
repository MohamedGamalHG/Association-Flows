package com.mg.Association_Flows.association.service;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.domain.repo.AssociationRepository;
import com.mg.Association_Flows.association.mapper.AssociationMapper;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import com.mg.Association_Flows.user.mapper.UserMapper;
import com.mg.Association_Flows.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final UserService service;
    private final AssociationMapper associationMapper;
    private final UserMapper userMapper;

    public List<AssociationDto> getAllAssociation() {
        List<Association> associations = associationRepository.findAll();
        return associations
                .stream()
                .map(associationMapper::mapToDto)
                .toList();
    }

    public AssociationDto getAssociationById(UUID id) {
        return  associationMapper.mapToDto(findAssociation(id));
    }

    public AssociationDto createAssociation(AssociationDto associationDto){
        associationDto.setOwner(userMapper.mapToDto(service.findUser(associationDto.getOwner().getId())));
        Association association = associationMapper.mapToEntity(associationDto);
        Association associationSaved = associationRepository.save(association);
        associationDto.setId(associationSaved.getId());
        return associationDto;
    }


    public AssociationDto updateAssociation(UUID id, AssociationDto associationDto) {
        Association association = findAssociation(id);
        associationMapper.updateAssociationFromDto(associationDto, association);
        associationRepository.save(association);
        return associationDto;
    }

    public Boolean deleteAssociation(UUID id) {
        findAssociation(id);
        associationRepository.deleteById(id);
        return true;
    }

    private Association findAssociation(UUID id){
        return associationRepository.findById(id).orElseThrow(this::message);
    }

    private RuntimeException message() {
        return new RuntimeException("Association Not Found");
    }
}
