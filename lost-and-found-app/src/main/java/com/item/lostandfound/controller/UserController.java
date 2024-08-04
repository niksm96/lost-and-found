package com.item.lostandfound.controller;

import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import com.item.lostandfound.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getUsers();
        if (!users.isEmpty())
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/claimLostItems")
    public ResponseEntity<?> claimLostItems(@RequestBody ClaimItemModel claimItems){
        Item item = itemService.claimItem(claimItems);
        if(item != null){
            return ResponseEntity.ok("Item claimed: " + item);
        }
        return ResponseEntity.internalServerError().body("Item couldn't be claimed");
    }
}
