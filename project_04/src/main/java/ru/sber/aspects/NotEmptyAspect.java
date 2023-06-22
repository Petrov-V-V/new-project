package ru.sber.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import ru.sber.exceptions.NullOrEmptyException;

import java.util.Collection;

/**
 * Аспект проверяющий, что аргументы метода не null и не пустые, если аргументами являются строки или коллекции
 */
@Aspect
public class NotEmptyAspect {

    @Before("@annotation(ru.sber.annotations.NotEmpty)")
    public void validateArguments(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || (args[i] instanceof String && ((String) args[i]).isEmpty())
                    || (args[i] instanceof Collection && ((Collection<?>) args[i]).isEmpty())) {
                throw new NullOrEmptyException("Argument at index " + (i-1) + " must not be null or empty.");
            }
        }
    }
}