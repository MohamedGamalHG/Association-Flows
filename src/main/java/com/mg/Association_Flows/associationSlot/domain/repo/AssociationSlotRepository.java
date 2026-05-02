package com.mg.Association_Flows.associationSlot.domain.repo;

import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AssociationSlotRepository extends BaseRepository<AssociationSlot> {

    List<AssociationSlot> findByAssociationId(UUID associationId);

    @Query("select slot.isVacant from AssociationSlot slot where  slot.association.id = :associationId and slot.isVacant = true")
    Boolean checkIfAnyUserAssignToOrder(@Param("associationId") UUID associationId);

    Boolean existsByTurnOrder(int orderId);

    @Query("select slot from AssociationSlot slot where " +
            " slot.user.id is null and slot.isVacant is false and slot.turnOrder is null")
    List<AssociationSlot> getAllAssociationSlotsThatNotAssignToUser();

//    @Query("select slot from AssociationSlot slot where  slot.association.id = ?1 and slot.isVacant = true")
//    AssociationSlot checkIfAnyUserAssignToOrder( UUID associationId);
}
