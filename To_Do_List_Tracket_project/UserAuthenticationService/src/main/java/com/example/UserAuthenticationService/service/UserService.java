package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.excepion.InvalidCredentialsException;
import com.example.UserAuthenticationService.excepion.UserAlreadyExistsException;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByEmailIdAndPassword(String userId, String password) throws InvalidCredentialsException;
}
