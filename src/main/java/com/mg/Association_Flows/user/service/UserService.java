package com.mg.Association_Flows.user.service;

import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

}
