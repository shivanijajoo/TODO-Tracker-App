package com.example.UserTaskService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT,reason = "User alreday exsist")
public class UserAlreadyExistsException extends Throwable {
}
