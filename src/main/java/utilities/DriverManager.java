package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static final ThreadLocal<WebDriver>  driverThread=new ThreadLocal<>();
    public static WebDriver getDriver(){
        if(driverThread.get()==null){
            setDriver();
        }
        return driverThread.get();
    }

    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");

        driverThread.set(new ChromeDriver(options));
    }
    public static void quitDriver(){
        if(driverThread.get()!=null){
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}
