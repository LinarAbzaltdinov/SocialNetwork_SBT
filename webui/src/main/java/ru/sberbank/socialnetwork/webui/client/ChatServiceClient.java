package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("chat-service")
@RequestMapping("/chat/")
public interface ChatServiceClient {
    @GetMapping(value = "/all", consumes = "application/json")
    String getAllChats();

    @GetMapping(value = "/{id}", consumes = "application/json")
    String getChat(@PathVariable("id") String id);

    @PostMapping(value = "/create")
    String addUser(@RequestParam("email") String email, @RequestParam("password") String password);
}
