package com.suvodip.userservice.service;

import java.util.List;

import com.suvodip.userservice.exceptions.InvalidTokenException;
import com.suvodip.userservice.exceptions.UserAlreadyExistException;
import com.suvodip.userservice.models.Role;
import com.suvodip.userservice.models.User;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistException;
    Role saveRole (Role role);
    void addRoleToUser( String username,String roleName );
    User getUser(String username);
    List<User> getUsers();
    void deleteAllUsers();
    boolean verifyUser(String token) throws InvalidTokenException;
}
