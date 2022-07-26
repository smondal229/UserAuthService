package com.suvodip.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvodip.userservice.models.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);
	VerificationToken removeByToken(String token);
}
