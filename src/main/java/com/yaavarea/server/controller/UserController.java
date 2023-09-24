package com.yaavarea.server.controller;

import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.model.mongo.User;
import com.yaavarea.server.service.AuthService;
import com.yaavarea.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "Users", description = "The Users Api")
public class UserController {

    private UserService userService;
    private AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    //    @GetMapping
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
            authService.saveUser(userDto);
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
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "204", description = "No user found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Object> fetchUserById(@RequestParam String id) {
        log.trace("Fetching a new user");
        try {
            User user = userService.fetchUserById(id);
            log.trace("User fetched");
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException ex) {
            log.error("User with the email {} doesn't exist", id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkUserIdExists(@RequestParam String id) {
        log.trace("Checking if {} exists", id);
        try {
            userService.checkUserIdExists(id);
            log.trace("User exists");
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException ex) {
            log.error("User with the email {} doesn't exist", id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Object> removeUserByEmail(@RequestParam("email") String email) {
        log.trace("Deleting a user with email: {}", email);
        try {
            userService.removeUser(email);
            authService.removeUser(email);
            log.trace("User deleted");
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
