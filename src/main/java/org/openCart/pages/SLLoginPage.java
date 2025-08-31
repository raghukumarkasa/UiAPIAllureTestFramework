package org.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.SafeActions;

public class SLLoginPage extends BasePage{



    //Locators for Swag Labs
    private By UserNameInputLocator = By.name("user-name");
    private By passwordInputLocator = By.name("password");
    private By loginButtonLocator = By.xpath("//input[@type='submit']");
    private By menuButtonLocator = By.xpath("//button[text()='Open Menu']");
    private By productsLabelLocator = By.xpath("//span[text()='Products']");
    private By logoutLinkLocator = By.linkText("Logout");

    //Constructor
    public SLLoginPage(WebDriver driver) {
        super(driver);
    }

    //methods
    public void enterUserName(String email){
        SafeActions.safeSendKeys(driver,UserNameInputLocator,email,"EmailId");
    }

    public void enterPassword(String password){
        SafeActions.safeSendKeys(driver,passwordInputLocator,password,"Password");
    }

    public void clickLoginButton(){
        SafeActions.safeCLick(driver, loginButtonLocator, "Login Button");
    }
    public void clickMenuButton(){
        SafeActions.safeCLick(driver, menuButtonLocator, "Login Button");
    }

    public boolean checkProductsLabel(){
        return SafeActions.safeIsDisplayed(driver,productsLabelLocator,"Products Label");
    }

    public void clickLogoutLink(){
        SafeActions.safeCLick(driver, logoutLinkLocator, "Logout Link");
    }
}
