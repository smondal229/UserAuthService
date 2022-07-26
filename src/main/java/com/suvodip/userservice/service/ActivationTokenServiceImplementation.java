package com.suvodip.userservice.service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.suvodip.userservice.models.VerificationToken;
import com.suvodip.userservice.repo.VerificationTokenRepo;

@Service
public class ActivationTokenServiceImplementation implements ActivationTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Autowired
    VerificationTokenRepo verificationTokenRepository;

    @Value("${verificationToken.validity}")
    private int tokenValidityInSeconds;

	@Override
	public VerificationToken createToken() {
        String tokenValue = new String(Base64.getEncoder().encode(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        VerificationToken secureToken = new VerificationToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusMinutes(getTokenValidityInSeconds()));
        this.saveVerificationToken(secureToken);
        return secureToken;
	}

	@Override
	public void saveVerificationToken(VerificationToken token) {
		verificationTokenRepository.save(token);
	}

	@Override
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}

	@Override
	public void removeToken(VerificationToken token) {
		verificationTokenRepository.delete(token);
	}

	@Override
	public void removeByToken(String token) {
		verificationTokenRepository.removeByToken(token);
	}
	
    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }
}
