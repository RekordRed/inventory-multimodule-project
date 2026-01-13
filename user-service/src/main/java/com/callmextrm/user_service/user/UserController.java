package com.callmextrm.user_service.user;


import com.callmextrm.user_service.dto.RoleToUser;
import com.callmextrm.user_service.dto.UserDto;
import com.callmextrm.user_service.dto.UserRegisterDto;
import com.callmextrm.user_service.dto.UserUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }


    //Show all users CONTROLLER
    @GetMapping
    public ResponseEntity<List<UserDto>> showAllUsers() {
        return ResponseEntity.ok(usersService.showUsers());
    }


    //Register Controller
    @PostMapping("register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.register(user));
    }


    //Update user CONTROLLER
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto user) {
        return
                ResponseEntity.ok(usersService.updateUser(id, user));
    }


    //Update User's Role CONTROLLER
    @PutMapping("/updaterole")
    public ResponseEntity<Void> updateUserRole(@RequestBody RoleToUser userRole) {
        usersService.updateUserRole(userRole);
        return ResponseEntity.noContent().build();
    }


    //Link role to user CONTROLLER
    @PostMapping("/roletouser")
    public ResponseEntity<Void> roleToUser(@RequestBody RoleToUser roleToUser) {
        usersService.roleToUser(roleToUser);
        return ResponseEntity.noContent().build();
    }

    //Delete User
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

