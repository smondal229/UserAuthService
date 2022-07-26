package com.suvodip.userservice;

import com.suvodip.userservice.models.Role;
import com.suvodip.userservice.models.User;
import com.suvodip.userservice.models.VerificationToken;
import com.suvodip.userservice.service.UserService;

import org.hibernate.mapping.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	 @Bean
	 PasswordEncoder passwordEncoder(){
		 final String idForEncode = "suvodip";
		 Map<String, PasswordEncoder> encoders = new HashMap<>();
		 encoders.put(idForEncode, new BCryptPasswordEncoder());
		 encoders.put("pdkdf2", new Pbkdf2PasswordEncoder());
		 encoders.put("scrypt", new SCryptPasswordEncoder());

		 return new DelegatingPasswordEncoder(idForEncode, encoders);
	 }

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.deleteAllUsers();
			userService.saveRole(new Role(null , "ROLE_USER"));
			userService.saveRole(new Role(null , "ROLE_MANAGER"));
			userService.saveRole(new Role(null , "ROLE_ADMIN"));
			userService.saveRole(new Role(null , "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "John Travolta", "sdipdev@gmail.com", "1234", new ArrayList<>(), false));
			userService.saveUser(new User(null, "Will Smith", "will@gmail.com", "1234", new ArrayList<>(), true));
			userService.saveUser(new User(null, "Jim Carry", "jim@gmail.com", "1234", new ArrayList<>(), true));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold@gmail.com", "1234", new ArrayList<>(), true));

			userService.addRoleToUser("sdipdev@gmail.com", "ROLE_USER");
			userService.addRoleToUser("will@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("jim@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_USER");

		};
	}
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
}
