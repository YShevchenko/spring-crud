package com.mytry.feedback.controller;

import com.mytry.feedback.entity.User;
import com.mytry.feedback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "user/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable("id") Integer id) {
        userRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "user/{id}")
    public ResponseEntity<User> replaceUser(@PathVariable("id") Integer id,
                                            @RequestBody User newUser) {
        User currentUser = userRepository.findOne(id);
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //TODO: Investifate how to avoid getters-setters here
        currentUser.setEmail(newUser.getEmail());
        currentUser.setUsername(newUser.getUsername());
        userRepository.save(currentUser);

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

}
