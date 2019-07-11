package com.mitrais.cdc.mongodbapp.controller;

import com.mitrais.cdc.mongodbapp.payload.UserLogin;
import com.mitrais.cdc.mongodbapp.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/auth", method= RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserLogin userLogin){

        log.info("Authentication Process....");
        Map<Object, Object> map = new HashMap<>();
        map.put("Message", "Login Testing");
        map.put("Contents", authenticationService.login(userLogin));

        return ResponseEntity.ok(map);
    }
}
