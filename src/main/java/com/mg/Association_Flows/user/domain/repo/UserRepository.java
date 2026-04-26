package com.mg.Association_Flows.user.domain.repo;

import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User> {
}
