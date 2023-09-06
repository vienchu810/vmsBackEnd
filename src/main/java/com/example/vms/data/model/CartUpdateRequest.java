package com.example.vms.data.model;

import lombok.Data;

@Data
public class CartUpdateRequest {
    private CartItem product;
    private User user;

}
