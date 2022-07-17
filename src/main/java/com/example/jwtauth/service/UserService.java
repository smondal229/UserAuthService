package com.example.jwtauth.service;

import java.util.List;

import com.example.jwtauth.models.Role;
import com.example.jwtauth.models.User;


public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	User getUser(String username);
	List<User> listAllUsers();
}
