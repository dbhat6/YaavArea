package com.yaavarea.server.controller;

import com.yaavarea.server.model.User;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
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
        try {
            userService.createUser(userDto);
            ResponseDto response = ResponseDto.builder().message("User Created").build();
            return ResponseEntity.ok(response);
        } catch (DuplicateKeyException ex) {
            ResponseDto response = ResponseDto.builder().message("User already exists")
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
    public ResponseEntity<User> fetchUserByEmail(@PathVariable String email) {
        try {
            User user = userService.fetchUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
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
