package com.example.UserTaskService.proxy;


import com.example.UserTaskService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthenticationService",url="localhost:8086")
public interface UserProxy {
    @PostMapping("/api/v1/user")
    public ResponseEntity saveUser(@RequestBody User user);
}
