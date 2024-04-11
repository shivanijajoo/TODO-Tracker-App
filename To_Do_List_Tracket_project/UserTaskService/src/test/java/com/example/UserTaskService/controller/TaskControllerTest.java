package com.example.UserTaskService.controller;

import com.example.UserTaskService.domain.Task;
import com.example.UserTaskService.service.TaskServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TaskServiceImpl taskServiceImpl;
    @InjectMocks
    private TaskController taskController;
    private Task task1, task2;
    List<Task> taskList;
    private User user;

    @BeforeEach
    void setUp() {
        task1 = new Task("1","new challange start","first backend project","03feb","10feb","low","notcompleted",false);
        task2=new Task("2","second Task","u can try","11feb","25feb","mid","notcompleted",false);
        taskList = Arrays.asList(task1, task2);

    }




}
