package ru.sberbank.socialnetwork.webui.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor  implements HandlerInterceptor {
    public static final String AUTH_COOKIE = "Authorization";

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object o, Exception e) throws Exception {

    }
}