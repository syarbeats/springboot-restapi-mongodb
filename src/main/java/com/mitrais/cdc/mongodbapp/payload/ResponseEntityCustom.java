package com.mitrais.cdc.mongodbapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntityCustom {

    private Contents contents;
    private String message;
}
