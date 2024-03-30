package com.tanthanh.notificationservice;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import brave.internal.baggage.BaggageFields.Factory;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class config {
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.custom()
						.slidingWindowSize(10)
						.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
						.minimumNumberOfCalls(5)
						.failureRateThreshold(50)
						.build()
						
						).build()
				);
	}
}
