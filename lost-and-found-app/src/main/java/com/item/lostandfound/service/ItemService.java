package com.item.lostandfound.service;

import com.item.lostandfound.exceptions.NoFileUploadedException;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ItemService is an interface with definition of the methods.
 */
public interface ItemService {

    public void saveLostItems(MultipartFile file) throws NoFileUploadedException;

    public List<Item> getLostItems();

    public Item claimItem(ClaimItemModel claimItems);

    public Map<Item, String> getClaimedItems();
}
