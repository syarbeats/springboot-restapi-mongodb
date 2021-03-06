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
    public String email;
    public String firstname;
    public String lastname;


   /* public User(ObjectId _id, String username, String password, boolean enabled, String role) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
*/
    public User(){

    }

    public User(String username, String password, boolean enabled, String role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public User(String username, String password, boolean enabled, String role, String email) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.email = email;
    }

    public User(String username, String password, boolean enabled, String role, String email, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

   /* public String get_id() {
        return this._id.toHexString();
    }*/

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

}
