package com.yaavarea.server.service;

import com.yaavarea.server.model.dto.AdditionalUserInfoDto;
import com.yaavarea.server.model.enums.XpCategoriesEnum;
import com.yaavarea.server.model.mongo.User;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.repo.UserPassRepo;
import com.yaavarea.server.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserPassRepo userPassRepo;
    private final AuthService authService;
    private final MapStructMapperImpl mapper;

    @Autowired
    public UserService(UserRepo userRepo, UserPassRepo userPassRepo, AuthService authService, MapStructMapperImpl mapper) {
        this.userRepo = userRepo;
        this.userPassRepo = userPassRepo;
        this.authService = authService;
        this.mapper = mapper;
    }

    public User createUser(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        user.setUserStats(new AdditionalUserInfoDto());
        user.setCreatedAt(LocalDateTime.now());
        userRepo.insert(user);
        authService.saveUser(userDto);
        return user;
    }

    public User fetchUserByEmail(String email) {
        Optional<User> savedUser = userRepo.findByEmail(email);
        if (savedUser.isPresent())
            return savedUser.get();
        else
            throw new NoSuchElementException("No user found!");
    }

    public User fetchUserById(String id) {
        Optional<User> savedUser = userRepo.findById(id);
        if (savedUser.isPresent())
            return savedUser.get();
        else
            throw new NoSuchElementException("No user found!");
    }

    public void checkUserIdExists(String id) {
        boolean savedUser = userRepo.existsById(id);
        if(!savedUser)
            throw new NoSuchElementException("User doesn't exist");
    }

    public void addCardsRated(String id, XpCategoriesEnum xpEnum) {
        Optional<User> savedUser = userRepo.findById(id);
        if (!savedUser.isPresent())
            throw new NoSuchElementException("No user found!");
        User user = savedUser.get();
        user.getUserStats().addRate();
        user.getUserStats().gainXp(xpEnum);
        userRepo.save(user);
    }

    public void removeUser(String email) {
        userRepo.deleteByEmail(email);
        authService.removeUser(email);
    }
}
