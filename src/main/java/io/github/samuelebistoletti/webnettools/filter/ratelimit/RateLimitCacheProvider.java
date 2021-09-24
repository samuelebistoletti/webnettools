package io.github.samuelebistoletti.webnettools.filter.ratelimit;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RateLimitCacheProvider {
    @ConfigProperty(name = "RATE_LIMIT", defaultValue = "1")
    long rateLimit;

    LoadingCache<String, Boolean> store;

    @PostConstruct
    void init() {
        store = CacheBuilder.newBuilder()
                .maximumSize(100000)
                .expireAfterWrite(rateLimit, TimeUnit.MILLISECONDS)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public Boolean load(String s) {
                                return false;
                            }
                        }
                );
    }

    @Singleton
    @Produces
    @RateLimitCacheQualifier
    public Map<String, Boolean> produce() {
        return store.asMap();
    }
}