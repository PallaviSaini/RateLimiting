package com.rateLimiter.ratelimitersolution.service;

import reactor.core.publisher.Flux;

public interface IRateLimitingService {
    public boolean consumeBucket(String apiName);

    Flux<String> helloMovieFromDifferentSource();
}
