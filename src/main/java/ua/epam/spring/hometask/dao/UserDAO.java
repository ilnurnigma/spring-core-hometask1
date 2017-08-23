package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDAO {
    private Collection<User> users = new HashSet<>();

    public boolean save(User user) {
        return users.add(user);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }

        return null;
    }

    /**
     * Getting object by id from storage
     *
     * @param id id of the object
     * @return Found object or <code>null</code>
     */
    public User getById(@Nonnull Long id) {
        for (User user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }

        return null;
    }

    /**
     * Getting all objects from storage
     *
     * @return collection of objects
     */
    public @Nonnull
    Collection<User> getAll() {
        Set<User> users = new HashSet<>();
        for (User user : this.users) {
            users.add(user);
        }

        return users;
    }
}
