package com.item.lostandfound.dao;

import com.item.lostandfound.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * ItemRepository is a custom JpaRepository that maps the object Item to the table item in the relational database.
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {

    /**
     * Finds the item by provided item name and place it was found in.
     * @param itemName
     * @param place
     * @return Item from DB.
     */
    Optional<Item> findByItemNameAndPlace(String itemName, String place);
}
