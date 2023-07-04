package com.sumn.corews.config;

import com.sumn.corews.middleware.UserRetrieve;
import com.sumn.corews.dao.UserDAO;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private UserDAO userDAO;

    public InterceptorConfig(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new UserRetrieve(userDAO)).addPathPatterns("/**");
    }
}
