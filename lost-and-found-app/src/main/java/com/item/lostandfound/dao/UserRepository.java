package com.item.lostandfound.dao;

import com.item.lostandfound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
