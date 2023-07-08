package com.sumn.corews.config;

import com.google.api.client.util.Value;
//import com.sumn.corews.middleware.ApiAuthentication;
import com.sumn.corews.middleware.UserRetrieve;
import com.sumn.corews.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private UserDAO userDAO;

//    private AppConfig appConfig;

    public InterceptorConfig(UserDAO userDAO){
        this.userDAO = userDAO;
//        this.appConfig = appConfig;
    }

    public void addInterceptors(InterceptorRegistry registry){

//        registry.addInterceptor(new ApiAuthentication(appConfig)).addPathPatterns("/**");
        registry.addInterceptor(new UserRetrieve(userDAO)).addPathPatterns("/**");
    }
}
