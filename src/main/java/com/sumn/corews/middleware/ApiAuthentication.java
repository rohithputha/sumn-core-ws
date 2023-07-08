package com.sumn.corews.middleware;

import com.sumn.corews.config.AppConfig;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiAuthentication implements HandlerInterceptor {

    private AppConfig appConfig;
    public ApiAuthentication(AppConfig appConfig){
        this.appConfig = appConfig;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(appConfig.isApiAuthEnable()){
            String apiAuthKey = (String) request.getHeader("apiAuthKey");
            System.out.println(apiAuthKey);
            if(apiAuthKey!=null && apiAuthKey.equals(appConfig.getApiAuthKey())){
                return true;
            }
            return false;
        }
        return true;
    }
}
