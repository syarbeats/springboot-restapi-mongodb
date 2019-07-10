package com.mitrais.cdc.mongodbapp.stepdefinition;


import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.payload.AuthenticationResponse;

import com.mitrais.cdc.mongodbapp.payload.ResponseEntityCustom;
import com.mitrais.cdc.mongodbapp.payload.UserLogin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.tomcat.util.codec.binary.Base64;

import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestAPIStepDefinition {

    private final String USERNAME_FOR_ID_5d22a9d168ed663ca092a574 = "user1";
    private final String ROLE_FOR_ID_5d22a9d168ed663ca092a574 = "ROLE_USER";
    private final String ID = "5d22a9d168ed663ca092a574";
    ResponseEntity<ResponseEntityCustom> postResponse;

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

    @Given("Register new user with username (.*) with password (.*) email (.*) and role (.*) using register API")
    public void register_new_user_with_username_test_with_password_test_using_register_API(String username, String password, String email, String role) {
        User user = new User(username, password, true, role, email);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> requestEntity = new HttpEntity<>(user, requestHeaders);
        postResponse = restTemplate.exchange
                ("http://localhost:8080/register", HttpMethod.POST, requestEntity, ResponseEntityCustom.class);

    }

    @When("When user has been registered successfully")
    public void when_user_has_been_registered_successfully() {
        assertThat(true, is(postResponse.getBody().getContents().isSuccess()));
        assertThat("User has been registered successfully", is(postResponse.getBody().getContents().getMessage()));
        assertThat("test", is(postResponse.getBody().getContents().getData().getUsername()));
        assertThat("ROLE_ADMIN", is(postResponse.getBody().getContents().getData().getRole()));
    }

    @Then("We can populate data for username (.*) using find-user-by-username api")
    public void we_can_populate_data_for_username_test_using_find_user_by_username_api(String username) {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin123"));
        ResponseEntity<ResponseEntityCustom> response = restTemplate.exchange
                ("http://localhost:8080/find-user-by-username/"+username, HttpMethod.GET, null, ResponseEntityCustom.class);

        assertThat(true, is(postResponse.getBody().getContents().isSuccess()));
        assertThat("User has been registered successfully", is(postResponse.getBody().getContents().getMessage()));
        assertThat("test", is(postResponse.getBody().getContents().getData().getUsername()));
        assertThat("ROLE_ADMIN", is(postResponse.getBody().getContents().getData().getRole()));
    }

    @Then("user (.*) with password (.*) can invoke login APi successfully")
    public void user_test_with_password_test_can_invoke_login_APi_successfully(String username, String password) {
        UserLogin user = new UserLogin(username, password);
        String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        HttpEntity<UserLogin> requestEntity = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<AuthenticationResponse> loginResponse = restTemplate.exchange
                ("http://localhost:8080/auth", HttpMethod.POST, requestEntity, AuthenticationResponse.class);


        assertThat(true, is(loginResponse.getBody().getContents().isSuccess()));
        assertThat("You have login successfully", is(loginResponse.getBody().getContents().getMessage()));
        assertThat("test", is(loginResponse.getBody().getContents().getData().getUsername()));
    }
}
