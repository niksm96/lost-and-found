package com.item.lostandfound.controller;

import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/uploadLostItems")
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

    @GetMapping("/lostItems")
    public ResponseEntity<?> getLostItems(){
        List<Item> listOfLostItems = itemService.getLostItems();
        if(!listOfLostItems.isEmpty()){
            return ResponseEntity.ok(listOfLostItems);
        }
        return ResponseEntity.internalServerError().body("Could not fetch lost items");
    }

    @GetMapping("/getClaimedItems")
    public ResponseEntity<?> getClaimedItems(){
        Map<Item, User> listOfLostItems = itemService.getClaimedItems();
        if(!listOfLostItems.isEmpty()){
            return ResponseEntity.ok(listOfLostItems);
        }
        return ResponseEntity.internalServerError().body("Could not claimed lost items");
    }
}
