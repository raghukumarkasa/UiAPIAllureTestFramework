package org.opencart.stepdefs;


import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

//This class will be extended by the individual test classes or step definition classes
public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setup(){
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
