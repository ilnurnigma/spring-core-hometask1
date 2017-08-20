package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import java.util.HashSet;
import java.util.Set;

public class Users {
    private static Set<User> users = new HashSet<>();

    public static Set<User> getUsers() {
        return users;
    }
}
