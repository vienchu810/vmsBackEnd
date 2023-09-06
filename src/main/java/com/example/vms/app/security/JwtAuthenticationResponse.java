package com.example.vms.app.security;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Object user;

    public JwtAuthenticationResponse(String accessToken, Object user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
