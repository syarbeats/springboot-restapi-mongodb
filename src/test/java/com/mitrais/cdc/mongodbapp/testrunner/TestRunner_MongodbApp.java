package com.mitrais.cdc.mongodbapp.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features= "features", glue= "com.mitrais.cdc.mongodbapp.stepdefinition")
public class TestRunner_MongodbApp {
}
