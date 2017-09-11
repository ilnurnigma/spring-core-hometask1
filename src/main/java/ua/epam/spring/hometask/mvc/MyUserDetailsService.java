package ua.epam.spring.hometask.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getDefaultUser(email);
        if (user != null) {
            return new MyUserPrincipal(user);
        }

        user = userDAO.getUserByEmail(email);
        if (user != null) {
            return new MyUserPrincipal(user);
        }

        throw new UsernameNotFoundException(email);
    }

    private User getDefaultUser(String email) {
        User user = new User();
        switch (email) {
            case "user@mail.com":
                user.setEmail("user@mail.com");
                user.setPassword(encoder.encode("12345"));
                user.setRoles("ROLE_REGISTERED_USER");
                return user;

            case "manager@mail.com":
                user.setEmail("manager@mail.com");
                user.setPassword(encoder.encode("12345"));
                user.setRoles("ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
                return user;
        }

        return null;
    }
}
