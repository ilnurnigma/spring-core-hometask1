package ua.epam.spring.hometask.aspects;

import ua.epam.spring.hometask.dao.DiscountCounterDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategies.Every10thTicketDiscountStrategy;

import java.util.HashMap;
import java.util.Map;

public class DiscountCounter {
    private Map<User, Long> userCounter = new HashMap<>();
    private DiscountCounterDAO discountCounterDAO;

    public long getTotal() {
/*        long totalCounter = 0;
        for (long counter : userCounter.values()) {
            totalCounter += counter;
        }*/


        return discountCounterDAO.getTotalCounter(Every10thTicketDiscountStrategy.class.getName());
    }

    public void addDiscountCounter(User user) {
/*        userCounter.putIfAbsent(user, 0L);
        userCounter.put(user, userCounter.get(user) + 1);*/
        discountCounterDAO.addDiscountCounter(user, Every10thTicketDiscountStrategy.class.getName());
    }

    public long getDiscountCounterForUser(User user) {
        return discountCounterDAO.getDiscountCounter(user,Every10thTicketDiscountStrategy.class.getName());
//        return userCounter.get(user);
    }

    public void setDiscountCounterDAO(DiscountCounterDAO discountCounterDAO) {
        this.discountCounterDAO = discountCounterDAO;
    }
}
