package com.mitrais.cdc.mongodbapp.stepdefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.payload.Contents;
import com.mitrais.cdc.mongodbapp.payload.ResponseEntityCustom;
import com.mitrais.cdc.mongodbapp.utility.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAPIStepDefinition {

    private final String USERNAME_FOR_ID_5d22a9d168ed663ca092a574 = "user1";
    private final String ROLE_FOR_ID_5d22a9d168ed663ca092a574 = "ROLE_USER";
    private final String ID = "5d22a9d168ed663ca092a574";


    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Given("Register new user with username (.*) with password (.*) and role (.*) using register API")
    public void register_new_user_with_username_test_with_password_test_using_register_API(String username, String password, String role) {
        User user = new User(username, password, true, role);
        System.out.println("Password for "+username +" is:"+password+" Role:"+role);
        restTemplate.postForObject("http://localhost:8080/register", user, User.class);
    }

    @When("When user has been registered successfully")
    public void when_user_has_been_registered_successfully() {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin123"));
        ResponseEntity<ResponseEntityCustom> response = restTemplate.exchange
                ("http://localhost:8080/find-user-by-username/test", HttpMethod.GET, null, ResponseEntityCustom.class);

        Contents contents = response.getBody().getContents();
        User user = contents.getData();
        System.out.println("Username:"+user.getUsername());
    }

    @Then("user test with password test can invoke login APi successfully")
    public void user_test_with_password_test_can_invoke_login_APi_successfully() {

    }
}
