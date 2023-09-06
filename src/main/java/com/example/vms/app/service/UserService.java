package com.example.vms.app.service;


import com.example.vms.data.model.CartItem;
import com.example.vms.data.model.CustomUserDetails;
import com.example.vms.data.model.User;
import com.example.vms.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final HttpSession httpSession;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /* User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);*/
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

    public UserDetails loadUserById(String userId) {
        User user = userRepository.findById(Long.parseLong(userId));

        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new CustomUserDetails(user);
    }

        public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username).orElse(null);
//    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User save(User user) {
        user = userRepository.save(user);

        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }



    public void addProductToUserCart(long userId, CartItem product) {
        User user = userRepository.findById(userId);
        if (user != null) {
            List<CartItem> cartItems = user.getCartItems();
            cartItems.add(product);
            user.setCartItems(cartItems);
            userRepository.save(user);
        } else {
            // Handle user not found
        }
    }

}
