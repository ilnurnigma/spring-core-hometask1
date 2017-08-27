package ua.epam.spring.hometask.aspects;

import ua.epam.spring.hometask.domain.Ticket;

public class DefaultLuckChecker implements LuckChecker {
    @Override
    public boolean checkLucky(Ticket ticket) {
        return Math.random() > 0.5;
    }
}
