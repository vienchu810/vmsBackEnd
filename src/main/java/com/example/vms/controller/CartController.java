package com.example.vms.controller;

import com.example.vms.app.mess.Message;
import com.example.vms.controller.helper.CartHelper;
import com.example.vms.data.model.CartItem;
import com.example.vms.data.repository.CartRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private final CartRepository cartItemRepository;

    private final CartHelper cartHelper;

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }

    public static List<CartItem> cartItems = new ArrayList<>();

    @GetMapping("/getCartItems/{userId}")

    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        System.out.println("codeKoCanTest userId: " + userId);
        cartItems = getCartItemsByUserId(userId);
        if (cartItems != null) {
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delCartItems/{idProduct}")
    public ResponseEntity delCartItem(@PathVariable Long idProduct) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(idProduct);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItemRepository.delete(cartItem);
            return Response.data(Message.XOA_THANH_CONG);

        }else {
            return Response.data(Message.VUI_LONG_THU_LAI);
        }

    }
}


