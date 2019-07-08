package com.mitrais.cdc.mongodbapp.controller;

import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.service.UserService;
import com.mitrais.cdc.mongodbapp.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity UserRegister(@RequestBody User user){
        return ResponseEntity.ok(new Utility("User Registration", userService.UserRegistration(user)).getResponseData());
    }

    @RequestMapping(value="/update/user", method = RequestMethod.PATCH)
    public ResponseEntity UpdateUserData(@RequestBody User user){
        return ResponseEntity.ok(new Utility("Update User Data", userService.UpdateUserData(user)).getResponseData());
    }

    @RequestMapping(value="/delete/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity DeleteUserData(@PathVariable("username") String username){
        return ResponseEntity.ok(new Utility("Delete User Data", userService.DeleteUserByUsername(username)).getResponseData());
    }

    @RequestMapping(value="/find/user/{id}", method = RequestMethod.GET)
    public ResponseEntity DeleteUserData(@PathVariable("id") ObjectId id){
        return ResponseEntity.ok(new Utility("Find User Data", userService.FindUserById(id)).getResponseData());
    }

    @RequestMapping(value="/all-users", method = RequestMethod.GET)
    public ResponseEntity GetAllUsers(){
        return ResponseEntity.ok(new Utility("Find User Data", userService.GetAllUsers()).getResponseData());
    }
}
