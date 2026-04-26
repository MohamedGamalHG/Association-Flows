package com.mg.Association_Flows.payment.domain.repo;

import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends BaseRepository<Payment> {
}
