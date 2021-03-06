package com.adam.manager.scenario;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.adam.manager.Browser;
import com.adam.manager.action.FbLogin;
import com.adam.manager.action.FbStatus;
import com.adam.manager.action.Subreddit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PostAThoughtTest {
    @InjectMocks
    private PostAThought postAThought;
    @Mock
    private Browser browser;
    @Mock
    private Subreddit subreddit;
    @Mock
    private FbLogin fbLogin;
    @Mock
    private FbStatus fbStatus;

    @Test
    public void shouldGetFirstThought() {
        // when
        Optional<String> actual = postAThought.getThought(asList("One", "Two"));

        // then
        assertThat(actual.get(), is("One"));
    }

    @Test
    public void shouldGetSecondThought() {
        // given
        postAThought.getThought(asList("One", "Two")); // first thought is consumed

        // when
        Optional<String> actual = postAThought.getThought(asList("One", "Two"));

        // then
        assertThat(actual.get(), is("Two"));
    }

    @Test
    public void shouldGetSecondAgainThought() {
        // when
        Optional<String> actual = postAThought.getThought(asList("One reddit something", "Two"));

        // then
        assertThat(actual.get(), is("Two"));
    }

    @Test
    public void shouldReturnEmpty() {
        // given
        postAThought.getThought(asList("One", "Two")); // first thought is consumed
        postAThought.getThought(asList("One", "Two")); // second thought is consumed

        // when
        Optional<String> actual = postAThought.getThought(asList("One", "Two"));

        // then
        assertThat(actual.isPresent(), equalTo(false));
    }

    @Test
    public void shouldPost() {
        // given
        WebDriver driver = mock(WebDriver.class);
        given(browser.firefoxDriver()).willReturn(driver);
        given(subreddit.showerthoughts()).willReturn(singletonList("one"));

        // when
        postAThought.postAThought();

        // then
        verify(browser).firefoxDriver();
        verify(fbLogin).login(driver);
        verify(fbStatus).updateStatus(driver, "one");
    }

    @Test
    public void shouldNotPost() {
        // given
        WebDriver driver = mock(WebDriver.class);
        given(browser.firefoxDriver()).willReturn(driver);
        given(subreddit.showerthoughts()).willReturn(new ArrayList<>());

        // when
        postAThought.postAThought();

        // then
        verify(browser, never()).firefoxDriver();
        verify(fbLogin, never()).login(isA(WebDriver.class));
        verify(fbStatus, never()).updateStatus(isA(WebDriver.class), isA(String.class));
    }
}