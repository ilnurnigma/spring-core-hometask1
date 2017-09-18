package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import ua.epam.spring.hometask.dao.DiscountCounterDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategies.DiscountStrategy;

@Aspect
public class DiscountAspect {
    private DiscountCounterDAO discountCounterDAO;

    @AfterReturning(pointcut = "execution(* *.getDiscount(..))",
            returning = "discount")
    private void afterAnyGetDiscount(JoinPoint joinPoint, byte discount) {
        User user = (User) joinPoint.getArgs()[0];

        Class<?> clazz = joinPoint.getTarget().getClass();
        discountCounterDAO.addDiscountCounter(user,clazz.getName());
    }

    public long getTotalDiscountCounter(Class<? extends DiscountStrategy> clazz) {
        return discountCounterDAO.getTotalCounter(clazz.getName());
    }

    public long getDiscountCounterForUser(Class<? extends DiscountStrategy> clazz, User user) {
        return discountCounterDAO.getDiscountCounter(user,clazz.getName());
    }

    public void setDiscountCounterDAO(DiscountCounterDAO discountCounterDAO) {
        this.discountCounterDAO = discountCounterDAO;
    }
}
