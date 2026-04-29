package com.sicklecare.api.services;

import com.sicklecare.api.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final StringRedisTemplate redisTemplate;
    private final JwtUtils jwtUtils;

    public void invalidateToken(String authHeader){

        if (authHeader != null && authHeader.startsWith("Bearer ")){

            String token = authHeader.substring(7);
            Long expiration = jwtUtils.getRemainingExperiationTime(token);

            redisTemplate.opsForValue().set(
                    "blacklist:" + token,
                    "revoked",
                    Duration.ofMillis(expiration)
            );
        }

    }

}
