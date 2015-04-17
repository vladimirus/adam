package com.adam.manager.action;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by vladimir.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubredditTest {
    @InjectMocks
    private Subreddit subreddit;
    @Mock
    private RestTemplate restTemplate;

//    {"data": { "children": [ {data": { "domain": "self.Showerthoughts"

    @Test
    @Ignore
    public void shouldReturnThoughts() {
        // given
        given(restTemplate.getForObject(isA(String.class), isA(Class.class))).willReturn("test");


        JsonObject json = new JsonObject();
        JsonArray children = new JsonArray();
        JsonObject child = new JsonObject();
        JsonObject childAttributes = new JsonObject();
        child.add("data", childAttributes);

        children.add(child);

        json.add("data", children);


        // when
        Collection<String> actual = subreddit.showerthoughts();

        // then
        assertThat(actual, hasSize(2));
    }
}