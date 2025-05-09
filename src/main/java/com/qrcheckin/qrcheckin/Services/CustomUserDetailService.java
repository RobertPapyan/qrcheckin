package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    UserRepository userRepo;

    @Autowired
    public CustomUserDetailService(UserRepository userRepo){ this.userRepo = userRepo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email not found."));
    }

    public void refreshUserDetails () {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null) return;

        var email = auth.getName();

        var updatedUser = this.loadUserByUsername(email);

        var newAuth = new UsernamePasswordAuthenticationToken(updatedUser,updatedUser.getPassword(),updatedUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);

    }
}
