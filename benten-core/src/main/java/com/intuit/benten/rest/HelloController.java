package com.intuit.benten.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/health")
    public String index() {
        return "Benten is up and running!";
    }

}