package com.item.lostandfound.service;

import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @return
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * @param user
     * @return
     */
    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
