package com.example.authenticationservice.enity;


import com.example.authenticationservice.repository.IRoleRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Optional;

@Entity
@AllArgsConstructor
@Data
@Table(name = "roles")
public class Role {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id" )
    private Integer id;

    @Column(name = "role" )
    private String role;


    public Role() {
    }

    public Role(String role ) {
        this.role = role ;
    }
    public Role(Integer id ) {
        super();
        this.id = id ;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.role;
    }

}
