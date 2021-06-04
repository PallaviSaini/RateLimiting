package com.rateLimiter.ratelimitersolution.service;

import com.rateLimiter.ratelimitersolution.entity.RateLimitConfiguration;
import com.rateLimiter.ratelimitersolution.repository.RateLimitingRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RateLimitingService implements IRateLimitingService{

    @Autowired
    private RateLimitingRepository rateLimitingRepository;

    Map<String,LocalBucket> bucketMap = new HashMap<String,LocalBucket>();

    @Autowired
    WebClient.Builder webClientBuilder;


    @PostConstruct
    public void registerBuckets(){
        List<RateLimitConfiguration> rateLimitingRepositoryList = rateLimitingRepository.findAll();
        rateLimitingRepositoryList.stream().forEach(rateLimitConfiguration-> {
            Refill refill = Refill.greedy(rateLimitConfiguration.getHitsInWindow(), Duration.ofSeconds(rateLimitConfiguration.getWindowSize()));
            Bandwidth limit = Bandwidth.classic(rateLimitConfiguration.getHitsInWindow(), refill);
            String key = prepareKeyForBuckets(rateLimitConfiguration);
            LocalBucket localBucket = Bucket4j.builder()
                    .addLimit(limit)
                    .build();
            bucketMap.put(key,localBucket);
        });
    }

    private String prepareKeyForBuckets(RateLimitConfiguration rateLimitConfiguration) {
        String apiName = rateLimitConfiguration.getApiName();
        String userName = rateLimitConfiguration.getUserName();
        if(null!=apiName && null!=userName) {
            StringBuilder stringBuilder = new StringBuilder(apiName);
            stringBuilder.append("~");
            stringBuilder.append(userName);
            return stringBuilder.toString();
        }else{
            return null;
        }
    }

    public boolean consumeBucket(String bucketKey){
        if(bucketMap.containsKey(bucketKey)){
            LocalBucket localBucket = bucketMap.get(bucketKey);
            return localBucket.tryConsume(1L);
        }else if(bucketMap.containsKey(null)){
            LocalBucket localBucket = bucketMap.get(null);
            return localBucket.tryConsume(1L);
        }
        return true;
    }

    @Override
    public Flux<String> helloMovieFromDifferentSource(){
        Flux<String> s =  webClientBuilder.build().get().uri("http://localhost:8082/movies/helloMovie")
                .retrieve().bodyToFlux(String.class).log();
        System.out.println("helloMovie done");
        return s;
    }
}
