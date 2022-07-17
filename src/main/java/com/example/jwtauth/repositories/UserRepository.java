package com.example.jwtauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jwtauth.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
}
