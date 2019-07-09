package com.mitrais.cdc.mongodbapp.payload;

import com.mitrais.cdc.mongodbapp.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contents {

    private boolean success;
    private String message;
    private User data;
}
