package com.mg.Association_Flows.associationSlot.domain.repo;

import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AssociationSlotRepository extends BaseRepository<AssociationSlot> {

    List<AssociationSlot> findByAssociationId(UUID associationId);

    @Query("select slot.isVacant from AssociationSlot slot where  slot.association.id = :associationId and slot.isReserved = true")
    Boolean checkIfAnyUserAssignToOrder(@Param("associationId") UUID associationId);

    Boolean existsByTurnOrder(int orderId);

    @Query("SELECT s FROM AssociationSlot s WHERE s.association.id = :assocId AND s.turnOrder = :order AND s.user IS NULL")
    Optional<AssociationSlot> findSpecificSlotToAssign(@Param("assocId") UUID assocId, @Param("order") int order);


//    @Query("select slot from AssociationSlot slot where  slot.association.id = ?1 and slot.isVacant = true")
//    AssociationSlot checkIfAnyUserAssignToOrder( UUID associationId);
}
