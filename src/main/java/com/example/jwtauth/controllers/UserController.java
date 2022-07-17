package com.example.jwtauth.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.jwtauth.models.User;
import com.example.jwtauth.service.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.listAllUsers());
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		// ServletUriComponentsBuilder.fromCurrentContextPath() - http://localhost:8080
        URI uri=  URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/create").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PostMapping("/add-role")
	public ResponseEntity<User> createRole(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
}

@Data
class RoleToUserForm {
	private String username;
	private String roleName;
}
