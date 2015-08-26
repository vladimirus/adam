package com.adam.manager.action;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.log4j.Logger.getLogger;
import static org.json.simple.JSONValue.parse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;

/**
 * Reading subreddits.
 */
@Component
public class Subreddit {
    private final static Logger log = getLogger(Subreddit.class);
    @Autowired
    private RestTemplate restTemplate;

    public Collection<String> showerthoughts() {
        return rangeClosed(1, 100)
                .mapToObj(i -> {
                    try {
                        log.info("Getting showerthoughts");
                        String response = restTemplate.getForObject("http://www.reddit.com/r/showerthoughts/top.json?&limit=100&show=all&t=day", String.class);
                        return parseShowerthoughts((JSONObject) parse(response));
                    } catch (Exception ignore) {
                        log.error(format("Can't get shower thoughts, iteration %s", i));
                        sleepUninterruptibly(10, SECONDS);
                        return Collections.<String>emptyList();
                    }
                })
                .filter(thoughts -> !thoughts.isEmpty())
                .findAny().orElse(Collections.<String>emptyList());
    }

    private Collection<String> parseShowerthoughts(JSONObject root) {
        Collection<String> thoughts = newArrayList();

        JSONObject data = (JSONObject) root.get("data");
        JSONArray children = (JSONArray) data.get("children");

        for (Object aChild : children) {
            JSONObject child = (JSONObject) aChild;
            JSONObject title = (JSONObject) child.get("data");
            thoughts.add((String) title.get("title"));
        }

        return thoughts;
    }

}
