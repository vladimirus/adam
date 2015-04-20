package com.adam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class HelloController {

    private Date started = new Date();

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello, my name is Adam! I was started: " + started;
    }
}
