package com.example.jwtauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jwtauth.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String roleName);
}
