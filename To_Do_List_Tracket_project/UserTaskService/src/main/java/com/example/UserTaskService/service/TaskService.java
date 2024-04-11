package com.example.UserTaskService.service;

import com.example.UserTaskService.domain.Task;
import com.example.UserTaskService.domain.User;
import com.example.UserTaskService.exception.TaskAlreadyExistsException;
import com.example.UserTaskService.exception.TaskNotFoundException;
import com.example.UserTaskService.exception.UserAlreadyExistsException;
import com.example.UserTaskService.exception.UserNotFoundException;

import java.util.List;

public interface TaskService {
    User register(User user)throws UserAlreadyExistsException;
    User saveUserTaskToWishList(Task task, String emailId) throws TaskAlreadyExistsException, UserNotFoundException;
    List<Task> getAllUserTaskFromWishList(String emailId) throws Exception, UserNotFoundException;
    User deleteTask(String emailId,String taskId) throws TaskNotFoundException, UserNotFoundException;
    User updateUserTaskWishListWithGivenTask(String emailId, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException;
    //User archiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException;
    //User markTaskAsCompleted(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException;
    User completeTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException;

    //User archiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException;
    User archiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException;

    //User unarchiveTask(String emailId, String taskId) throws UserNotFoundException, TaskNotFoundException ;
        // Logic to unarchive the task


}

