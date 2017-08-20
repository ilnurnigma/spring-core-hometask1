package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.Users;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        for (User user : Users.getUsers()) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User save(@Nonnull User object) {
        if (Users.getUsers().add(object)) {
            return object;
        }

        return null;
    }

    @Override
    public void remove(@Nonnull User object) {
        Users.getUsers().remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        for (User user : Users.getUsers()) {
            if (id.equals(user.getId())) {
                return user;
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return Users.getUsers();
    }
}
