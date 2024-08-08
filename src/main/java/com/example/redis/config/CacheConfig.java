package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
//        // 설정 구성을 먼저 진행한다.
//        // Redis를 이용해서 Spring Cache를 사용할 때
//        // Redis 관련 설정을 모아두는 클래스
//        RedisCacheConfiguration configuration = RedisCacheConfiguration
//                // 대부분의 기본설정을 사용할 거임
//                .defaultCacheConfig()
//                // null을 캐싱 할것인지
//                .disableCachingNullValues()// 널은 캐싱 안함
//                // 기본 캐시 유지 시간 (Time To Live[Ttl])
//                .entryTtl(Duration.ofSeconds(120))// 10초간 캐시 데이터 유지
//                // 캐시를 구분하는 접두사 설정
//                .computePrefixWith(CacheKeyPrefix.simple())
//                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
//                .serializeValuesWith(
//                    SerializationPair.fromSerializer(RedisSerializer.java())
//                );
//
//        return RedisCacheManager
//                .builder(redisConnectionFactory)
//                .cacheDefaults(configuration)
//                .build();
//    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
                return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfiguration())
                .withInitialCacheConfigurations(customConfigurationMap())
                .build();
    }
    private Map<String, RedisCacheConfiguration> customConfigurationMap() {
        Map<String, RedisCacheConfiguration> customConfigurationMap = new HashMap<>();
        customConfigurationMap.put(RedisCacheKey.itemCache, itemCacheConfiguration());
        customConfigurationMap.put(RedisCacheKey.itemAllCache, itemAllCacheConfiguration());
        customConfigurationMap.put(RedisCacheKey.itemSearchCache, itemSearchCacheConfiguration());
        return customConfigurationMap;
    }

    private RedisCacheConfiguration defaultCacheConfiguration() {
                return RedisCacheConfiguration
                // 대부분의 기본설정을 사용할 거임
                .defaultCacheConfig()
                // null을 캐싱 할것인지
                .disableCachingNullValues()// 널은 캐싱 안함
                // 기본 캐시 유지 시간 (Time To Live[Ttl])
                .entryTtl(Duration.ofSeconds(120))// 10초간 캐시 데이터 유지
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
                .serializeValuesWith(
                    SerializationPair.fromSerializer(RedisSerializer.java())
                );
    }
    //itemCache의 설정
    private RedisCacheConfiguration itemCacheConfiguration() {
        return RedisCacheConfiguration
                // 대부분의 기본설정을 사용할 거임
                .defaultCacheConfig()
                // null을 캐싱 할것인지
                .disableCachingNullValues()// 널은 캐싱 안함
                // 기본 캐시 유지 시간 (Time To Live[Ttl])
                .entryTtl(Duration.ofSeconds(120))// 10초간 캐시 데이터 유지
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );
    }
    //itemAllCache의 설정
    private RedisCacheConfiguration itemAllCacheConfiguration() {
        return RedisCacheConfiguration
                // 대부분의 기본설정을 사용할 거임
                .defaultCacheConfig()
                // null을 캐싱 할것인지
                .disableCachingNullValues()// 널은 캐싱 안함
                // 기본 캐시 유지 시간 (Time To Live[Ttl])
                .entryTtl(Duration.ofSeconds(120))// 10초간 캐시 데이터 유지
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );
    }
    //itemSearchCache의 설정
    private RedisCacheConfiguration itemSearchCacheConfiguration() {
        return RedisCacheConfiguration
                // 대부분의 기본설정을 사용할 거임
                .defaultCacheConfig()
                // null을 캐싱 할것인지
                .disableCachingNullValues()// 널은 캐싱 안함
                // 기본 캐시 유지 시간 (Time To Live[Ttl])
                .entryTtl(Duration.ofSeconds(120))// 10초간 캐시 데이터 유지
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );
    }

}
