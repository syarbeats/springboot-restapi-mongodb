package com.mitrais.cdc.mongodbapp.frontend.stepdefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class RegisterNewUserStepDefinition {

    WebDriver webDriver;

    @Given("Register new user with username (.*) with password (.*) firstname (.*) lastname (.*) email (.*) and role (.*) using register API")
    public void register_new_user_with_username_test_with_password_test_firstname_Syarif_lastname_Hidayat_email_srf_hidayat_gmail_com_and_role_ROLE_ADMIN_using_register_API(String username, String password, String firstname, String lastname, String email, String role) {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        System.out.println("Path:"+System.getProperty("webdriver.chrome.driver"));
        webDriver = new ChromeDriver();

        webDriver.get("http://localhost:3000/user/register");
        webDriver.findElement(By.xpath("//*[@name='username']")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@name='password']")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@name='email']")).sendKeys(email);
        webDriver.findElement(By.xpath("//*[@name='firstname']")).sendKeys(firstname);
        webDriver.findElement(By.xpath("//*[@name='lastname']")).sendKeys(lastname);
        webDriver.findElement(By.xpath("//*[@id='register']")).click();
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @When("When user has been registered successfully will go to register-successfully url")
    public void when_user_has_been_registered_successfully_will_go_to_register_successfully_url() {
        String expectedMessage="Please check your email to active your email !!!! Login";
        String actualMessage = webDriver.findElement(By.xpath("//*[@id='info']")).getText();
        Assert.assertTrue("Register User was failed", expectedMessage.equals(actualMessage));
    }

    @Then("User (.*) with password (.*) can be there on table list of user")
    public void user_test_with_password_test_can_be_there_on_table_list_of_user(String username, String password) {
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

    @Then("User (.*) with password (.*) still cannot login successfully without activate it firstly")
    public void user_test_with_password_test_still_cannot_login_successfully_without_activate_it_firstly(String username, String password) {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        System.out.println("Path:"+System.getProperty("webdriver.chrome.driver"));
        webDriver = new ChromeDriver();

        webDriver.get("http://localhost:3000/login");
        webDriver.findElement(By.xpath("//*[@name='username']")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@name='password']")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@id='login']")).click();
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        String errorMessage =  webDriver.findElement(By.xpath("//*[@id='error']")).getText();
        assertThat("Invalid Credentials", is(errorMessage));

    }
}
