package com.example.vms.controller.user.payload;

import lombok.Data;

@Data
public class UpdateUserDto {
    private long id;

    private String username;

    private String email;

    private String phone;

    private String password;
}
