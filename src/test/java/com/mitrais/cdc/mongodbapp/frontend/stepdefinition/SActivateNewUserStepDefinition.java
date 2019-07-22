package com.mitrais.cdc.mongodbapp.frontend.stepdefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SActivateNewUserStepDefinition {

    WebDriver webDriver;

    @Given("New user with username (.*) with password (.*) has been registered successfully")
    public void new_user_with_username_test_with_password_test_has_been_registered_successfully(String username, String password) {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        System.out.println("Path:"+System.getProperty("webdriver.chrome.driver"));
        webDriver = new ChromeDriver();

        webDriver.get("http://localhost:3000/login");
        webDriver.findElement(By.xpath("//*[@name='username']")).sendKeys("admin");
        webDriver.findElement(By.xpath("//*[@name='password']")).sendKeys("admin123");
        webDriver.findElement(By.xpath("//*[@id='login']")).click();
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);


        List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id='root']/div/div[3]/div/main/div/div/div/div/div/div/table/tbody/tr/td[1]"));
        System.out.println("No. of rows: "+ rows.size());
        int newRow =  rows.size()+1;
        String user = webDriver.findElement(By.xpath("//*[@id='root']/div/div[3]/div/main/div/div/div/div/div/div/table/tbody/tr["+newRow+"]/td[1]")).getText();
        System.out.println("Username:"+user);
        assertThat("test", is(user));
    }

    @When("When user (.*) has been activated successfully")
    public void when_user_has_been_activated_successfully(String username) {

        String userEncoded = new String(Base64.encodeBase64(username.getBytes()));
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");

        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:8080/mongodb-app/api/activate?id="+userEncoded);

    }

    @Then("Username (.*) with password (.*) can be used to login successfully")
    public void username_test_with_password_test_can_be_used_to_login_successfully(String username, String password) {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        webDriver = new ChromeDriver();

        webDriver.get("http://localhost:3000/login");
        webDriver.findElement(By.xpath("//*[@name='username']")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@name='password']")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@id='login']")).click();
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id='root']/div/div[3]/div/main/div/div/div/div/div/div/table/tbody/tr/td[1]"));
        System.out.println("No. of rows: "+ rows.size());
        int newRow =  rows.size()+1;
        String user = webDriver.findElement(By.xpath("//*[@id='root']/div/div[3]/div/main/div/div/div/div/div/div/table/tbody/tr["+newRow+"]/td[1]")).getText();
        String status = webDriver.findElement(By.xpath("//*[@id='root']/div/div[3]/div/main/div/div/div/div/div/div/table/tbody/tr["+newRow+"]/td[5]")).getText();
        System.out.println("Username:"+user);
        assertThat("test", is(user));
        assertThat("true", is(status));

    }
}
