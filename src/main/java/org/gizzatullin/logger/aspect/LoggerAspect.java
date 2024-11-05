package org.gizzatullin.logger.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.gizzatullin.logger.config.LoggerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private final LoggerProperties loggerProperties;

    @Autowired
    public LoggerAspect(LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object log(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            if (loggerProperties.isEnabled()) {
                if (loggerProperties.getLevel().equals("SHORT")) {
                    logger.info("[HTTP LOGGER] Method {} is started", joinPoint.getSignature().toShortString());
                }
                if (loggerProperties.getLevel().equals("LONG")) {
                    logger.info("[HTTP LOGGER] Method {} is started with arguments {}",
                            joinPoint.getSignature().toShortString(), joinPoint.getArgs());
                }
            }
            result = joinPoint.proceed();
            if (loggerProperties.isEnabled()) {
                if (loggerProperties.getLevel().equals("SHORT")) {
                    logger.info("[HTTP LOGGER] Method {} is finished", joinPoint.getSignature().toShortString());
                }
                if (loggerProperties.getLevel().equals("LONG")) {
                    logger.info("[HTTP LOGGER] Method {} is finished with arguments {}",
                            joinPoint.getSignature().toShortString(), joinPoint.getArgs());
                }
            }
        } catch (Throwable e) {
            if (loggerProperties.isEnabled()) {
                logger.error("[HTTP LOGGER] Method {} threw an exception {}", joinPoint.getSignature().toShortString(),
                        e.getMessage());
            }
        }
        return result;
    }
}
