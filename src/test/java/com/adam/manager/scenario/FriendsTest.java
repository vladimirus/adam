package com.adam.manager.scenario;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.adam.manager.Browser;
import com.adam.manager.action.FbFriends;
import com.adam.manager.action.FbLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

@RunWith(MockitoJUnitRunner.class)
public class FriendsTest {
    @InjectMocks
    private Friends friends;
    @Mock
    private Browser browser;
    @Mock
    private FbLogin fbLogin;
    @Mock
    private FbFriends fbFriends;

    @Test
    public void shouldMakeFriends() {
        // given
        WebDriver driver = mock(WebDriver.class);
        given(browser.firefoxDriver()).willReturn(driver);

        // when
        friends.makeFriends();

        // then
        verify(browser).firefoxDriver();
        verify(fbLogin).login(driver);
        verify(fbFriends).makeFriends(driver);
    }
}