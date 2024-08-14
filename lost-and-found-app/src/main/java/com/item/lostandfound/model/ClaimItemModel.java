package com.item.lostandfound.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Record for Rest API that claims the item for a user.
 * @param userId
 * @param item
 */
public record ClaimItemModel(Integer userId, Item item) {
}

