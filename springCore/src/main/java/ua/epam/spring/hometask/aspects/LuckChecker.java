package ua.epam.spring.hometask.aspects;

import ua.epam.spring.hometask.domain.Ticket;

public interface LuckChecker {
    boolean checkLucky(Ticket ticket);
}
