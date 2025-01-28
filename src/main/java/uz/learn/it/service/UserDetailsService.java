package uz.learn.it.service;

import uz.learn.it.entity.User;

public interface UserDetailsService {
    User findUserByUsername(String username);
}
