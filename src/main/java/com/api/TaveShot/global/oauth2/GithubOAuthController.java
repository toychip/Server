package com.api.TaveShot.global.oauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/authorize")
public class GithubOAuthController {

    @GetMapping("/github")
    public void githubLogin() {
    }
}
