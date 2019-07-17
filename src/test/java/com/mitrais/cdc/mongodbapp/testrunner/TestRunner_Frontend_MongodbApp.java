package com.mitrais.cdc.mongodbapp.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features= "features/frontend", glue= "com.mitrais.cdc.mongodbapp.frontend.stepdefinition")
public class TestRunner_Frontend_MongodbApp {
}
