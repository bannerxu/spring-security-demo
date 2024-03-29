package top.banner.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("hello")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
