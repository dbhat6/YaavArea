package com.yaavarea.server.controller;

import com.yaavarea.server.model.User;
import com.yaavarea.server.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserDetailsController {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Integer getUser(HttpSession session) {
        Integer counter = (Integer) session.getAttribute("count");

//        User user = new User("dbhat6", "Deepak", "Bhat",
//                new Date(), 2, 1900, new EmailAddress("deepakbht67@gmail.com"));
        System.out.println(session.getId());

        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }

        session.setAttribute("count", counter);
        return counter;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser.getId());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable String id) {
        Optional<User> savedUser = userRepository.findById(String.valueOf(id));
        return ResponseEntity.ok(savedUser.get());
    }
}
