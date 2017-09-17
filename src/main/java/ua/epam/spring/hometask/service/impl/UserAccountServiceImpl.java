package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.UserAccountDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.service.UserAccountService;

public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDAO accountDAO;

    @Override
    public UserAccount refill(UserAccount account, double amount) {
        return accountDAO.refill(account, amount);
    }

    @Override
    public UserAccount subtract(UserAccount account, double amount) {
        return accountDAO.subtract(account, amount);
    }

    @Override
    public UserAccount getUserAccount(User user) {
        return accountDAO.getAccount(user);
    }

    public void setAccountDAO(UserAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}
