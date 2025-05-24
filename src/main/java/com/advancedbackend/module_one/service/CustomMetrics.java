package com.advancedbackend.module_one.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter customCounter;
    private final Timer customTimer;

    public CustomMetrics(MeterRegistry registry) {
        this.customCounter = Counter.builder("custom_requests_total")
                .description("Total number of custom requests")
                .register(registry);

        this.customTimer = Timer.builder("custom_processing_time")
                .description("Time taken for custom processing")
                .register(registry);
    }

    public void processSomething() {
        customCounter.increment();

        customTimer.record(() -> {
            // simulate processing
            try {
                Thread.sleep(200); // simulate work
            } catch (InterruptedException ignored) {}
        });
    }
}

