package com.example.vms.controller.auth.payload;


import com.example.vms.app.mess.Message;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank(message = Message.USERNAME_KHONG_HOP_LE)
    @Length(min = 3, max = 20, message = Message.USERNAME_KHONG_HOP_LE)
    private String username;

    @Length(min = 6, max = 20, message = Message.PASSWORD_KHONG_HOP_LE)
    private String password;

    private String name;
    private String email;

    private String phone;
    private String imgAvatar;
}
