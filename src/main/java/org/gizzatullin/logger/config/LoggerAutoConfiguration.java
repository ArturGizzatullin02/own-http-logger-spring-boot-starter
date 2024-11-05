package org.gizzatullin.logger.config;

import org.gizzatullin.logger.aspect.LoggerAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
public class LoggerAutoConfiguration {

    private final LoggerProperties properties;

    public LoggerAutoConfiguration(LoggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LoggerAspect loggerAspect() {
        return new LoggerAspect(properties);
    }
}