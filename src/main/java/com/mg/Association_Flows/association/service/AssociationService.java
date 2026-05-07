package com.mg.Association_Flows.association.service;

import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.association.domain.repo.AssociationRepository;
import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.association.mapper.AssociationMapper;
import com.mg.Association_Flows.associationSlot.service.AssociationSlotService;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import com.mg.Association_Flows.user.mapper.UserMapper;
import com.mg.Association_Flows.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final AssociationSlotService associationSlotService;

    public List<AssociationDto> getAllAssociation() {
        List<Association> associations = associationRepository.findAll();
        return associations
                .stream()
                .map(associationMapper::mapToDto)
                .toList();
    }

    public AssociationDto getAssociationById(UUID id) {
        return associationMapper.mapToDto(findAssociation(id));
    }

    @Transactional // this transactional for association slot because will generate slots
    public AssociationDto createAssociation(AssociationDto associationDto) {
        associationDto.setOwner(userMapper.mapToDto(service.findUser(associationDto.getOwner().getId())));
        Association association = associationMapper.mapToEntity(associationDto);

        LocalDate endDate = associationDto.getStartDate().plusMonths(associationDto.getTotalShares());

        associationDto.setEndDate(endDate);

        Association associationSaved = associationRepository.save(association);
        associationDto.setId(associationSaved.getId());
        // start call slot service to generate slots
        associationSlotService.generateInitialSlots(associationSaved);
        // end call
        return associationDto;
    }


    public AssociationDto updateAssociation(UUID id, AssociationDto associationDto) {
        Association association = findAssociation(id);
        /*
         check if there is any user assign to this association then we can't make
         change in the association from total shares or monthly amount
        */

        /*
         * I put this logic of 2 method to handle that data that will be updated
         * the data that allowance to update is (title,description,status)
         * after association is created and user assigned to this association
         * */

        // the two method i remove it and instead of them i make this code to check if change in core
        boolean isTryingChangeCore = associationDto.getMonthlyAmount() != null ||
                associationDto.getTotalShares() != null ||
                associationDto.getStartDate() != null;

        if (isTryingChangeCore)
            handleAssociationAssign(id);

        /*
        handle logic if there is any update in the monthly amount , total share so this
         will reflect in the totalPoolAmount
        */

        handleTotalPool(associationDto, association);

        handleEndDate(associationDto,association);

        associationMapper.updateAssociationFromDto(associationDto, association);
        associationRepository.save(association);
        return associationDto;
    }

    private void handleEndDate(AssociationDto associationDto, Association association) {
        if(associationDto.getTotalShares() != null)
            associationDto.setEndDate(association.getStartDate().plusMonths(associationDto.getTotalShares()));
    }

    private void handleAssociationAssign(UUID id) {
        boolean checked = associationSlotService.checkIfAnyUserAssignToOrder(id);
        if (checked)
            throw new RuntimeException("This association has already been assigned we can'\t make update");

    }

    public Boolean deleteAssociation(UUID id) {
        findAssociation(id);
        associationRepository.deleteById(id);
        return true;
    }

    public Association findAssociation(UUID id) {
        return associationRepository.findById(id).orElseThrow(this::message);
    }

    public BigDecimal totalPoolAmountOfAssociation(UUID associationId) {
        return findAssociation(associationId).getTotalPoolAmount();
    }

    public BigDecimal monthlyAmountOfAssociation(UUID associationId) {
        return findAssociation(associationId).getMonthlyAmount();
    }

    public LocalDate startDateOfAssociation(UUID associationId) {
        return findAssociation(associationId).getStartDate();
    }

    public LocalDate endDateOfAssociation(UUID associationId) {
        return findAssociation(associationId).getEndDate();
    }

    public AssociationStatus statusOfAssociation(UUID associationId) {
        return findAssociation(associationId).getStatus();
    }
    private RuntimeException message() {
        return new RuntimeException("Association Not Found");
    }

    private void handleTotalPool(AssociationDto associationDto, Association association) {
        // update code base to be shorter
        BigDecimal monthly = (associationDto.getMonthlyAmount() != null) ? associationDto.getMonthlyAmount() : association.getMonthlyAmount();
        Integer shares = (associationDto.getTotalShares() != null) ? associationDto.getTotalShares() : association.getTotalShares();

        associationDto.setTotalPoolAmount(monthly.multiply(BigDecimal.valueOf(shares)));
    }

}
