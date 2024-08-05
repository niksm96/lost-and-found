package com.item.lostandfound.controller;

import com.item.lostandfound.model.Item;
import com.item.lostandfound.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {LostAndFoundControllerTest.class})
class LostAndFoundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //@Test
    void uploadLostItems() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "input.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        Mockito.when(this.itemService.saveLostItems(file)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/uploadLostItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].fileName")
                                .value("input.txt")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[2].message")
                                .value("File upload done")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[3].fileContentType")
                                .value("text/plain")
                );

        Mockito.verify(this.itemService, Mockito.times(1)).saveLostItems(Mockito.any());
    }

    //@Test
    void getLostItems() throws Exception {
        Mockito.when(this.itemService.getLostItems()).thenReturn(getListOfLostItems());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/public/lostItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].itemId")
                                .value(1)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[1].itemName")
                                .value("Laptop")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[2].quantity")
                                .value(2)
                ).andExpect(
                MockMvcResultMatchers.jsonPath("$[3].place")
                        .value("Airport")
        );

    }

    @Test
    void claimLostItems() {
    }

    @Test
    void getClaimedItems() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void register() {
    }

    private List<Item> getListOfLostItems() {
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item();
        item1.setItemId(1);
        item1.setItemName("Laptop");
        item1.setQuantity(2);
        item1.setPlace("Airport");
        Item item2 = new Item();
        item1.setItemId(2);
        item1.setItemName("Jewels");
        item1.setQuantity(1);
        item1.setPlace("Taxi");
        itemList.add(item1);
        itemList.add(item2);
        return itemList;
    }
}