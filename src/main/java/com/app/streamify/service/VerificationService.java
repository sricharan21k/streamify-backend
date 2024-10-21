package com.app.streamify.service;

import com.app.streamify.dto.ValidateOTP;
import com.app.streamify.model.OTP;
import com.app.streamify.repository.OTPRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class VerificationService {

    private final JavaMailSender mailSender;
    private final OTPRepository otpRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VerificationService(JavaMailSender javaMailSender,
                               OTPRepository otpRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        this.mailSender = javaMailSender;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean sendOtpMessage(String email) {
        String messageBody = "Hi, here is the otp. \n" + generateOtp(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sricharanata19@gmail.com");
        message.setSubject("subject");
        message.setTo(email);
        message.setText(messageBody);
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean validateOtp(ValidateOTP otp) {
        OTP existingOtp = otpRepository.findByEmail(otp.getEmail()).orElseThrow();

        boolean valid = passwordEncoder.matches(otp.getCode(), existingOtp.getCode())
                && existingOtp.getExpiry().isAfter(LocalDateTime.now());

        otpRepository.deleteById(existingOtp.getId());
        return valid;
    }


    private String generateOtp(String email) {
        String code = new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
        OTP otp = OTP.builder().email(email)
                .code(passwordEncoder.encode(code))
                .expiry(LocalDateTime.now().plusMinutes(2))
                .build();

        List<OTP> previousOTPs = otpRepository.findAllByEmail(email);
        if (!previousOTPs.isEmpty()) {
            previousOTPs.forEach(x -> {
                otpRepository.deleteById(x.getId());
            });
        }
        otpRepository.save(otp);
        return code;
    }
}
