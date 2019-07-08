package com.mitrais.cdc.mongodbapp.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class User {

    @Id
    ObjectId _id;

    public String username;
    public String password;
    public boolean enabled;
    public String role;


    public User(ObjectId _id, String username, String password, boolean enabled, String role) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public User(){

    }

    public User(String username, String password, boolean enabled, String role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
