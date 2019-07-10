package com.mitrais.cdc.mongodbapp.payload;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class ContentsPayload {

    private ObjectId _id;
    private String username;
    private String password;
    private String role;
    private String email;
    private boolean enabled;
}
