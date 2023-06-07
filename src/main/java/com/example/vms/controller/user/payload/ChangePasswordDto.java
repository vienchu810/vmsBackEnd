package com.example.vms.controller.user.payload;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private long userId;

    private String newPassword;
}
