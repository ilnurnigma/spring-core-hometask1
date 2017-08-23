package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDAO extends DomainObjectDAO<User>{
    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        for (User user : domainObjects) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }

        return null;
    }
}
