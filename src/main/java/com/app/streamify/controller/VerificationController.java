package com.app.streamify.controller;

import com.app.streamify.dto.ValidateOTP;
import com.app.streamify.service.VerificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/send-otp")
    public boolean sendOtp(@RequestBody String email) {
        return verificationService.sendOtpMessage(email);
    }

    @PostMapping("/validate-otp")
    public boolean validateOtp(@RequestBody ValidateOTP otp) {
        return verificationService.validateOtp(otp);
    }

}
