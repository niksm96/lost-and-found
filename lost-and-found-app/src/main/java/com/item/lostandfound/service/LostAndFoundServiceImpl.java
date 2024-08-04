package com.item.lostandfound.service;

import com.item.lostandfound.dao.LostAndFoundRepository;
import com.item.lostandfound.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostAndFoundServiceImpl implements LostAndFoundService{

    @Autowired
    private LostAndFoundRepository lostAndFoundRepository;

    /**
     * @return
     */
    @Override
    public List<User> getUsers() {
        return lostAndFoundRepository.findAll();
    }
}
