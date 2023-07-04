package com.sumn.corews.controller;

import com.sumn.corews.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class CurrentUser {
    private OAuth2AuthorizedClientService authClientSrv;

    public CurrentUser(OAuth2AuthorizedClientService authClientSrv){
        this.authClientSrv = authClientSrv;
    }
    @GetMapping("/users")
    public ResponseEntity<UserDTO> getUserDetails(HttpServletRequest request){
       return new ResponseEntity<>((UserDTO) request.getAttribute("userDetails"), HttpStatus.OK);
    }

//    @GetMapping("/logout")
//    public String logout(Authentication authentication) throws ServletException {
//       if(authentication!=null){
//           OAuth2AuthorizedClient authClient = authClientSrv.loadAuthorizedClient(authentication.get)
//       }
//    }
}
