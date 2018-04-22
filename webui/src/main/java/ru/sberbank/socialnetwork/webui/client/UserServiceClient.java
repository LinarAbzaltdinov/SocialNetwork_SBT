package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.util.List;

@FeignClient("users-service")
@RequestMapping("/users")
public interface UserServiceClient {
    @GetMapping(value = "/all", consumes = "application/json")
    List<UserInfo> getAllUsers();

    @GetMapping(value = "/one", consumes = "application/json")
    UserInfo getUser(@RequestHeader("Authorization") String authToken);

    @PostMapping(value = "/create")
    String addUser(@RequestParam("email") String email, @RequestParam("password") String password);
}