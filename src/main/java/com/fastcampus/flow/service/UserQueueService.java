package com.fastcampus.flow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.fastcampus.flow.exception.ErrorCode.QUEUE_ALREADY_REGISTERED_USER;

@RequiredArgsConstructor
@Service
public class UserQueueService  {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private final String USER_QUEUE_WAIT_KEY = "user:queue:%s:wait";

    //대기열 등록 API
    public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
        //redis sortedset
        // key : userIdd
        // value : unix timestamp
        //rank

        var unixTimestamp = Instant.now().getEpochSecond();
        return reactiveRedisTemplate.opsForZSet().add(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString(), unixTimestamp)
                .filter(i -> i)
                .switchIfEmpty(Mono.error(QUEUE_ALREADY_REGISTERED_USER.build()))
                .flatMap(i -> reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), userId.toString()))
                .map(i-> i>=0 ?i+1:0);


    }
}
