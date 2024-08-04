package com.item.lostandfound.dao;

import com.item.lostandfound.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByItemNameAndPlace(String itemName, String place);
}
