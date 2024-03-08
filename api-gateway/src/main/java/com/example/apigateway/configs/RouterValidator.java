//package com.example.apigateway.configs;
//
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//@Service
//public class RouterValidator {
//
//    public static final List<String> openEndpoins = List.of(
//            "/auth/register",
//            "/auth/login"
//    );
//    public Predicate<ServerHttpRequest> isSecured =
//            request -> openEndpoins.stream()
//                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
//
//}
