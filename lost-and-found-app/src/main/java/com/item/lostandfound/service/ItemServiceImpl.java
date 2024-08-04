package com.item.lostandfound.service;

import com.item.lostandfound.dao.ItemRepository;
import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param multipartFile
     * @return
     */
    @Override
    public boolean saveLostItems(MultipartFile multipartFile) {
        File file = null;
        try {
            file = FileUtils.convertMultipartFileToFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Item> listOfItems = FileUtils.listOfItemsFromFile(file);
        return !itemRepository.saveAll(listOfItems).isEmpty();
    }

    /**
     * @return
     */
    @Override
    public List<Item> getLostItems() {
        return itemRepository.findAll();
    }

    /**
     * @param claimItem
     * @return Saved Item
     */
    @Override
    public Item claimItem(ClaimItemModel claimItem) {
        Item selectedItem = claimItem.item();
        Item item = null;
        Optional<Item> itemOptional = itemRepository.findByItemNameAndPlace(selectedItem.getItemName(), selectedItem.getPlace());
        if(itemOptional.isPresent()) item = itemOptional.get();
        assert item != null;
        item.setQuantity(item.getQuantity() - selectedItem.getQuantity());
        Set<Integer> listOfUserIds = item.getSetOfUsersIds() != null ? item.getSetOfUsersIds() : new HashSet<>();
        listOfUserIds.add(claimItem.userId());
        item.setSetOfUsersIds(listOfUserIds);
        return itemRepository.save(item);
    }

    /**
     * @return
     */
    @Override
    public Map<Item, User> getClaimedItems() {
        Map<Item, User> map = new HashMap<>();
        List<Item> listOfAllLostItems = itemRepository.findAll();
        listOfAllLostItems.parallelStream().forEach(item -> {
            if(item.getSetOfUsersIds() != null){
               item.getSetOfUsersIds().forEach(userId -> {
                   Optional<User> user = userRepository.findById(userId);
                   if(user.isEmpty())
                       throw new RuntimeException("Could not find User");
                   map.put(item,user.get());
               });
            }
        });
        return map;
    }

}
