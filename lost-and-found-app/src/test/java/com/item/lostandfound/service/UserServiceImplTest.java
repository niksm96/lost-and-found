package com.item.lostandfound.service;

import com.item.lostandfound.dao.UserRepository;
import com.item.lostandfound.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(UserServiceImplTest.class);
    }

    @Test
    void getUsers() {
        Mockito.when(this.userRepository.findAll()).thenReturn(getListOfUsers());
        this.userService.getUsers();
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void register() {
        Mockito.when(this.userRepository.save(getListOfUsers().get(0))).thenReturn(getListOfUsers().get(0));
        Mockito.when(this.passwordEncoder.encode("johndoe@123")).thenReturn("johndoe@123");
        this.userService.register(getListOfUsers().get(0));
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any(User.class));
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