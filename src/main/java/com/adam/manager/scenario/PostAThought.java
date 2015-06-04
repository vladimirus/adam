package com.adam.manager.scenario;

import static com.google.common.cache.CacheBuilder.newBuilder;

import com.adam.manager.Browser;
import com.adam.manager.action.FbLogin;
import com.adam.manager.action.FbStatus;
import com.adam.manager.action.Subreddit;
import com.google.common.cache.Cache;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class PostAThought {
    @Autowired
    private Browser browser;
    @Autowired
    private FbLogin fbLogin;
    @Autowired
    private Subreddit subreddit;
    @Autowired
    private FbStatus status;

    private Cache<Integer, String> cache;

    public PostAThought() {
        this.cache = newBuilder()
                .maximumSize(1000)
                .build();
    }

    @Scheduled(initialDelay = 663451, fixedRate = 46891234) //initial 11 minutes delay, fixedRate = 13 hours
    public void postAThought() {
        Optional<String> thought = getThought(subreddit.showerthoughts());
        if (thought.isPresent()) {
            WebDriver driver = browser.firefoxDriver();
            fbLogin.login(driver);
            status.updateStatus(driver, thought.get());
            driver.quit();
        }
    }

    Optional<String> getThought(Collection<String> thoughts) {
        return thoughts.stream()
                .filter(in -> cache.getIfPresent(in.hashCode()) == null)
                .filter(in -> !in.toLowerCase().contains("reddit"))
                .filter(in -> {
                    cache.put(in.hashCode(), in);
                    return true;
                })
                .findFirst();
    }
}
