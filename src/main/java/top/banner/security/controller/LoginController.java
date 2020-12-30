package top.banner.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @PostMapping("/toMain")
//    @Secured("ROLE_abC")
//    @PreAuthorize("hasRole('abc')")
    @PreAuthorize("hasRole('ROLE_abc')")
    public String main() {
        return "redirect:main.html";
    }

    @PostMapping("/toError")
    public String error() {
        return "redirect:error.html";
    }

    @GetMapping("/demo")
    @ResponseBody
    public String demo() {
        return "demo";
    }
}
