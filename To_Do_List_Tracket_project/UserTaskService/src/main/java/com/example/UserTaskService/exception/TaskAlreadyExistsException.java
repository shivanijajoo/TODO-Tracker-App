package com.example.UserTaskService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT,reason = "Task alreday exsist")
public class TaskAlreadyExistsException extends Throwable {
}
