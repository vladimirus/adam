package com.adam.manager.action;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.log4j.Logger.getLogger;

import com.adam.dao.FriendRepository;
import com.adam.model.Friend;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FbFriends {
    private final static Logger log = getLogger(FbFriends.class);
    @Autowired
    private FriendRepository friendRepository;

    public void makeFriends(WebDriver driver) {
        driver.get("https://www.facebook.com/friends/requests/?fcref=ffb");
        friendRepository.save(print(collectFriends(driver)));
    }

    private Iterable<Friend> collectFriends(WebDriver driver) {
        Collection<Friend> friends = newArrayList();
        for (int i = 0; i < 5; i++) {
            friends.addAll(parseFriendsWebElement(driver, driver.findElements(By.className("friendBrowserListUnit"))));
        }
        return friends;
    }

    private Collection<Friend> parseFriendsWebElement(WebDriver driver, Collection<WebElement> webFriends) {
        Collection<Friend> friends = newArrayList();
        for (WebElement friend : webFriends) {
            try {
                scrollTo(driver, friend);

                Optional<Friend> added = befriend(friend, parseFriendWebElement(friend));
                if (added.isPresent()) {
                    friends.add(added.get());
                }
            } catch (NumberFormatException ignoreCompletely) {

            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
        return friends;
    }

    private Optional<Friend> befriend(WebElement element, Friend friend) {
        Friend clicked = null;
        Friend dbFriend = friendRepository.findByName(friend.getName());
        if (dbFriend == null) {
            if (friend.getNoOfMutualFrieds() >= 14 && friend.getNoOfMutualFrieds() < 50) {
                WebElement button = element.findElement(By.cssSelector(friend.getAddLink()));
                if ("Add friend".equalsIgnoreCase(button.getText())) {
                    log.debug("Befriending " + friend.getName());
                    button.click();
                    clicked = friend;
                }
            }
        }
        return fromNullable(clicked);
    }

    private Friend parseFriendWebElement(WebElement friendElement) {
        String mutualText = friendElement.findElement(By.className("friendBrowserMarginTopTiny")).getText();
        mutualText = mutualText.replaceAll("\\D+", "");

        String name = friendElement.findElement(By.className("friendBrowserNameTitle")).getText();

        int noOfFriends = Integer.valueOf(mutualText);

        WebElement friendButtonElement = friendElement.findElement(By.tagName("button"));
        String friendButton = friendButtonElement.getAttribute("class").replace(" ", ".");

        return new Friend(name, noOfFriends, "button." + friendButton);
    }

    private void scrollTo(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleepUninterruptibly(1, SECONDS);
    }

    private Iterable<Friend> print(Iterable<Friend> friends) {
        log.debug("************************");
        log.debug("Number of friends: " + size(friends));
        for (Friend friend : friends) {
            log.debug(friend);
        }
        return friends;
    }
}
