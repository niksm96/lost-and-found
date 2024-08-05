package com.item.lostandfound.service;

import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl is an implementation class of UserService interface, containing all the "how"(business logic) part.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Invokes the finaAll of UserRepository to fetch all the users.
     * @return list of users.
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Encrypts the password of the user who is registering and saves the user to the DB.
     * @param user to be registered
     * @return User who has been registered.
     */
    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
