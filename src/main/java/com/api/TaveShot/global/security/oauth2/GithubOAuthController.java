package com.api.TaveShot.global.security.oauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login/oauth/authorize")
public class GithubOAuthController {

    @GetMapping("/github")
    public void githubLogin() {
    }
}
