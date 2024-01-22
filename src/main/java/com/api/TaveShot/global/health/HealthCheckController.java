package com.api.TaveShot.global.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @ResponseBody
    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
