package com.example.userservice.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Aspect
@Data
public class MyAspect {

    @Autowired
    private RestTemplate restTemplate;

    //authorize role user
    @Before("@annotation(com.example.userservice.common.Author)")
    public ResponseEntity roleUser(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Author authorAnnotation = methodSignature.getMethod().getAnnotation(Author.class);
        String role = authorAnnotation.role();
        //get token from requestHeader
        String token = extractTokenFromHeader();

        String url = "http://localhost:8765/auth/valid-token?role=" + role;

        return callAuth(url, token);
    }
    //call api auth
    public ResponseEntity callAuth(String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    //get token from requestHeader
    private String extractTokenFromHeader() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("Authorization");
    }
}
