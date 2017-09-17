package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;

public interface UserAccountService {
    UserAccount refill(UserAccount account, double amount);

    UserAccount subtract(UserAccount account, double amount);

    UserAccount getUserAccount(User user);
}
