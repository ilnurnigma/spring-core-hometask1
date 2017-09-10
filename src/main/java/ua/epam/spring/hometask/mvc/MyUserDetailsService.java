package ua.epam.spring.hometask.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new MyUserPrincipal(user);
    }
}
