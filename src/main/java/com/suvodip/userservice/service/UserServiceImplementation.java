package com.suvodip.userservice.service;

import com.suvodip.userservice.email.context.AccountVerificationEmailContext;
import com.suvodip.userservice.exceptions.InvalidTokenException;
import com.suvodip.userservice.exceptions.UserAlreadyExistException;
import com.suvodip.userservice.models.Role;
import com.suvodip.userservice.models.User;
import com.suvodip.userservice.models.VerificationToken;
import com.suvodip.userservice.repo.RoleRepo;
import com.suvodip.userservice.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService , UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ActivationTokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            log.error("user not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("user found in the database :{}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> { authorities.add(new SimpleGrantedAuthority(role.getName())); });
        return new org.springframework.security.core.userdetails.User(
        		user.getUsername(),
        		user.getPassword(),
        		user.isAccountVerified(),
        		true, // AccountNotExpired
        		true, // AccountCredentialsNotExpired
        		true, // AccountNotLocked
        		authorities);
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
    	if (userRepo.findByUsername(user.getUsername()) != null) {
    		throw new UserAlreadyExistException("User already exists with email "+ user.getUsername());
    	}

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role to the database " , role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info(" Adding role {} to user {}" , roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);

        log.info("Is user with user role {}", roleName.equals("ROLE_USER"));
        if (!user.isAccountVerified() && roleName.equals("ROLE_USER")) {
            sendRegistrationMail(user);
        }
    }

    @Override
    public User getUser(String username) {
        log.info(" fetching user {} ", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info(" Fetching all users");
        return userRepo.findAll();
    }

    private void sendRegistrationMail(User user) {
    	VerificationToken token = tokenService.createToken();
    	token.setUser(user);
    	AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
    	emailContext.init(user);
    	emailContext.setToken(token.getToken());
    	emailContext.buildVerificationUrl("http://localhost:8080", token.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        };
    }

	@Override
	public void deleteAllUsers() {
		userRepo.deleteAll();
	}

	@Override
	public boolean verifyUser(String token) throws InvalidTokenException {
	    VerificationToken secureToken = tokenService.findByToken(token);
	    if (Objects.isNull(secureToken) || !token.equals(secureToken.getToken()) || secureToken.isExpired()) {
	        throw new InvalidTokenException("Token is not valid");
	    }
	    log.info(" fetching user id {} ", secureToken.getUser().getId());
	    User user = userRepo.findById(secureToken.getUser().getId()).orElseGet(() -> null);
	    if (Objects.isNull(user)) {
	        return false;
	    }
	    user.setAccountVerified(true);
	    userRepo.save(user); // let’s same user details

	    // we don’t need invalid password now
	    tokenService.removeToken(secureToken);
	    return true;
	}

}
