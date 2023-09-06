package com.example.vms.controller.helper;

import com.example.vms.app.service.UserService;
import com.example.vms.data.model.CartItem;
import com.example.vms.data.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.vms.controller.CartController.cartItems;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CartHelper {
    private final UserService userService;
    private final List<CartItem> cartItemList = new ArrayList<>();

    public boolean deleteCartItems(Long idProduct){

        CartItem itemRemove = null;
        System.out.println("cartItemList "+ cartItems);

        for (CartItem cartItem : cartItems){
            System.out.println("cartItem "+ cartItem);
            if (cartItem.getIdProduct().equals(idProduct)){

                itemRemove = cartItem;
                System.out.println("cartItem itemRemove "+ itemRemove);
                break;
            }
        }
        if(itemRemove!= null){
            cartItems.remove(itemRemove);
            return true;

        }else {
            return false;
        }

    }


    public void addCartForUser(String username, List<CartItem> cartItems){
        System.out.println("codeKoCanTest username " + username);
        User user = userService.findByUsername(username);
        System.out.println("codeKoCanTest user " + user);
        if (user != null) {
            for (CartItem cartItem : cartItems) {
                cartItem.setUserId(user); // Đặt user cho từng cartItem
            }
            if (user.getCartItems() == null) {
                user.setCartItems(new ArrayList<>());
            }
            user.getCartItems().addAll(cartItems);
            userService.saveUser(user);
        }
    }
}
