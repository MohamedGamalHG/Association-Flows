package com.mg.Association_Flows.user.service;

import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.service.PaymentService;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.user.domain.repo.UserRepository;
import com.mg.Association_Flows.user.domain.dtos.UserDto;
import com.mg.Association_Flows.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        User userSaved = userRepository.save(user);
        userDto.setId(userSaved.getId());
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream().map(userMapper::mapToDto).toList();
        return userMapper.mapToDtoList(users);
    }

    public UserDto getUserById(UUID id) {
        return userMapper.mapToDto(findUser(id));
    }

    public UserDto updateUser(UUID id, UserDto userDto) {
        User findedUser = findUser(id);
        /*
         * this to update user that get from DB with data that will change to it
         * that come from request but there is dangerous in this usage
         * this statement below is just update the field that come in request other will
         * be null
         * until it have a data if phone_number has value and i just update email then
         * when use BeanUtils.copyProperties(userDto, findedUser, "id");
         * will make phone_number null
         * 
         * BeanUtils.copyProperties(userDto, findedUser, "id");
         */
        userMapper.updateUserFromDto(userDto, findedUser);
        return userMapper.mapToDto(userRepository.save(findedUser));
    }

    public boolean deleteUser(UUID id) {
        findUser(id);
        userRepository.deleteById(id);
        return true;
    }

    public User findUser(UUID id) {
        return userRepository.findById(id).orElseThrow(this::message);
    }

    private RuntimeException message() {
        return new RuntimeException("User Not Found");
    }




}
