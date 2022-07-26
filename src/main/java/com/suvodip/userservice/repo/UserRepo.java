package com.suvodip.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvodip.userservice.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
