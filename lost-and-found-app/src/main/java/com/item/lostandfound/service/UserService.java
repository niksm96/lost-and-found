package com.item.lostandfound.service;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * UserService is an interface with definition of the methods.
 */
public interface UserService {

    public List<User> getUsers();

    public User register(User user);
}
