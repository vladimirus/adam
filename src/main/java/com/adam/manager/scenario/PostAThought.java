package com.adam.manager.scenario;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.common.collect.FluentIterable.from;

import com.adam.manager.Browser;
import com.adam.manager.action.FbLogin;
import com.adam.manager.action.FbStatus;
import com.adam.manager.action.Subreddit;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    @Scheduled(cron = "1 */783 * * * *") //every 13 hours
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
        return from(thoughts).firstMatch(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (cache.getIfPresent(input.hashCode()) == null) {
                    cache.put(input.hashCode(), input);
                    return true;
                }
                return false;
            }
        });
    }
}
