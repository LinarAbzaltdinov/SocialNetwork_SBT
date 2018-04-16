package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient("users-service")
@RequestMapping("/users")
public interface UserServiceClient {
    @GetMapping(value = "/all", consumes = "application/json")
    String getAllUsers();

    @GetMapping(value = "/{id}", consumes = "application/json")
    String getUser(@PathVariable("id") String id);

    @PostMapping(value = "/create")
    String addUser(@RequestParam("email") String email, @RequestParam("password") String password);
}
