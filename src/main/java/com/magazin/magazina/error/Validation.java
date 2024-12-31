package com.magazin.magazina.error;

import com.magazin.magazina.models.Role;

public class Validation {

    public Role ValidateRole(String role) {

        try {
            return Role.valueOf(role.toUpperCase());
        }catch(IllegalArgumentException ex) {
            return Role.USER;
        }
    }
}
