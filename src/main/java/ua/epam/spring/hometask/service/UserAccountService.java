package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.User;

public interface UserAccountService {
    double addAmount(User user, double amount);

    double subtractAmount(User user, double amount);

    double getAmount(User user);
}
