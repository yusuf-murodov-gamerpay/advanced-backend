package com.advancedbackend.module_one.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("customHealth")
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean appIsHealthy = checkYourAppLogic();

        if (appIsHealthy) {
            return Health.up()
                    .withDetail("customService", "Running")
                    .build();
        } else {
            return Health.down()
                    .withDetail("customService", "Unavailable")
                    .build();
        }
    }

    private boolean checkYourAppLogic() {
        return true;
    }
}