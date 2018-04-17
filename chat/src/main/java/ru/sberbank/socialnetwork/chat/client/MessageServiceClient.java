package ru.sberbank.socialnetwork.chat.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("message-service")
@RequestMapping("/message/")
public interface MessageServiceClient {

    @GetMapping(value = "/{id}", consumes = "application/json")
    Message getMessage(@PathVariable("id") String id);

    @PostMapping(value = "/create")
    String addUser(@RequestParam("email") String email, @RequestParam("password") String password);
}
