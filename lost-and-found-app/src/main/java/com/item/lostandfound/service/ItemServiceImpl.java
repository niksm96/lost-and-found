package com.item.lostandfound.service;

import com.item.lostandfound.dao.ItemRepository;
import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.exceptions.InvalidFileException;
import com.item.lostandfound.exceptions.LostAndFoundException;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

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
            logger.error("Either failed to create a temp file or failed to transfer the contents of multipart file onto the temp file", e);
            throw new LostAndFoundException("Failed to convert multipart file to temp File", e);
        }
        List<Item> listOfItems = null;
        try {
            listOfItems = FileUtils.listOfItemsFromFile(file);
        } catch (InvalidFileException | IOException e) {
            logger.error("Failed to process the input file", e);
            throw new LostAndFoundException("Failed to process the input file", e);
        }
        return !itemRepository.saveAll(listOfItems).isEmpty();
    }

    /**
     * @return
     */
    @Override
    public List<Item> getLostItems() {
        logger.info("Fetching all the lost items...");
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
        logger.info("Finding the item by name and place to claim from repository: {}", selectedItem);
        Optional<Item> itemOptional = itemRepository.findByItemNameAndPlace(selectedItem.getItemName(), selectedItem.getPlace());
        if (itemOptional.isPresent()) item = itemOptional.get();
        assert item != null;
        logger.info("Item to be claimed by userId: {}", claimItem.userId());
        Set<Integer> listOfUserIds = item.getSetOfUsersIds() != null ? item.getSetOfUsersIds() : new HashSet<>();
        listOfUserIds.add(claimItem.userId());
        item.setSetOfUsersIds(listOfUserIds);
        return itemRepository.save(item);
    }

    /**
     * @return
     */
    @Override
    public Map<Item, String> getClaimedItems() {
        Map<Item, String> map = new HashMap<>();
        List<Item> listOfAllLostItems = itemRepository.findAll();
        listOfAllLostItems.parallelStream()
                .filter(item -> item.getSetOfUsersIds() != null)
                .forEach(item -> item.getSetOfUsersIds()
                        .stream().map(userId -> userRepository.findById(userId)).forEach(user -> {
                            if (user.isEmpty()) logger.error("Could not find User");
                            User claimedUser = user.get();
                            map.put(item, claimedUser.getUserId() + ":" + claimedUser.getUsername());
                        }));
        return map;
    }

}
