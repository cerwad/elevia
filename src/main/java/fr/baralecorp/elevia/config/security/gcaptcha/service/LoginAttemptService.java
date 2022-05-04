package fr.baralecorp.elevia.config.security.gcaptcha.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 5;
    private final LoadingCache<String, Integer> attemptsCache = CacheBuilder.newBuilder().
            expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
                public Integer load(String key) {
                    return 0;
                }
            });

    public LoginAttemptService() {
        super();

    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = loginAttempts(key);

        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        return loginAttempts(key) >= MAX_ATTEMPT;
    }


    public int loginAttempts(String key) {
        int attempts = 0;
        Integer attempt = attemptsCache.getIfPresent(key);
        if (attempt != null) {
            attempts = attempt;
        }
        return attempts;
    }
}