package com.item.lostandfound.controller;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import com.item.lostandfound.service.UserService;
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

@RestController
@RequestMapping("/lostAndFound")
public class LostAndFoundController {

    private static final Logger logger = LoggerFactory.getLogger(LostAndFoundController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

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
        return ResponseEntity.ok(map);
    }

    @GetMapping("/public/lostItems")
    public ResponseEntity<?> getLostItems() {
        List<Item> listOfLostItems = itemService.getLostItems();
        if (!listOfLostItems.isEmpty()) {
            logger.info("Successfully fetched all the lost items: {}", listOfLostItems);
            return ResponseEntity.ok(listOfLostItems);
        }
        logger.error("Could not fetch lost items");
        return ResponseEntity.internalServerError().body("Could not fetch lost items");
    }

    @PostMapping("/public/claimLostItems")
    public ResponseEntity<?> claimLostItems(@Validated @RequestBody ClaimItemModel claimItems) {
        if (claimItems != null && claimItems.item() != null && claimItems.userId() != null) {
            Item item = itemService.claimItem(claimItems);
            if (item != null) {
                logger.info("Successfully claimed the lost item: {} by the user: {}", item, claimItems.userId());
                return ResponseEntity.ok("Item claimed: " + item);
            }
            return ResponseEntity.internalServerError().body("Item couldn't be claimed");
        }
        logger.error("Improper input, 1 or more field values missing : {}", claimItems);
        return ResponseEntity.badRequest().body("Improper input, 1 or more field values missing: " + claimItems);
    }

    @GetMapping("/admin/getClaimedItems")
    public ResponseEntity<?> getClaimedItems() {
        Map<Item, User> listOfLostItems = itemService.getClaimedItems();
        if (!listOfLostItems.isEmpty()) {
            return ResponseEntity.ok(listOfLostItems);
        }
        return ResponseEntity.internalServerError().body("Could not claimed lost items");
    }

    @GetMapping("/public/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getUsers();
        if (!users.isEmpty())
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/public/registerUser")
    public ResponseEntity<?> register(@Validated @RequestBody User user) {
        if (userService.register(user) != null) {
            return ResponseEntity.ok("User registered: " + user.getUsername());
        }
        return new ResponseEntity<String>("User couldn't be registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
