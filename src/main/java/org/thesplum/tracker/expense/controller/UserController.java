package org.thesplum.tracker.expense.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thesplum.tracker.expense.model.User;
import org.thesplum.tracker.expense.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        
        ArrayList<User> users = new ArrayList<>();
        for (User usr: userRepository.findAll()) {
            users.add(usr);
        }

        return ResponseEntity.ok(users);
    }
}
