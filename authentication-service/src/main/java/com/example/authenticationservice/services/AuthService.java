    package com.example.authenticationservice.services;


    import com.example.authenticationservice.entities.AuthRequest;
    import com.example.authenticationservice.entities.AuthResponse;
    import com.example.authenticationservice.entities.UserVO;
    import lombok.AllArgsConstructor;
    import org.mindrot.jbcrypt.BCrypt;
    import org.springframework.context.annotation.Bean;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    @Service
    @AllArgsConstructor
    public class AuthService {


        private final RestTemplate restTemplate;
        private final JwtUtil jwtUtil;

        public AuthResponse register(AuthRequest request){
            //do validation if user exists id DB
            request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

            String accessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
            String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");

            return new AuthResponse(accessToken,refreshToken);
        }
    }
