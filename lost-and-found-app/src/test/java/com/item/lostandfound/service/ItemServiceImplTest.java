package com.item.lostandfound.service;

import com.item.lostandfound.dao.ItemRepository;
import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.exceptions.NoFileUploadedException;
import com.item.lostandfound.model.ClaimItemModel;
import com.item.lostandfound.model.Item;
import com.item.lostandfound.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(ItemServiceImplTest.class);
    }

    @Test
    void saveLostItems() throws NoFileUploadedException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "input.txt",
                MediaType.TEXT_PLAIN_VALUE,
                ("ItemName: Laptop\n" + "Quantity: 2\n" + "Place: Airport\n" + "------------------------\n" + "ItemName: Jewels\n" + "Quantity: 1\n" + "Place: Taxi\n" + "------------------------\n" + Arrays.toString("------------------------".getBytes())).getBytes()
        );
        Mockito.when(this.itemRepository.saveAll(listOfLostItemsToSave())).thenReturn(getListOfLostItems());
        this.itemService.saveLostItems(file);
        Mockito.verify(this.itemRepository, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    void getLostItems() {
        Mockito.when(this.itemRepository.findAll()).thenReturn(getListOfLostItems());
        this.itemService.getLostItems();
        Mockito.verify(this.itemRepository, Mockito.times(1)).findAll();
    }

    @Test
    void claimItem() {
        Item item = new Item();
        item.setItemName("Laptop");
        item.setQuantity(1);
        item.setPlace("Airport");
        ClaimItemModel claimItemModel = new ClaimItemModel(1, item);
        Item itemWithUserIds = getListOfLostItems().get(0);
        Set<Integer> listOfIds = itemWithUserIds.getSetOfUsersIds() != null ? itemWithUserIds.getSetOfUsersIds() : new HashSet<>();
        listOfIds.add(claimItemModel.userId());
        itemWithUserIds.setSetOfUsersIds(listOfIds);

        Mockito.when(this.itemRepository.findByItemNameAndPlace("Laptop", "Airport")).thenReturn(Optional.ofNullable(getListOfLostItems().get(0)));
        Mockito.when(this.itemRepository.save(Mockito.any(Item.class))).thenReturn(itemWithUserIds);

        this.itemService.claimItem(claimItemModel);
        Mockito.verify(this.itemRepository, Mockito.times(1)).findByItemNameAndPlace(Mockito.any(String.class), Mockito.any(String.class));
        Mockito.verify(this.itemRepository, Mockito.times(1)).save(Mockito.any(Item.class));
    }

    @Test
    void getClaimedItems() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("John Doe");
        Mockito.when(this.itemRepository.findAll()).thenReturn(getListOfLostItems());
        Mockito.when(this.userRepository.findById(1)).thenReturn(Optional.of(user));
        this.itemService.getClaimedItems();
        Mockito.verify(this.itemRepository, Mockito.times(1)).findAll();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any(Integer.class));

    }

    private List<Item> listOfLostItemsToSave() {
        List<Item> itemList = new ArrayList<>();
        Set<Integer> setOfUserIds = new HashSet<>();
        Item item1 = new Item();
        item1.setItemName("Laptop");
        item1.setQuantity(2);
        item1.setPlace("Airport");
        Item item2 = new Item();
        item2.setItemName("Jewels");
        item2.setQuantity(1);
        item2.setPlace("Taxi");
        itemList.add(item1);
        itemList.add(item2);
        return itemList;
    }

    private List<Item> getListOfLostItems() {
        List<Item> itemList = new ArrayList<>();
        Set<Integer> setOfUserIds = new HashSet<>();
        Item item1 = new Item();
        item1.setItemId(1);
        item1.setItemName("Laptop");
        item1.setQuantity(2);
        item1.setPlace("Airport");
        setOfUserIds.add(1);
        item1.setSetOfUsersIds(setOfUserIds);
        Item item2 = new Item();
        item2.setItemId(2);
        item2.setItemName("Jewels");
        item2.setQuantity(1);
        item2.setPlace("Taxi");
        itemList.add(item1);
        itemList.add(item2);
        return itemList;
    }
}