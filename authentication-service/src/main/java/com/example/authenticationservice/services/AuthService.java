    package com.example.authenticationservice.services;


    import com.example.authenticationservice.entities.AuthRequest;
    import com.example.authenticationservice.entities.AuthResponse;
    import com.example.authenticationservice.entities.MsAuthRequest;
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

        public AuthResponse msAuthRequest(MsAuthRequest request) {
            // Thực hiện xác nhận vai trò và tạo mã thông báo
            String userId = request.getId(); // Hoặc bất kỳ cách nào để lấy userId từ request
            String role = request.getRole(); // Hoặc bất kỳ cách nào để lấy role từ request
            if (isValidRole(role)) {
                String accessToken = jwtUtil.generate(userId, role, "ACCESS");
                String refreshToken = jwtUtil.generate(userId, role, "REFRESH");
                return new AuthResponse(accessToken, refreshToken);
            } else {
                // Nếu vai trò không hợp lệ, có thể xử lý hoặc ném ra một ngoại lệ
                throw new IllegalArgumentException("Invalid role provided.");
            }
        }

//        private boolean isValidRole(String role) {
//
//        }
    }
