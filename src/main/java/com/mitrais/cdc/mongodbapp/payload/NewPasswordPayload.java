package com.mitrais.cdc.mongodbapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordPayload {
    private String id;
    private String password;

    public NewPasswordPayload(String id, String password) {
        this.id = id;
        this.password = password;
    }
}


