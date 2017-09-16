package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.UserAccountDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserAccountService;

public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDAO accountDAO;

    @Override
    public double addAmount(User user, double amount) {
        return accountDAO.add(user, amount);
    }


    public void setAccountDAO(UserAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}
