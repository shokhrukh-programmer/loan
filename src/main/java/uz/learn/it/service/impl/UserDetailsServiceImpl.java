package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.entity.User;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
