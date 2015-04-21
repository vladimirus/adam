package com.adam.manager.action;

import static org.openqa.selenium.Keys.COMMAND;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.chord;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class FbStatus {

    public void updateStatus(WebDriver driver, String status) {
        WebElement textarea = driver.findElement(By.tagName("textarea"));
        textarea.click();
        textarea.sendKeys(status);
        textarea.sendKeys(chord(CONTROL, ENTER));
        textarea.sendKeys(chord(COMMAND, ENTER));
    }
}
