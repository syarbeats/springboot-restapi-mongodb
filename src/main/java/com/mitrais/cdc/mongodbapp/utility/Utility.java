package com.mitrais.cdc.mongodbapp.utility;

import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.repository.IUserRepository;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class Utility {

    private String message;
    private Object data;

    @Autowired
    private IUserRepository userRepository;

    public Utility(String message, Object data){
        this.message = message;
        this.data = data;

    }

    public Utility(){}

    public Map<Object, Object>  getResponseData(){
        Map<Object, Object> map = new HashMap<>();
        map.put("message", this.message);
        map.put("contents", this.data);

        return map;
    }



}
