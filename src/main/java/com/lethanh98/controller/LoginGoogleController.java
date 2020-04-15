package com.lethanh98.controller;

import com.lethanh98.config.teamplate.RestAll;
import com.lethanh98.dto.request.LoginGoogleRequestDto;
import com.lethanh98.dto.response.LoginGoogleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
public class LoginGoogleController {
    @Autowired
    RestAll restAll;

    @GetMapping("/login_google")
    public LoginGoogleResponseDto get(@RequestParam("code") String code) {
        LoginGoogleRequestDto checkRequestDto = LoginGoogleRequestDto.builder()
                .code(code)
                .clientId("601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com")
                .clientSecret("8oWBL4fnEYX4_sspOvct9YW0")
                .redirectUri("http://localhost:8080/login/login_google")
                .grantType("authorization_code")
                .build();
        HttpEntity<LoginGoogleRequestDto> requestEntityRemit = new HttpEntity<>(checkRequestDto);

        ResponseEntity<LoginGoogleResponseDto> response = restAll.restTemplate().exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntityRemit, new ParameterizedTypeReference<LoginGoogleResponseDto>() {
        });
        LoginGoogleResponseDto loginGoogleResponseDto = response.getBody();
        loginGoogleResponseDto.getUrl().put("Lấy thông tin user", "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + loginGoogleResponseDto.getAccessToken());
        return loginGoogleResponseDto;
    }

    @GetMapping("")
    public Map<String, String> login() {
        Map<String, String> url = new HashMap<>();
        url.put("Url login", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=email&redirect_uri=http://localhost:8080/login/login_google&response_type=code");
        return url;
    }
}
