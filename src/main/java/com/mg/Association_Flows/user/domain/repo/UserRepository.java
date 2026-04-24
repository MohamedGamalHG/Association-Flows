package com.mg.Association_Flows.user.domain.repo;

import com.mg.Association_Flows.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
