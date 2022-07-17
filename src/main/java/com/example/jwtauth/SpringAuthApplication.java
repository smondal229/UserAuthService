package com.example.jwtauth;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.jwtauth.models.Role;
import com.example.jwtauth.models.User;
import com.example.jwtauth.service.UserService;

@SpringBootApplication
public class SpringAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			
			userService.saveUser(new User(null, "Suvodip Mondal", "s.mondal", "1234", new ArrayList<Role>()));
			userService.addRoleToUser("s.mondal", "ROLE_SUPER_ADMIN");
			
			userService.saveUser(new User(null, "Akash Arora", "a.arora", "1234", new ArrayList<Role>()));
			userService.addRoleToUser("a.arora", "ROLE_ADMIN");
			
			userService.saveUser(new User(null, "Shubham Pathak", "s.pathak", "1234", new ArrayList<Role>()));
			userService.addRoleToUser("s.pathak", "ROLE_MANAGER");
			
			userService.saveUser(new User(null, "Karan Sharma", "k.sharma", "1234", new ArrayList<Role>()));
			userService.addRoleToUser("k.sharma", "ROLE_USER");
		};
	}

}
