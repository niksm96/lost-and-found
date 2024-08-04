package com.item.lostandfound.controller;

import com.item.lostandfound.model.User;
import com.item.lostandfound.service.LostAndFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LostAndFoundController {

    @Autowired
    private LostAndFoundService lostAndFoundService;

    @GetMapping("/sampleAPI")
    public ResponseEntity<String> sampleAPI(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping("/uploadLostItems")
    public ResponseEntity<?> uploadLostItems(@RequestBody MultipartFile file){
        Map<String, String> map = new HashMap<>();

        // Populate the map with file details
        map.put("fileName", file.getOriginalFilename());
        map.put("fileSize", String.valueOf(file.getSize()));
        map.put("fileContentType", file.getContentType());

        // File upload is successful
        map.put("message", "File upload done");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = lostAndFoundService.getUsers();
        if (!users.isEmpty())
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
