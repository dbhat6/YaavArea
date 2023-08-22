package com.yaavarea.server.controller;

import com.yaavarea.server.model.mongo.User;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserDetailsController {

    private UserService userService;

    @Autowired
    public UserDetailsController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
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

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.trace("Registering a new user");
        try {
            userService.createUser(userDto);
            log.trace("User registered");
            ResponseDto response = ResponseDto.builder().message("User Created").build();
            return ResponseEntity.ok(response);
        } catch (DuplicateKeyException ex) {
            log.error("User already exists with the email {}", userDto.getEmail());
            ResponseDto response = ResponseDto.builder().message("User already exists")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

//    @PostMapping("/new")
//    public ResponseEntity<String> createNewUser(@RequestBody User user) {
//        Optional<User> userPresent = userRepo.findByEmailAndFirstNameAndLastName(
//                user.getEmail(), user.getFirstName(), user.getLastName());
//        if(userPresent.isPresent())
//            return ResponseEntity.status(500).body("User is already present");
//        user.setXpLevel(0);
//        user.setXp(0);
//        System.out.println(user);
//        User savedUser = userRepo.insert(user);
//        return ResponseEntity.ok(savedUser.getId());
//    }

//    @PutMapping
//    public ResponseEntity<Object> updateUser(@RequestBody User user) {
//        Optional<User> response = userRepo.findByEmail(user.getEmail());
//        if (response.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        var responseUpdated = user.update(user.getId(), user);
//        return ResponseEntity.ok(userRepo.save(responseUpdated));
//    }
//
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> fetchUserByEmail(@PathVariable String email) {
        log.trace("Fetching a new user");
        try {
            User user = userService.fetchUserByEmail(email);
            log.trace("User fetched");
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException ex) {
            log.error("User with the email {} doesn't exist", email);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

//    @GetMapping("/id/{id}")
//    public ResponseEntity<User> fetchUserById(@PathVariable String id) {
//        Optional<User> savedUser = userRepo.findById(id);
//        if (savedUser.isPresent())
//            return ResponseEntity.ok(savedUser.get());
//        else
//            return ResponseEntity.notFound().build();
//    }
}
