package com.universidad.authentication.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponse {
    private String userId;
    private String email;

    public RegistrationResponse(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}

