package com.mitrais.cdc.mongodbapp.stepdefinition;

import com.mitrais.cdc.mongodbapp.model.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserStepDefinition {

    User user = new User();

    @Given("^that the user (.*) is given a task to clear (.*) certification exam$")
    public void certificationName(String username, String role) throws Throwable {
        user.setUsername(username);
        user.setRole(role);
    }

    @When("^(.*) got (\\d+) marks in exam$")
    public void gotMarks(String name, int marks) throws Throwable {
        user.setPassword(name);
    }

    @Then("^(.*) is known as (.*) certified$")
    public void certifiedYes(String username, String certification) throws Throwable {
        assertThat(username, is(user.getUsername()));
        assertThat(user.getRole(), equalTo("Java"));
    }
}
