package utilities;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SafeActions {

    public static void safeCLick(WebDriver driver, By locator, String desc){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            LoggerPrinter.print("Clicked: " + desc, OutputMode.LOG);
        } catch (Exception e) {
            LoggerPrinter.print("Error clicking: ("+ desc +"): "+e.getMessage(),OutputMode.BOTH);
            throw new FrameworkException("Click failed: "+desc,e);
        }
    }

    public static void safeSendKeys(WebDriver driver, By locator,String input, String desc){
        try {
            WebElement elem = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
            elem.clear();
            elem.sendKeys(input);
            LoggerPrinter.print("Sent keys to: " + desc, OutputMode.DEBUG);
        } catch (Exception e) {
            LoggerPrinter.print("Error typing to ("+ desc +"): "+e.getMessage(),OutputMode.BOTH);
            throw new FrameworkException("SendKeys failed: "+desc,e);
        }
    }

    public static boolean safeIsDisplayed(WebDriver driver, By locator, String desc){
        boolean isDisplayed=false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
            LoggerPrinter.print("Checking element displayed: " + desc, OutputMode.LOG);
        } catch (Exception e) {
            LoggerPrinter.print("Error locating element (" +desc+ "): "+e.getMessage(), OutputMode.BOTH);
            throw new FrameworkException("Locating element failed: "+desc,e);
        }
        return isDisplayed;
    }

    public static void safeAcceptAlert(WebDriver driver){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
//            driver.wait(2000);
            alert.accept();
            LoggerPrinter.print("Accepted alert with text : '"+alert.getText(), OutputMode.LOG);
        } catch (Exception e) {
            //do nothing
        }
    }
}
