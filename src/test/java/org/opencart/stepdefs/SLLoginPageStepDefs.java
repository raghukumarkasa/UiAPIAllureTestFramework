package org.opencart.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openCart.pages.SLLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilities.DriverManager;

public class SLLoginPageStepDefs {
    private WebDriver driver= DriverManager.getDriver();
    private SLLoginPage slLoginPage;

    @Given("I am on the Swag Labs login page")
    public void iAmOnTheSwagLabsLoginPage() {
        driver.get("https://www.saucedemo.com/");
        slLoginPage = new SLLoginPage(driver);
    }

    @Given("I have entered a valid username and password")
    public void iHaveEnteredAValidUsernameAndPassword() {
        slLoginPage.enterUserName("standard_user");
        slLoginPage.enterPassword("secret_sauce");
    }

    @When("I click on the login button")
    public void iClickOnTheLoginButton() {
        slLoginPage.clickLoginButton();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertEquals(slLoginPage.checkProductsLabel(),true);
    }

    @Given("I have entered an invalid {string} and {string}")
    public void iHaveEnteredAnInvalidAnd(String loginId, String pwd) {
        slLoginPage.enterUserName(loginId);
        slLoginPage.enterPassword(pwd);
    }

    @Then("I should see an error message indicating {string}")
    public void iShouldSeeAnErrorMessageIndicating(String errorText) {
        Assert.assertEquals(driver.findElement(By.cssSelector(".error-message-container.error")).getText(),errorText);
    }


}
