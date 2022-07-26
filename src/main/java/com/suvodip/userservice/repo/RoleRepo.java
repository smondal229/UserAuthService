package com.suvodip.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suvodip.userservice.models.Role;

public interface RoleRepo  extends JpaRepository<Role, Long> {
    Role findByName(String  name);

}
