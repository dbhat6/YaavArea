package com.yaavarea.server.controller;

import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.mongo.Otp;
import com.yaavarea.server.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {

    private OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping
    public ResponseEntity<Object> createNewOtp(@RequestBody Map<String, String> requestBody) {
        log.trace("Creating a new OTP");
        try {
            otpService.createOtp(requestBody.get("phoneNumber"));
            log.trace("OTP created");
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
