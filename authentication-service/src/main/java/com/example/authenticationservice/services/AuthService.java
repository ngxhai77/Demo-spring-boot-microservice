    package com.example.authenticationservice.services;


    import com.example.authenticationservice.enity.Role;
    import com.example.authenticationservice.enity.User;
    import com.example.authenticationservice.entities.RegisterRequest;
    import com.example.authenticationservice.entities.AuthResponse;
    import com.example.authenticationservice.entities.LoginRequest;
    import com.example.authenticationservice.entities.UserVO;
    import com.example.authenticationservice.repository.IRoleRepository;
    import com.example.authenticationservice.repository.IUserRepository;
    import com.example.authenticationservice.services.impl.IAuthService;
    import io.jsonwebtoken.*;
    import lombok.AllArgsConstructor;
    import org.mindrot.jbcrypt.BCrypt;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.HttpStatusCode;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.HttpClientErrorException;
    import org.springframework.web.client.RestTemplate;

    import java.util.List;
    import java.util.Set;

    @Service
    @AllArgsConstructor
    public class AuthService implements IAuthService {


        private final RestTemplate restTemplate;
        private final JwtUtil jwtUtil;
        private final AuthenticationManager authenticationManager;
        private IUserRepository userRepository ;
        private UserDetailsService userDetailService;
        private IRoleRepository roleRepository;



        @Override
        public ResponseEntity<Object> register(RegisterRequest request) {
            //check email exists in database
            var foundUser = userRepository.findByUserName(request.getUserName());
            var foundRole = roleRepository.findByRole(request.getRole());

            //check user exists in table user
            if(!foundUser.isPresent())
            {
                var user = User.builder()
                        .userName(request.getUserName())
                        .fullName(request.getFullName())
                        .address(request.getAddress())
                        .phone(request.getPhone())
                        .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                        .build();

                if (foundRole.isPresent()) {
                    // Nếu vai trò đã được tìm thấy, thêm vào đối tượng User
                    user.addRole(foundRole.get());
                } else {
                    // Nếu vai trò không tồn tại, bạn có thể xử lý nó tùy thuộc vào logic của bạn, ví dụ:
                     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Role not found");
                }

                userRepository.save(user);

                return ResponseEntity.ok().body("Register user successfully");
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Email already exists in the database");
        }


        @Override
        public AuthResponse login(LoginRequest request) {
            try {
                //send email and password from request to authenticationManager precess authentication
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUserName(),
                                request.getPassword()
                        )
                );
                //get info user
                var user = userRepository.findByUserName(request.getUserName()).orElseThrow();
                //generate token
                var jwtToken = jwtUtil.generateToken(user);
                return AuthResponse.builder()
                        .accessToken(jwtToken)
                        .build();
            }catch (AuthenticationException e) {
                // return AuthenticationResponse.builder().error("Invalid credentials").build();
                throw new RuntimeException("Authentication failed", e);
            }
        }


        //valid token return role
        @Override
        public ResponseEntity<Object> isValidToken(String token, String role) {
            try {
                //replace characters exists "Bearer"
                if(token == null || token.startsWith("Bearer ")){
                    token = token.substring(7);
                }

                //get userName of user
                String userName = jwtUtil.extractUsername(token);

                //get info of user by userName
                UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);

                //check token user expired
                if (jwtUtil.isTokenValid(token, userDetails)){
                    //get role of user
                    Claims claims = jwtUtil.getClaims(token);
                    List<String> roles = claims.get("role",List.class);

                    if (checkRole(roles, role)){
                        return ResponseEntity.status(HttpStatus.OK).body("ACCESS GRANTED");
                    }
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED ACCESS THIS API");
            }catch (HttpClientErrorException e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED access this API");
            }
        }
        public boolean checkRole(List<String> parentRoles, String subRole){
            for (String parentString : parentRoles) {
                if (parentString.contains(subRole)) {
                    return true;
                }
            }
            return false;
        }


    }




