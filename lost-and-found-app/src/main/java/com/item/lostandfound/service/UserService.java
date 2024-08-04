package com.item.lostandfound.service;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public List<User> getUsers();
}
