package ru.sberbank.socialnetwork.webui.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(HttpSession httpSession) {
        Object sessionUserId = httpSession.getAttribute("userId");
        if (sessionUserId == null) {
            return "redirect:/login";
        } else {
            return "redirect:/profile";
        }
    }
}
