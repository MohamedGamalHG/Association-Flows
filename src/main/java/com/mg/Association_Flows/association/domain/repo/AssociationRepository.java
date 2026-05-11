package com.mg.Association_Flows.association.domain.repo;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;


@Repository
public interface AssociationRepository extends BaseRepository<Association> {

    @Modifying
    @Query("UPDATE Association a SET a.currentCollectedBalance = a.currentCollectedBalance - :payoutAmount WHERE a.id = :id AND a.currentCollectedBalance >= :payoutAmount")
    int deductPayoutFromBalance(@Param("id") UUID id, @Param("payoutAmount") BigDecimal payoutAmount);

}
