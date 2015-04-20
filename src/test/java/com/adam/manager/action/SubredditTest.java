package com.adam.manager.action;

import static java.util.Collections.addAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

    @Test
//    @Ignore
    public void shouldReturnThoughts() {
        // given
        String json = subreddit(listing("title1"), listing("title2")).toJSONString();
        given(restTemplate.getForObject(isA(String.class), isA(Class.class))).willReturn(json);

        // when
        Collection<String> actual = subreddit.showerthoughts();

        // then
        assertThat(actual, hasSize(2));
        assertThat(actual.contains("title1"), is(true));
    }

    private JSONObject subreddit(JSONObject... children) {
        JSONObject data = new JSONObject();
        data.put("children", jsonArrayOf(children));

        JSONObject root = new JSONObject();
        root.put("data", data);
        root.put("kind", "listing");
        return root;
    }

    private JSONObject listing(String title) {
        JSONObject subreddit = new JSONObject();
        subreddit.put("title", title);

        JSONObject actualObject = new JSONObject();
        actualObject.put("data", subreddit);
        actualObject.put("kind", "t5");
        return actualObject;
    }

    private JSONArray jsonArrayOf(Object... items) {
        JSONArray array = new JSONArray();
        addAll(array, items);
        return array;
    }
}