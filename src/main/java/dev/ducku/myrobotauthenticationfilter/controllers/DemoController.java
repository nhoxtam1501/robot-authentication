package dev.ducku.myrobotauthenticationfilter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String publicPage() {
        return "Welcome to public Page";
    }

    @GetMapping("/demo")
    public String demo(Authentication authentication) {
        return "WELCOME TO VIP ROOM ~[" + authentication.getName() + "]🥳🎉";
    }
}
