package com.example.UserTaskService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Task {
    @Id

    private String taskId;
    private String taskName;
    private String discription;
    private String dueDate;
    private String taskPriority;
    private String taskStatus;
    private boolean archivedTasks;

    public Task(String taskName, String discription, String dueDate, String taskPriority, String taskStatus, boolean archivedTasks) {
        this.taskId = UUID.randomUUID().toString();;
        this.taskName = taskName;
        this.discription = discription;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.archivedTasks = archivedTasks;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public boolean isArchivedTasks() {
        return archivedTasks;
    }

    public void setArchivedTasks(boolean archivedTasks) {
        this.archivedTasks = archivedTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", discription='" + discription + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", taskPriority='" + taskPriority + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", archivedTasks=" + archivedTasks +
                '}';
    }
}
