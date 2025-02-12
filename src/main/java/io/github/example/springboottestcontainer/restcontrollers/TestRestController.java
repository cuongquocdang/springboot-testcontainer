package io.github.example.springboottestcontainer.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/tests")
public class TestRestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
