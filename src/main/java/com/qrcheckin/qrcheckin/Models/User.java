package com.qrcheckin.qrcheckin.Models;

import groovy.transform.builder.Builder;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(nullable = true)
    private String image;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private Boolean enabled;

    @Column(nullable = true)
    private String apiKey;

    @Column(name = "remember_token", nullable = true)
    private String rememberToken;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToMany (mappedBy = "proffessor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;

    public static Builder builder(){
        return new Builder();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    //User Builder
    public static class Builder {
        private final User user = new User();

        public User build() {
            return user;
        }

        public Builder name(String name) {
            user.setName(name);
            return this;
        }

        public Builder surname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public Builder image(String image) {
            user.setImage(image);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder enabled(Boolean enabled) {
            user.enabled = enabled;
            return this;
        }

        public Builder apiKey(String apiKey) {
            user.setApiKey(apiKey);
            return this;
        }

        public Builder rememberToken(String rememberToken) {
            user.setRememberToken(rememberToken);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            user.setUpdatedAt(updatedAt);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            user.setCreatedAt(createdAt);
            return this;
        }
    }

    @Override
    public String toString(){
        return "Email: " + this.getEmail() + ", name: " + this.getName() + ", surname: " + this.getSurname();
    }
}
