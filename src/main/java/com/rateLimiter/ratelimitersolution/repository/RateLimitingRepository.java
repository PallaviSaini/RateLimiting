package com.rateLimiter.ratelimitersolution.repository;

import com.rateLimiter.ratelimitersolution.entity.RateLimitConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface RateLimitingRepository extends JpaRepository<RateLimitConfiguration,Integer> {
}
