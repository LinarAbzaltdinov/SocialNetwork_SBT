package ru.sberbank.socialnetwork.webui.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.socialnetwork.webui.client.ChatServiceClient;
import ru.sberbank.socialnetwork.webui.models.UserInfo;
import ru.sberbank.socialnetwork.webui.services.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AddGroupToModelInterceptor implements HandlerInterceptor {

    @Autowired
    private ChatServiceClient chatServiceClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {
        final Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String groupId = pathVariables.get("groupId");
        if (groupId == null || modelAndView == null) {
            return;
        }
        modelAndView.addObject("group", chatServiceClient.getGroupById(groupId));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object o, Exception e) throws Exception {

    }
}
