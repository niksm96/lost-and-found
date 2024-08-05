package com.item.lostandfound.controller;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import com.item.lostandfound.service.UserService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a Rest Controller class of the Lost And Found App containing various rest calls both for admin and user.
 */
@RestController
@RequestMapping("/lostAndFound")
public class LostAndFoundController {

    private static final Logger logger = LoggerFactory.getLogger(LostAndFoundController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /**
     * Rest API to upload file containing lost items and store them in DB. Only accessible by admin
     * @param file Takes in a multipart file in text format containing list of lost items.
     * @return HttpStatus OK if the upload was successful, else Internal Server Error.
     */
    @PostMapping("/admin/uploadLostItems")
    public ResponseEntity<?> uploadLostItems(@RequestBody MultipartFile file) {
        Map<String, String> map = new HashMap<>();

        map.put("fileName", file.getOriginalFilename());
        map.put("fileSize", String.valueOf(file.getSize()));
        map.put("fileContentType", file.getContentType());
        map.put("message", "File upload done");

        boolean isItemsSaved = itemService.saveLostItems(file);
        if (!isItemsSaved) {
            logger.error("File {} could not be upload", file);
            return new ResponseEntity<>("File could not be uploaded", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("File upload is done : {}", map);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Rest API to fetch all the lost items.
     * @return list of lost items
     */
    @GetMapping("/public/lostItems")
    public ResponseEntity<?> getLostItems() {
        List<Item> listOfLostItems = itemService.getLostItems();
        if (!listOfLostItems.isEmpty()) {
            logger.info("Successfully fetched all the lost items: {}", listOfLostItems);
            return new ResponseEntity<>(listOfLostItems, HttpStatus.OK);
        }
        logger.error("Could not fetch lost items");
        return new ResponseEntity<String>("Could not fetch lost items", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Rest API to claim the lost items by a user.
     * @param claimItems Item that can be claimed
     * @return HttpStatus OK if the claiming of the item was successful else Internal Server Error.
     *         Also, Bad Request in case of improper input.
     */
    @PostMapping("/public/claimLostItems")
    public ResponseEntity<?> claimLostItems(@RequestBody ClaimItemModel claimItems) {
        if (claimItems != null && claimItems.item() != null && claimItems.userId() != null) {
            Item item = itemService.claimItem(claimItems);
            if (item != null) {
                logger.info("Successfully claimed the lost item: {} by the user: {}", item, claimItems.userId());
                return new ResponseEntity<>("Item claimed: " + item, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Item couldn't be claimed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.error("Improper input, 1 or more field values missing : {}", claimItems);
        return new ResponseEntity<String>("Improper input, 1 or more field values missing : " +  claimItems, HttpStatus.BAD_REQUEST);
    }

    /**
     * Rest API to list the claimed items. Only accessible by admin.
     * @return HttpStatus OK, displayed with list of claimed items along with User associated with it else Internal Server Error.
     */
    @GetMapping("/admin/getClaimedItems")
    public ResponseEntity<?> getClaimedItems() {
        Map<Item, String> listOfLostItems = itemService.getClaimedItems();
        if (!listOfLostItems.isEmpty()) {
            logger.info("Successfully fetched the list of claimed lost items: {}", listOfLostItems);
            return new ResponseEntity<>(listOfLostItems, HttpStatus.OK);
        }
        logger.info("Could not find any claimed lost items");
        return new ResponseEntity<String>("Could not find any claimed lost items", HttpStatus.NOT_FOUND);
    }

    /**
     * Rest API to list all the users.
     * @return HttpStatus OK, displayed with list of users else Internal Server Error.
     */
    @GetMapping("/public/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getUsers();
        if (!users.isEmpty())
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Rest API to register a user.
     * @param user
     * @return HttpStatus OK, if the user registered successfully else Internal Server Error.
     */
    @PostMapping("/public/registerUser")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.register(user) != null) {
            return ResponseEntity.ok("User registered: " + user.getUsername());
        }
        return new ResponseEntity<String>("User couldn't be registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
