package com.example.UserTaskService.service;

import com.example.UserTaskService.domain.Task;
import com.example.UserTaskService.domain.User;
import com.example.UserTaskService.exception.TaskAlreadyExistsException;
import com.example.UserTaskService.exception.TaskNotFoundException;
import com.example.UserTaskService.exception.UserAlreadyExistsException;
import com.example.UserTaskService.exception.UserNotFoundException;
import com.example.UserTaskService.proxy.UserProxy;
import com.example.UserTaskService.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private UserTaskRepository userTaskRepository;
    UserProxy userProxy;

    @Autowired
    public TaskServiceImpl(UserTaskRepository userTaskRepository, UserProxy userProxy) {
        this.userTaskRepository = userTaskRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User register(User user) throws UserAlreadyExistsException {
        if (userTaskRepository.findById(user.getEmailId()).isPresent())
            throw new UserAlreadyExistsException();
        //return userTaskRepository.save(user);
        User saveUser = userTaskRepository.save(user);
        if (!(saveUser.getEmailId().isEmpty())) {
            ResponseEntity r = userProxy.saveUser(user);
            System.out.println(r.getBody());
        }
        return saveUser;
    }

    @Override
    public User saveUserTaskToWishList(Task task, String emailId) throws TaskAlreadyExistsException, UserNotFoundException {
        User user = userTaskRepository.findById(emailId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.getTaskList() == null) {
            user.setTaskList(Arrays.asList(task));
        } else {
            List<Task> tasks = user.getTaskList();
            if (tasks.contains(task)) {
                throw new TaskAlreadyExistsException();
            }
            tasks.add(task);
            user.setTaskList(tasks);
        }
        return userTaskRepository.save(user);
    }

    @Override
    public List<Task> getAllUserTaskFromWishList(String emailId) throws UserNotFoundException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        return userTaskRepository.findById(emailId).get().getTaskList();
    }

    @Override
    public User deleteTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userTaskRepository.findById(emailId).get();
        List<Task> taskList = user.getTaskList();
        boolean isTaskPresent = taskList.removeIf(track -> track.getTaskId().equals(taskId));
        if (!isTaskPresent) {
            throw new TaskNotFoundException();
        }
        // delete the user details specified
        user.setTaskList(taskList);
        return userTaskRepository.save(user);

        /*User user=userTaskRepository.findById(emailId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        List<Task>tasks=user.getTaskList();
        Task taskToDelete=null;
        for (Task task:tasks) {
            if (task.getTaskId().equals(taskId)) {
                taskToDelete = task;
                break;
            }
        }
        if (taskToDelete==null){
            throw new TaskNotFoundException();
        }
        tasks.remove(taskToDelete);
        userTaskRepository.save(user);

        return user;



        */
    }

    @Override
    public User updateUserTaskWishListWithGivenTask(String emailId, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userTaskRepository.findById(emailId).get();
        List<Task> taskList = user.getTaskList();

        Task existingTask = null;
        for (Task t : taskList) {
            if (t.getTaskId().equals(task.getTaskId())) {
                existingTask = t;
            }
        }
        if (existingTask == null) {
            throw new TaskNotFoundException();
        }
        if (existingTask.equals(task)) {
            throw new TaskAlreadyExistsException();
        }
        taskList.remove(existingTask);
        taskList.add(task);
        return userTaskRepository.save(user);
    }

    @Override
    public User completeTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userTaskRepository.findById(emailId).get();
        List<Task> taskList = user.getTaskList();

        Task taskToComplete = findTaskById(taskList, taskId);
        if (taskToComplete == null) {
            throw new TaskNotFoundException();
        }
        taskToComplete.setTaskStatus("Completed");

        return userTaskRepository.save(user);
    }

    @Override
    public User archiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userTaskRepository.findById(emailId).get();
        List<Task> taskList = user.getTaskList();

        Task taskToArchive = findTaskById(taskList, taskId);
        if (taskToArchive == null) {
            throw new TaskNotFoundException();
        }

        // Set the task as archived
        taskToArchive.setArchivedTasks(true);

        return userTaskRepository.save(user);
    }




    /*@Override
    public User unarchiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
        // Check if the user exists
        if (userTaskRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        // Retrieve the user and their task list
        User user = userTaskRepository.findById(emailId).get();
        List<Task> taskList = user.getTaskList();

        // Find the task to unarchive by its ID
        Task taskToUnarchive = findTaskById(taskList, taskId);

        // If the task is not found, throw an exception
        if (taskToUnarchive == null) {
            throw new TaskNotFoundException();
        }

        // Unarchive the task by setting its archivedTasks flag to false
        taskToUnarchive.setArchivedTasks(false);

        // Save the updated user with the unarchived task
        return userTaskRepository.save(user);
    }
*/

    // Helper method to find a non-archived task by its ID
    private Task findTaskById(List<Task> taskList, String taskId) {
        return taskList.stream()
                .filter(task -> task.getTaskId().equals(taskId) && !task.isArchivedTasks())
                .findFirst()
                .orElse(null);
    }




/*@Override
public User archiveTask(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
    if(userTaskRepository.findById(emailId).isEmpty()){
        throw new UserNotFoundException();
    }

    User user = userTaskRepository.findById(emailId).get();
    List<Task> taskList = user.getTaskList();

    boolean isTaskPresent = taskList.removeIf(task -> task.getTaskId().equals(taskId));
    if(!isTaskPresent){
        throw new TaskNotFoundException();
    }
    user.setTaskList(taskList);
    return userTaskRepository.save(user);

}
    private User getUserByEmail(String emailId) throws UserNotFoundException {
        return userTaskRepository.findById(emailId)
                .orElseThrow(UserNotFoundException::new);
    }
    private Task findTaskById(List<Task> taskList, String taskId) {
        for (Task task : taskList) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public User markTaskAsCompleted(String emailId, String taskId) throws TaskNotFoundException, UserNotFoundException {
        User user = getUserByEmail(emailId);
        List<Task> taskList = user.getTaskList();

        Task taskToComplete = findTaskById(taskList, taskId);

        if (taskToComplete == null) {
            throw new TaskNotFoundException();
        }

        taskToComplete.setTaskStatus("completed");
        user.setTaskList(taskList);
        return userTaskRepository.save(user);
    }*/

}
