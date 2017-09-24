package ua.epam.spring.hometask.domain;

public class UserAccount {
    private User user;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccount(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }

    public UserAccount() {
    }
}
