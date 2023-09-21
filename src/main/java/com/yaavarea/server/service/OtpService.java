package com.yaavarea.server.service;

import com.google.i18n.phonenumbers.Phonenumber;
import com.yaavarea.server.model.dto.AdditionalUserInfoDto;
import com.yaavarea.server.model.dto.UserDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.model.mongo.Otp;
import com.yaavarea.server.model.mongo.User;
import com.yaavarea.server.repo.OtpRepo;
import com.yaavarea.server.repo.UserPassRepo;
import com.yaavarea.server.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepo otpRepo;

    @Autowired
    public OtpService(UserRepo userRepo, OtpRepo otpRepo, UserPassRepo userPassRepo, AuthService authService, MapStructMapperImpl mapper) {
        this.otpRepo = otpRepo;
    }

    public Otp createOtp(String phoneNumber) {
        try {
            Otp otp = new Otp();
            Random rand = new Random();
            int randomNum = rand.nextInt(900000) + 100000;
            otp.setOtp(randomNum);
            otp.setPhoneNumber(phoneNumber);
            return otpRepo.insert(otp);
        } catch (DuplicateKeyException ex) {
            Optional<Otp> optionalOtp = otpRepo.findByPhoneNumber(phoneNumber);
            // TODO: change code to send to a messaging platform
            if (optionalOtp.isPresent())
                return optionalOtp.get();
            else
                throw new NoSuchElementException("No otp found!");
        }
    }
}
