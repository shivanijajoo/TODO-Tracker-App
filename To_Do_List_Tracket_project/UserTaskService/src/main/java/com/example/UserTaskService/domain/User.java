package com.example.UserTaskService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {
    @Id
    private String emailId;
    private String userName;
    @Transient
    private String password;
    private long phoneNo;
    private int age;
    private List<Task> taskList;

    public User() {
    }

    public User(String emailId, String userName, String password, long phoneNo, int age, List<Task> taskList) {
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.phoneNo = phoneNo;
        this.age = age;
        this.taskList = taskList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo=" + phoneNo +
                ", age=" + age +
                ", taskList=" + taskList +
                '}';
    }
}
