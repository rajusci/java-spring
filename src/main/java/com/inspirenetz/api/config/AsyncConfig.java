package com.inspirenetz.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by sandheepgr on 29/9/16.
 */
@Configuration
public class AsyncConfig {

    /**
     * Properties for the threadpool.
     */
    @Value("${async.corePoolSize}")
    private Integer corePoolSize;

    @Value("${async.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${async.queueCapacity}")
    private Integer queueCapacity;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        return executor;

    }


}
