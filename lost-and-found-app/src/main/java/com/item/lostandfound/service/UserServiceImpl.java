package com.item.lostandfound.service;

import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @return
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }



}
