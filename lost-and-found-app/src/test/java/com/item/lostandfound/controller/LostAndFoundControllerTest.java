package com.item.lostandfound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.lostandfound.config.SpringTestConfiguration;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import com.item.lostandfound.service.ItemService;
import com.item.lostandfound.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestConfiguration.class)    //Passing the security config class details.
@AutoConfigureWebMvc
class LostAndFoundControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private LostAndFoundController lostAndFoundController;

    @MockBean
    private SecurityContext securityContext;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(lostAndFoundController);
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //@Test
    @WithUserDetails("shail@mail.com")
    void uploadLostItems() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "input.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        Mockito.when(this.itemService.saveLostItems(file)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/lostAndFound/admin/uploadLostItems")
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("john@mail.com")
    void getLostItems() throws Exception {
        Mockito.when(this.itemService.getLostItems()).thenReturn(getListOfLostItems());
        mockMvc.perform(MockMvcRequestBuilders.get("/lostAndFound/public/lostItems")
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @WithUserDetails("john@mail.com")
    void claimLostItems() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = new Item();
        item.setItemName("Laptop");
        item.setQuantity(1);
        item.setPlace("Airport");
        ClaimItemModel claimItemModel = new ClaimItemModel(1, item);
        Item itemWithUserIds = getListOfLostItems().get(0);
        Set<Integer> listOfIds = itemWithUserIds.getSetOfUsersIds() != null ? itemWithUserIds.getSetOfUsersIds() : new HashSet<>();
        listOfIds.add(claimItemModel.userId());
        itemWithUserIds.setSetOfUsersIds(listOfIds);
        Mockito.when(this.itemService.claimItem(claimItemModel)).thenReturn(itemWithUserIds);
        mockMvc.perform(MockMvcRequestBuilders.post("/lostAndFound/public/claimLostItems")
                .content(objectMapper.writeValueAsString(claimItemModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("shail@mail.com")
    void getClaimedItems() throws Exception {
        Map<Item, String> map = new HashMap<>();
        map.put(getListOfLostItems().get(1), "1:JohnDoe");
        Mockito.when(this.itemService.getClaimedItems()).thenReturn(map);
        mockMvc.perform(MockMvcRequestBuilders.get("/lostAndFound/admin/getClaimedItems")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("john@mail.com")
    void getAllUsers() throws Exception {
        Mockito.when(this.userService.getUsers()).thenReturn(getListOfUsers());
        mockMvc.perform(MockMvcRequestBuilders.get("/lostAndFound/public/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("john@mail.com")
    void register() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(this.userService.register(getListOfUsers().get(0))).thenReturn(getListOfUsers().get(0));
        mockMvc.perform(MockMvcRequestBuilders.post("/lostAndFound/public/registerUser")
                        .content(objectMapper.writeValueAsString(getListOfUsers().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
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

    private List<User> getListOfUsers() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setUsername("John Doe");
        user.setEmail("johndoe@test.com");
        user.setPassword("johndoe@123");
        user.setRoles("ROLE_USER");
        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("Jane Doe");
        user2.setEmail("janedoe@test.com");
        user2.setPassword("janedoe@123");
        user2.setRoles("ROLE_ADMIN");
        userList.add(user);
        userList.add(user2);
        return userList;
    }
}