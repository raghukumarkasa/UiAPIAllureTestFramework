package BackupStepDefs;

import io.cucumber.java.en.*;
import org.openCart.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilities.DriverManager;

public class LoginPageStepdefs  {
    private WebDriver driver= DriverManager.getDriver();
    private LoginPage loginPage;

    @Given("I am on the Open cart login page")
    public void iAmOnTheOpenCartLoginPage() {
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
        loginPage = new LoginPage(driver);
    }

    @Given("I have entered a valid username and password")
    public void iHaveEnteredAValidUsernameAndPassword() {
        loginPage.enterEmail("standard_user");
        loginPage.enterPassword("secret_sauce");
    }

    @When("I click on the login button")
    public void iClickOnTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertEquals(loginPage.checkLogoutLink(),true);
    }

    @Given("I have entered an invalid {string} and {string}")
    public void iHaveEnteredAnInvalidAnd(String loginId, String pwd) {
        loginPage.enterEmail(loginId);
        loginPage.enterPassword(pwd);
    }

    @Then("I should see an error message indicating {string}")
    public void iShouldSeeAnErrorMessageIndicating(String arg0) {
        Assert.assertEquals(driver.findElement(By.cssSelector(".alert-danger")).isDisplayed(),true);
    }
}
