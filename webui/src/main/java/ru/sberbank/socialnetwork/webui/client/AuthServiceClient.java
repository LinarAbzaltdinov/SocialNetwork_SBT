package ru.sberbank.socialnetwork.webui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.socialnetwork.webui.models.Credentials;

@FeignClient("auth-service")
@RequestMapping("/auth")
public interface AuthServiceClient {
    @PostMapping(value = "/check")
    Boolean checkToken(@RequestParam("authToken") String authToken);

    @PostMapping(value = "/login")
    String login(@RequestParam("credentials") Credentials credentials);
}
