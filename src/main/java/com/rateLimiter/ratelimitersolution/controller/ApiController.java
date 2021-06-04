package com.rateLimiter.ratelimitersolution.controller;

import com.rateLimiter.ratelimitersolution.model.Developer;
import com.rateLimiter.ratelimitersolution.model.Organization;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @RequestMapping(value="/developers",method = RequestMethod.GET)
    public List<Developer> getDevelopers(@RequestParam("userName") String userName){
        return Arrays.asList(new Developer(1, "Pallavi", "NPD"),
                new Developer(2, "Hardik", "HR"));
    }

    @RequestMapping(value="/organizations",method = RequestMethod.GET)
    public List<Organization> getOrganizations(@RequestParam("userName") String userName){
        return Arrays.asList(new Organization(1,"Blue Optima","Banglore"),
                new Organization(2,"Blue Optima","Gurgaon"));
    }

    @RequestMapping(value="/hello-world2",method = RequestMethod.GET)
    public Flux<String> helloWorld2() throws InterruptedException {
        Thread.sleep(3000);
        return Flux.just("Hello World2","Hello World3");
    }
}
