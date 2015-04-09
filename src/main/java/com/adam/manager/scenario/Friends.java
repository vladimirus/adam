package com.adam.manager.scenario;

import static org.apache.log4j.Logger.getLogger;

import com.adam.manager.Browser;
import com.adam.manager.action.FbFriends;
import com.adam.manager.action.FbLogin;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Friends {
    private final static Logger log = getLogger(Friends.class);

    @Autowired
    private Browser browser;
    @Autowired
    private FbLogin fbLogin;
    @Autowired
    private FbFriends fbFriends;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.debug("The time is now " + new Date());
    }

    public void makeFriends() {
        WebDriver driver = browser.firefoxDriver();
        fbLogin.login(driver);
        fbFriends.makeFriends(driver);
        driver.quit();
    }
}
