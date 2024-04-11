package com.example.UserAuthenticationService.excepion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND,reason = "User not found")
public class InvalidCredentialsException extends Throwable {
}
