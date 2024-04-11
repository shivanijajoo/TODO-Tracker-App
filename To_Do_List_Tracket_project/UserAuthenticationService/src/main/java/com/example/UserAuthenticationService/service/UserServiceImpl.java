package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.excepion.InvalidCredentialsException;
import com.example.UserAuthenticationService.excepion.UserAlreadyExistsException;
import com.example.UserAuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getEmailId()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmailIdAndPassword(String emailId, String password) throws InvalidCredentialsException {
        System.out.println(emailId);
        System.out.println(password);
        User loggedInUser=userRepository.findByEmailIdAndPassword(emailId,password);
        if(loggedInUser==null){
            throw new InvalidCredentialsException();
        }
        return loggedInUser;
    }

}
