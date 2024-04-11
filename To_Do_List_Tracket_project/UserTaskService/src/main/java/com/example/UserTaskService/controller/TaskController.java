package com.example.UserTaskService.controller;

import com.example.UserTaskService.domain.Task;
import com.example.UserTaskService.domain.User;
import com.example.UserTaskService.exception.TaskAlreadyExistsException;
import com.example.UserTaskService.exception.TaskNotFoundException;
import com.example.UserTaskService.exception.UserAlreadyExistsException;
import com.example.UserTaskService.exception.UserNotFoundException;
import com.example.UserTaskService.service.TaskService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v2")
//@CrossOrigin(origins = "http://localhost:4200/")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return new ResponseEntity<>(taskService.register(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("user/task")
    public ResponseEntity<?> saveTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            Claims claim = (Claims) request.getAttribute("claims");
            String emailId = claim.getSubject();
            return new ResponseEntity<>(taskService.saveUserTaskToWishList(task, emailId), HttpStatus.CREATED);
        } catch (TaskAlreadyExistsException e) {
            return new ResponseEntity<>("Task already exists in wishlist", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/tasks")
    public ResponseEntity<?> getAllTasks(HttpServletRequest request) {
        try {
            Claims claim = (Claims) request.getAttribute("claims");
            String emailId = claim.getSubject();
            List<Task> tasks = taskService.getAllUserTaskFromWishList(emailId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving tasks", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("user/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(taskService.deleteTask(emailId, taskId), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(taskService.updateUserTaskWishListWithGivenTask(emailId, task), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TaskAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/task/complete/{taskId}")
    public ResponseEntity<?> completeTask(@PathVariable String taskId, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(taskService.completeTask(emailId, taskId), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/task/archive/{taskId}")
    public ResponseEntity<?> archiveTask(@PathVariable String taskId, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(taskService.archiveTask(emailId, taskId), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

       /* @PutMapping("user/task/unarchive/{taskId}")
        public ResponseEntity<?> unarchiveTask(@PathVariable String taskId, HttpServletRequest request) {
            try {
                Claims claims = (Claims) request.getAttribute("claims");
                String emailId = claims.getSubject();
                return new ResponseEntity<>(taskService.unarchiveTask(emailId, taskId), HttpStatus.OK);
            } catch (UserNotFoundException | TaskNotFoundException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }*/




   /*@PutMapping("/archiveTask/{emailId}/{taskId}")
    public ResponseEntity<User> archiveTask(@PathVariable String emailId, @PathVariable String taskId) {
        try {
            User updatedUser = taskService.archiveTask(emailId, taskId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (TaskNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/complete")
    public ResponseEntity<?> markTaskAsCompleted(@RequestBody Map<String, String> request) {
        String emailId = request.get("emailId");
        String taskId = request.get("taskId");

        try {
            User updatedUser = taskService.markTaskAsCompleted(emailId, taskId);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }*/






/*------------------------------------------------------------------------------------------------*/
    /*
    private TaskService taskService;
    private ResponseEntity<?> responseEntity;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            responseEntity = new ResponseEntity<>(taskService.register(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

    @PostMapping("user/task")
    public ResponseEntity<?> saveTask(@RequestBody Task task, HttpServletRequest request) throws UserNotFoundException {
        try {
            System.out.println("header" + request.getHeader("Authorization")); // key
            Claims claim = (Claims) request.getAttribute("claims"); // key
            System.out.println("emailId from claims ::" + claim.getSubject());
            String emailId = claim.getSubject();
            System.out.println("UserInfo:" + emailId);
            responseEntity = new ResponseEntity<>(taskService.saveUserTaskToWishList(task, emailId), HttpStatus.CREATED);
        } catch (TaskAlreadyExistsException e) {
            return new ResponseEntity<>("Task already exists in wishlist", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("user/tasks")
    public ResponseEntity<?> getAllTasks(HttpServletRequest request) {
        try {
            Claims claim = (Claims) request.getAttribute("claims");
            String emailId = claim.getSubject();
            List<Task> tasks = taskService.getAllUserTaskFromWishList(emailId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving tasks", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("user/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId,HttpServletRequest request) throws UserNotFoundException,TaskNotFoundException {
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("emailID from claims :: " + claims.getSubject());
        String emailID = claims.getSubject();
        System.out.println("emailId :: "+emailID);
        try {
            responseEntity = new ResponseEntity<>(taskService.deleteTask(emailID, taskId), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException m) {
            throw new TaskNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }
    @PutMapping("user/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task, HttpServletRequest request) throws TaskNotFoundException {
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("emailId from claims :: " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("Email Id :: "+emailId);

            responseEntity = new ResponseEntity<>(taskService.updateUserTaskWishListWithGivenTask(emailId, task), HttpStatus.OK);
        }catch (UserNotFoundException | TaskNotFoundException m) {
            throw new TaskNotFoundException();
        }catch (Exception | TaskAlreadyExistsException e){
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/archive")
    public ResponseEntity<User> archiveTask(@RequestParam String emailId, @RequestParam String taskId) {
        try {
            Claims claims = (Claims) responseEntity.getAttribute("claims");
            emailId = claims.getSubject();

            User updatedUser = taskService.archiveTask(emailId, taskId);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/










}






