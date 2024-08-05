package com.item.lostandfound.service;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService {

    public boolean saveLostItems(MultipartFile file);

    public List<Item> getLostItems();

    public Item claimItem(ClaimItemModel claimItems);

    public Map<Item, String> getClaimedItems();
}
