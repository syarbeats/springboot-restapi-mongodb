package com.mitrais.cdc.mongodbapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    public String Message;
    public AuthenticationPayload Data;
}
