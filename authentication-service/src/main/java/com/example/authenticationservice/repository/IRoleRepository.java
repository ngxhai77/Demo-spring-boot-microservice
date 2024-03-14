package com.example.authenticationservice.repository;

import com.example.authenticationservice.enity.Role;
import com.example.authenticationservice.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}
