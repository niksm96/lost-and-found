package com.item.lostandfound.dao;

import com.item.lostandfound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostAndFoundRepository extends JpaRepository<User, Integer> {

}
