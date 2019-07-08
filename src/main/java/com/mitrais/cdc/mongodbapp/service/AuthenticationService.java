package com.mitrais.cdc.mongodbapp.service;

import com.mitrais.cdc.mongodbapp.payload.APIResponse;
import com.mitrais.cdc.mongodbapp.payload.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    public APIResponse login(UserLogin userLogin) {

        String username = userLogin.getUsername();
        String password = userLogin.getPassword();

        log.info("Username:", username);
        log.info("Password:", password);

        return new APIResponse(true, "You have login successfully", userLogin);
    }
}
