package com.mitrais.cdc.mongodbapp.stepdefinition;

import com.mitrais.cdc.mongodbapp.payload.ActivatePayload;
import com.mitrais.cdc.mongodbapp.payload.AuthenticationResponse;
import com.mitrais.cdc.mongodbapp.payload.ResponseEntityCustomV2;
import com.mitrais.cdc.mongodbapp.payload.UserLogin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SActivateNewUserStepDefinition {

    RestTemplate restTemplate = new RestTemplate();

    @Given("New user with username (.*) with password (.*) has been registered successfully")
    public void new_user_with_username_test_with_password_test_has_been_registered_successfully(String username, String password) {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin123"));
        ResponseEntity<ResponseEntityCustomV2> response = restTemplate.exchange
                ("http://localhost:8080/api/find-user-by-username/"+username, HttpMethod.GET, null, ResponseEntityCustomV2.class);

        assertThat(true, is(response.getBody().getContents().isSuccess()));
        assertThat("User data was found", is(response.getBody().getContents().getMessage()));
        assertThat("test", is(response.getBody().getContents().getData().getUsername()));
        assertThat(false, is(response.getBody().getContents().getData().isEnabled()));
    }

    @When("When user (.*) has been activated successfully")
    public void when_user_has_been_activated_successfully(String username) {
        String id = "dGVzdA==";
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin123"));
        ResponseEntity<ActivatePayload> response = restTemplate.exchange
                ("http://localhost:8080/api/activate?id="+id, HttpMethod.GET, null, ActivatePayload.class);

        assertThat("test", is(response.getBody().getContents()));
        assertThat("Your account has been activated", is(response.getBody().getMessage()));
    }

    @Then("Username (.*) with password (.*) can be used to login successfully")
    public void username_test_with_password_test_can_be_used_to_login_successfully(String username, String password) {

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
        assertThat("test", is(loginResponse.getBody().getContents().getData().getUsername()));
    }
}
