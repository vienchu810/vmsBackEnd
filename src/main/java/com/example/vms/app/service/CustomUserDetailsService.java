package com.example.vms.app.service;

import com.example.vms.data.model.CustomUserDetails;
import com.example.vms.data.model.User;
import com.example.vms.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpSession;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        System.out.println("user " + user);
        if (user== null){
            throw new UsernameNotFoundException("User not found with username: " + username);

        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        System.out.println("customUserDetails.getUsername() "+ customUserDetails.getUsername());
        httpSession.setAttribute("user_id", customUserDetails.getUsername());

        return customUserDetails;

    }
}
