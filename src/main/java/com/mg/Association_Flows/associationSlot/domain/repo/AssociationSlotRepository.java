package com.mg.Association_Flows.associationSlot.domain.repo;

import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AssociationSlotRepository extends BaseRepository<AssociationSlot> {

    List<AssociationSlot> findByAssociationId(UUID associationId);
}
