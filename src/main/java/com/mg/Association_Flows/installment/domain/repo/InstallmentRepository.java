package com.mg.Association_Flows.installment.domain.repo;

import com.mg.Association_Flows.installment.domain.entity.Installment;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstallmentRepository extends BaseRepository<Installment> {

    @Query("select ins from Installment ins where ins.associationSlot.id = :slotId and ins.status = 'PENDING' order by dueDate asc")
    List<Installment>findBySlotId(@Param("slotId") UUID slotId);

    List<Installment> findByAssociationSlotIdAndDueDateIn(UUID slotId,List<LocalDate> dueDates);

//    @Query("select ins from Installment ins where ins.associationSlot.id = :slotId and ins.dueDate in :dueDates ")
//    List<Installment> findByAssociationSlotIdAndDueDateIn(@Param("slotId") UUID slotId,@Param("dueDates") List<LocalDate> dueDates);
}
