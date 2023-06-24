package com.proxym.newsletter.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {
    @GetMapping("/welcome")
    public String HelloWord(){
        return "Hello Word !";
    }

}
