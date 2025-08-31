package org.opencart.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DriverManager;
import utilities.LoggerPrinter;
import utilities.OutputMode;

import java.io.ByteArrayInputStream;

public class Hooks {
//    @After
//    public void tearDown(Scenario scenario){
//        if (scenario.isFailed()){
//            LoggerPrinter.print("Scenario failed: "+scenario.getName(), OutputMode.BOTH);
//            addScreenshotAttachment();
//        }else{
//            addScreenshotAttachment();
//        }
//        DriverManager.quitDriver();
//    }
//
//    //@Attachment(value = "Screenshot - {scenarioName}",type="image/png")
//    @Attachment(value = "Failed Screenshot",type="image/png")
//    public byte[] addScreenshotAttachment(){
//        try {
//            return ((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
//        } catch (Exception e) {
//            LoggerPrinter.print("Failed to capture snapshot: "+e.getMessage(),OutputMode.BOTH);
//        }
//        return new byte[0];
//    }

    @AfterStep
    public void afterEachStep(Scenario scenario){
        if (scenario.isFailed()) {
            LoggerPrinter.print("Step failed inside: " + scenario.getName(), OutputMode.BOTH);
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Failed Step Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
            } catch (Exception e) {
                LoggerPrinter.print("Screenshot capture failed: " + e.getMessage(), OutputMode.BOTH);
            }
        }
    }


    @After
    public void afterEach(Scenario scenario) {
        DriverManager.quitDriver();
    }
}
