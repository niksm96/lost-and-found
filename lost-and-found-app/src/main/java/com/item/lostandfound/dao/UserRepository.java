package com.item.lostandfound.dao;

import com.item.lostandfound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is a custom JpaRepository that maps the object User to the table user in the relational database.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds the user by username.
     * @param username
     * @return user from DB.
     */
    User findByUsername(final String username);
}
