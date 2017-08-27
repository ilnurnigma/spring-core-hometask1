package ua.epam.spring.hometask.aspects;

import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

public class DiscountCounter {
    private Map<User, Long> userCounter = new HashMap<>();

    public long getTotal() {
        long totalCounter = 0;
        for (long counter : userCounter.values()) {
            totalCounter += counter;
        }

        return totalCounter;
    }

    public void addDiscountCounter(User user) {
        userCounter.putIfAbsent(user, 0L);
        userCounter.put(user, userCounter.get(user) + 1);
    }

    public long getDiscountCounterForUser(User user) {
        return userCounter.get(user);
    }
}
