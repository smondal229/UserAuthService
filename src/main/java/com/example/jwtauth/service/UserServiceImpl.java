package com.example.jwtauth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwtauth.models.Role;
import com.example.jwtauth.models.User;
import com.example.jwtauth.repositories.RoleRepository;
import com.example.jwtauth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findUserByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);		
	}

	@Override
	public User getUser(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public List<User> listAllUsers() {
		return userRepository.findAll();
	}

}
