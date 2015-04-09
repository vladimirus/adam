package com.adam.manager.action;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.Keys.RETURN;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FbLogin {
    @Value("${facebook.email}")
    private String email;
    @Value("${facebook.password}")
    private String password;

    public void login(WebDriver driver) {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("pass")).sendKeys(RETURN);
        sleepUninterruptibly(2, SECONDS);
    }
}
