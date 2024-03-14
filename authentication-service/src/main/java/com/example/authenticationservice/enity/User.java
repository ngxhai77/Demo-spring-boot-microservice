package com.example.authenticationservice.enity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
    @NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Nullable
    @Column(name = "userName", length = 30)
    @NotBlank(message = "userName isn't allow empty")
    private String userName;

    @Nullable
    @Column(name = "password", length = 255)
    @NotBlank(message = "password isn't allow empty")
    private String  password;

    @Nullable
    @Column(name = "fullName", length = 30)
    @NotBlank(message = "fullName isn't allow empty")
    private String fullName;

    @Nullable
    @Column(name = "phone", length = 13)
    private String phone;

    @Nullable
    @Column(name = "address", length = 80)
    private String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
        }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Set<Role> getRoles() {
            return roles;
    }
    public void setRoles(Set<Role> roles) {
            this.roles = roles;
    }


    public  void addRole(Role role) {
            this.roles.add(role);
        }

    }

