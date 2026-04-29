package com.sicklecare.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    // Define a number of requests to 5 for a period of 1 minute;
    private static final int MAX_REQUESTS = 5;
    private static final int WINDOW_MINUTES = 1;

    public Boolean isAllow(String key){

        String redisKey = "ratelimit:" + key;

        // Increment le counter on Redis
        Long currentCount = redisTemplate.opsForValue().increment(redisKey);

        if (currentCount != null && currentCount == 1){
            // First request: we define an expiration in 1 minute
            redisTemplate.expire(redisKey, Duration.ofMinutes(WINDOW_MINUTES));
        }

        return currentCount != null && currentCount <= MAX_REQUESTS;
    }

}
