package com.item.lostandfound.model;

/**
 * Record for Rest API that claims the item for a user.
 * @param userId
 * @param item
 */
public record ClaimItemModel(Integer userId, Item item) {
}

