package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Dedicated pool for PDF extraction (including OCR, the slow path). Bounded
     * deliberately: Spring's default @Async executor (SimpleAsyncTaskExecutor)
     * spawns a new unbounded thread per task, which is fine in dev but a real
     * resource-exhaustion risk under concurrent uploads in production — a burst
     * of large scanned PDFs could spin up unbounded OCR threads simultaneously.
     */
    @Bean(name = "extractionTaskExecutor")
    public Executor extractionTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("extraction-");
        // If the queue is full, run the task on the caller's thread instead of
        // silently dropping it — degrades to synchronous behavior under extreme
        // load rather than losing an upload's extraction entirely.
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}