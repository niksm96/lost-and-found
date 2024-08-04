package com.item.lostandfound.controller;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import com.item.lostandfound.service.UserService;
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

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/uploadLostItems")
    public ResponseEntity<?> uploadLostItems(@RequestBody MultipartFile file){
        Map<String, String> map = new HashMap<>();

        // Populate the map with file details
        map.put("fileName", file.getOriginalFilename());
        map.put("fileSize", String.valueOf(file.getSize()));
        map.put("fileContentType", file.getContentType());

        // File upload is successful
        map.put("message", "File upload done");

        boolean isItemsSaved = itemService.saveLostItems(file);
        if(isItemsSaved){
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/public/lostItems")
    public ResponseEntity<?> getLostItems(){
        List<Item> listOfLostItems = itemService.getLostItems();
        if(!listOfLostItems.isEmpty()){
            return ResponseEntity.ok(listOfLostItems);
        }
        return ResponseEntity.internalServerError().body("Could not fetch lost items");
    }

    @PostMapping("/public/claimLostItems")
    public ResponseEntity<?> claimLostItems(@RequestBody ClaimItemModel claimItems){
        Item item = itemService.claimItem(claimItems);
        if(item != null){
            return ResponseEntity.ok("Item claimed: " + item);
        }
        return ResponseEntity.internalServerError().body("Item couldn't be claimed");
    }

    @GetMapping("/admin/getClaimedItems")
    public ResponseEntity<?> getClaimedItems(){
        Map<Item, User> listOfLostItems = itemService.getClaimedItems();
        if(!listOfLostItems.isEmpty()){
            return ResponseEntity.ok(listOfLostItems);
        }
        return ResponseEntity.internalServerError().body("Could not claimed lost items");
    }

    @GetMapping("/public/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getUsers();
        if (!users.isEmpty())
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/public/registerUser")
    public ResponseEntity<?> register(@Validated @RequestBody User user) {
        if(userService.register(user) != null){
            return ResponseEntity.ok("User registered: " + user.getUsername());
        }
        return new ResponseEntity<String>("User couldn't be registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
