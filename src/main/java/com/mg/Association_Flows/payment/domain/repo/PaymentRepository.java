package com.mg.Association_Flows.payment.domain.repo;

import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PaymentRepository extends BaseRepository<Payment> {

    Optional<Payment> findByAssociationSlotId(UUID association_slot_Id);

    @Query("select p from Payment p where p.associationSlot.association_id = :association_id")
    Optional<List<Payment>> getAllPaymentByAssociationId(UUID association_id);
}
