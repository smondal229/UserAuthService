package com.suvodip.userservice.service;

import com.suvodip.userservice.models.VerificationToken;

public interface ActivationTokenService {
	VerificationToken createToken();
	void saveVerificationToken(VerificationToken token);
	VerificationToken findByToken(String token);
	void removeToken(VerificationToken token);
	void removeByToken(String token);
}
