package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategies.DiscountStrategy;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class DiscountAspect {
    private Map<Class<?>, DiscountCounter> discountsTotalCounter = new HashMap<>();

    @AfterReturning(pointcut = "execution(* *.getDiscount(..))",
            returning = "discount")
    private void afterAnyGetDiscount(JoinPoint joinPoint, byte discount) {
        User user = (User) joinPoint.getArgs()[0];

        Class<?> clazz = joinPoint.getTarget().getClass();
        if (!discountsTotalCounter.containsKey(clazz)) {
            discountsTotalCounter.put(clazz, new DiscountCounter());
        }

        if (discount <= 0) {
            return;
        }

        discountsTotalCounter.get(clazz).addDiscountCounter(user);
    }

    public long getTotalDiscountCounter(Class<? extends DiscountStrategy> clazz) {
        return discountsTotalCounter.get(clazz).getTotal();
    }

    public long getDiscountCounterForUser(Class<? extends DiscountStrategy> clazz, User user) {
        return discountsTotalCounter.get(clazz).getDiscountCounterForUser(user);
    }
}
