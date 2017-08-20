package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Users {
    private static Collection<User> users = new HashSet<>();

    public static Collection<User> getUsers() {
        return users;
    }
}
