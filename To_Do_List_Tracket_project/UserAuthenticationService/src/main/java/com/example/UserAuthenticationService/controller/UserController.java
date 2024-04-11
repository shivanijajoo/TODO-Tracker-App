package com.example.UserAuthenticationService.controller;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.excepion.InvalidCredentialsException;
import com.example.UserAuthenticationService.excepion.UserAlreadyExistsException;
import com.example.UserAuthenticationService.security.SecurityTokenGenerator;
import com.example.UserAuthenticationService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
//@RequestMapping(value="/api/v1",methods = RequestMethod.POST)
//@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;
    private ResponseEntity responseEntity;

    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/user")
    public ResponseEntity saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            User userData = userService.saveUser(user);
            responseEntity = new ResponseEntity(userData, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) throws InvalidCredentialsException {
        User loggedInUser = userService.getUserByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        if (loggedInUser == null)
            throw new InvalidCredentialsException();
        String token = securityTokenGenerator.createToken(user);
        Map<String,String> data=new HashMap<>();
        data.put("token",token);
        return new ResponseEntity(data, HttpStatus.OK);


    }


}
