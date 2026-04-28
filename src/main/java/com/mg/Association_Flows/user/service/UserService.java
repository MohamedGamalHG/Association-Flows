package com.mg.Association_Flows.user.service;

import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return findUser(id);
    }

    public User updateUser(UUID id, User user) {
        User findedUser = findUser(id);
        // this to update user that get from DB with data that will change to it
        // that come from request
        BeanUtils.copyProperties(user, findedUser, "id");
        return userRepository.save(findedUser);
    }

    public boolean deleteUser(UUID id) {
        findUser(id);
        userRepository.deleteById(id);
        return true;
    }

    private User findUser(UUID id) {
        return userRepository.findById(id).orElseThrow(this::message);
    }

    private RuntimeException message() {
        return new RuntimeException("User Not Found");
    }

}
