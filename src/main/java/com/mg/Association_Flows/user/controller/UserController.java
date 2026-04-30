package com.mg.Association_Flows.user.controller;

import com.mg.Association_Flows.user.domain.dtos.UserDto;
import com.mg.Association_Flows.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PostMapping
    public UserDto createUser(@RequestBody UserDto user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id,userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
