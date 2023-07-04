package com.sumn.corews.middleware;

import com.sumn.corews.dto.UserDTO;
import com.sumn.corews.dao.UserDAO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRetrieve implements HandlerInterceptor {
    private UserDAO userDAO;

    public UserRetrieve(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Authentication authentication = (Authentication) request.getUserPrincipal();
        DefaultOAuth2User userDetails  = (DefaultOAuth2User) authentication.getPrincipal();
        UserDTO currentUserDTO = UserDTO.builder()
                .email(userDetails.getAttributes().get("email").toString())
                .name(userDetails.getAttributes().get("name").toString())
                .userId(userDetails.getAttributes().get("sub").toString())
                .build();
        userDAO.upsertUser(currentUserDTO);
        request.setAttribute("userDetails", currentUserDTO);
        return true;
    }

}
