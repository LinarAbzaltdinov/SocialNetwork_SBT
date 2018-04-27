package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.socialnetwork.webui.models.UserInfo;

import java.util.List;

@FeignClient("gateway")
@RequestMapping("/users")
public interface UserServiceClient {
    @GetMapping(value = "/all", consumes = "application/json")
    List<UserInfo> getAllUsers();

    @GetMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<UserInfo> getUser(@PathVariable("id") String userId);

    @PostMapping(value = "/getByEmail", consumes = "application/json")
    ResponseEntity<UserInfo> getUserByEmail(@RequestParam("email") String email);

    @PostMapping(value = "/create")
    ResponseEntity<String> createUser(@RequestParam("email") String email, @RequestParam("password") String password);

    @PostMapping(value = "/login")
    Boolean login(@RequestParam("email") String email, @RequestParam("password") String password);

    @PutMapping(value = "/update")
    ResponseEntity<UserInfo> editUser(@RequestBody UserInfo updatedUser);

}