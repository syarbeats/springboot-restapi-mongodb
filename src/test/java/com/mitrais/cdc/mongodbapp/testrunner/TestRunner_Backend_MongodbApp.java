package com.mitrais.cdc.mongodbapp.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features= "features/backend", glue= "com.mitrais.cdc.mongodbapp.backend.stepdefinition")
public class TestRunner_Backend_MongodbApp {
}
