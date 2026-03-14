package com.lab.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class ShiroLabApplication {

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        @RequestParam(defaultValue = "false") boolean rememberMe) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);
            subject.login(token);
            return "Login OK | user=" + username + " | rememberMe=" + rememberMe;
        } catch (Exception e) {
            return "Login Failed: " + e.getMessage();
        }
    }

    @GetMapping("/whoami")
    public String whoami() {
        Subject subject = SecurityUtils.getSubject();
        return "isAuthenticated: " + subject.isAuthenticated() + "\n"
            + "isRemembered: " + subject.isRemembered() + "\n"
            + "principal: " + subject.getPrincipal() + "\n";
    }

    public static void main(String[] args) {
        SpringApplication.run(ShiroLabApplication.class, args);
    }
}
