package org.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.SafeActions;

public class LoginPage extends BasePage {

    //private WebDriver driver;

    //Locators for Naveen automation labs
    private By emailInputLocator = By.name("email");
    private By passwordInputLocator = By.name("password");
    private By loginButtonLocator = By.xpath("//input[@type='submit']");
    private By forgottenPasswordLinkLocator = By.linkText("password");
    private By logoutLinkLocator = By.linkText("Logout");



    //Constructor
    public LoginPage(WebDriver driver){
        super(driver);
    }

    //methods
    public void enterEmail(String email){
        SafeActions.safeSendKeys(driver,emailInputLocator,email,"EmailId");
    }

    public void enterPassword(String password){
        SafeActions.safeSendKeys(driver,passwordInputLocator,password,"Password");
    }

    public void clickLoginButton(){
        SafeActions.safeCLick(driver, loginButtonLocator, "Login Button");
    }

    public void clickForgotPasswordLink(){
        SafeActions.safeCLick(driver, forgottenPasswordLinkLocator, "Forgot Password Link");
    }

    public boolean checkForgotPasswordLinkDisplayed(){
        return SafeActions.safeIsDisplayed(driver, forgottenPasswordLinkLocator, "Forgot Password Link");
    }

    public boolean checkLogoutLink(){
        return SafeActions.safeIsDisplayed(driver, logoutLinkLocator, "Logout Link");
    }

/*    public void login(String email, String password){
        enterUserName(email);
        enterPassword(password);
        clickLoginButton();
    }*/

}