package ru.sberbank.socialnetwork.webui.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.socialnetwork.webui.client.AuthServiceClient;
import ru.sberbank.socialnetwork.webui.client.UserServiceClient;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

public class AddUserInfoToModelInterceptor implements HandlerInterceptor {
    public static final String AUTH_COOKIE = "Authorization";

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie authCookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(AUTH_COOKIE))
                .findFirst()
                .orElse(null);
        if (authCookie == null || authCookie.getValue() == null) {
            return;
        }
        String authToken = authCookie.getValue();
        UserInfo user = userInfoService.getUser(authToken);
        modelAndView.addObject("user", user);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
