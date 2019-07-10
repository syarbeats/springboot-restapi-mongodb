package com.mitrais.cdc.mongodbapp.payload;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class UserPayload {

    private ObjectId _id;
    private String username;
}
