package com.yaavarea.server.service;

import com.yaavarea.server.model.mongo.UserPass;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.repo.UserPassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final MapStructMapperImpl mapper;
    private UserPassRepo userPassRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserPassRepo userPassRepo, BCryptPasswordEncoder passwordEncoder, MapStructMapperImpl mapper) {
        this.userPassRepo = userPassRepo;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPass user = userPassRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles()).build();
    }

    public String saveUser(UserDto user) {
        String passwd = user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        UserPass userPass = new UserPass();
        userPass.setUsername(user.getEmail());
        userPass.setPassword(encodedPassword);
        userPass.setRoles(user.getRoles());
        userPass = userPassRepo.insert(userPass);
        return userPass.getId();
    }

    public boolean removeUser(String username) {
        userPassRepo.deleteByUsername(username);
        return true;
    }
}
