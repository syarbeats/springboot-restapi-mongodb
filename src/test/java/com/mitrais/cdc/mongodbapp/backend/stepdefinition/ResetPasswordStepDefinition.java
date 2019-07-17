package com.mitrais.cdc.mongodbapp.backend.stepdefinition;

import com.mitrais.cdc.mongodbapp.payload.AuthenticationResponse;
import com.mitrais.cdc.mongodbapp.payload.NewPasswordPayload;
import com.mitrais.cdc.mongodbapp.payload.ResponseEntityCustomV2;
import com.mitrais.cdc.mongodbapp.payload.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResetPasswordStepDefinition {

    RestTemplate restTemplate = new RestTemplate();

    @Given("User with username (.*) with password (.*) want to change his password")
    public void user_with_username_user_with_password_test_want_to_change_his_password(String username, String password) {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin123"));
        ResponseEntity<ResponseEntityCustomV2> response = restTemplate.exchange
                ("http://localhost:8080/api/find-user-by-username/"+username, HttpMethod.GET, null, ResponseEntityCustomV2.class);

        assertThat(true, is(response.getBody().getContents().isSuccess()));
        assertThat("User data was found", is(response.getBody().getContents().getMessage()));
        assertThat("user555", is(response.getBody().getContents().getData().getUsername()));
        assertThat(true, is(response.getBody().getContents().getData().isEnabled()));
    }

    @When("When user (.*) has changed his password to (.*) successfully")
    public void when_user_user_has_changed_his_password_to_test_successfully(String username, String password) {
        String id = "dXNlcjU1NQ==";
        NewPasswordPayload passwordPayload = new NewPasswordPayload(id, password);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<NewPasswordPayload> requestEntity = new HttpEntity<>(passwordPayload, requestHeaders);

        ResponseEntity<ResponseEntityCustomV2> loginResponse = restTemplate.exchange
                ("http://localhost:8080/api/reset", HttpMethod.POST, requestEntity, ResponseEntityCustomV2.class);


        assertThat(true, is(loginResponse.getBody().getContents().isSuccess()));
        assertThat("Change password process have done successfully", is(loginResponse.getBody().getMessage()));
        assertThat("user555", is(loginResponse.getBody().getContents().getData().getUsername()));
    }

    @Then("Username (.*) can login with new password (.*) successfully")
    public void username_user_can_login_with_new_password_test_successfully(String username, String password) {

        UserLogin user = new UserLogin(username, password);
        String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        HttpEntity<UserLogin> requestEntity = new HttpEntity<>(user, requestHeaders);

        ResponseEntity<AuthenticationResponse> loginResponse = restTemplate.exchange
                ("http://localhost:8080/api/auth", HttpMethod.POST, requestEntity, AuthenticationResponse.class);


        assertThat(true, is(loginResponse.getBody().getContents().isSuccess()));
        assertThat("You have login successfully", is(loginResponse.getBody().getContents().getMessage()));
        assertThat("user555", is(loginResponse.getBody().getContents().getData().getUsername()));
    }
}
