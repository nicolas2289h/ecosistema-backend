package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.User;
import com.semillero.ecosistemas.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IUserService userService;

    //RETURN ALL THE USERS (SUPPLIERS AND ADMINS)
    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    //FIND ANY USER BY EMAIL
    @GetMapping("/find/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        Optional<User> optionalUser = userService.findUserByEmail(email);
        return optionalUser.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
}