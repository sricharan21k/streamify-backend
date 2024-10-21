package com.app.streamify.repository;

import com.app.streamify.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {

    Optional<OTP> findByEmail(String email);

    List<OTP> findAllByEmail(String email);

    Optional<OTP> findByCode(String token);
}
