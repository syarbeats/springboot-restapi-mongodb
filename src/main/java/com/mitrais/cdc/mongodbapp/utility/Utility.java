package com.mitrais.cdc.mongodbapp.utility;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Utility {

    private String message;
    private Object data;

    public Utility(String message, Object data){
        this.message = message;
        this.data = data;

    }

    public Map<Object, Object>  getResponseData(){
        Map<Object, Object> map = new HashMap<>();
        map.put("message", this.message);
        map.put("contents", this.data);

        return map;
    }
}
