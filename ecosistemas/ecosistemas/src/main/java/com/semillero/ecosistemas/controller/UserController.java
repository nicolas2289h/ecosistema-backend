package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.User;
import com.semillero.ecosistemas.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> switchUserState(@PathVariable Long id) {

        User user = userService.findUserById(id);
        if(user!=null){
            userService.switchState(user);
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}