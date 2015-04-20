package com.adam.manager.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class FbStatus {

    public void updateStatus(WebDriver driver, String status) {
        driver.get("https://www.facebook.com/");
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        textarea.click();
        textarea.sendKeys(status);

    }
}
