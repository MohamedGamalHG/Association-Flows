package com.mg.Association_Flows.payment.domain.repo;

import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PaymentRepository extends BaseRepository<Payment> {

    Optional<Payment> findByAssociationSlotId(UUID association_slot_Id);

    @Query("select p from Payment p where p.associationSlot.association.id = :assocId")
    Optional<List<Payment>> getAllPaymentByAssociationId(@Param("assocId")UUID association_id);

    // AND p.isUsedForPayout = false
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.associationSlot.association.id = :assocId AND p.status = 'CONFIRMED'")
    BigDecimal calculateAvailablePool(@Param("assocId") UUID assocId);
}
