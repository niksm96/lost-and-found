package com.item.lostandfound.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String itemName;

    private Integer quantity;

    private String place;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class, cascade = { CascadeType.ALL })
    @JoinTable(name = "Users_Items", joinColumns = { @JoinColumn(name = "itemId") }, inverseJoinColumns = {
            @JoinColumn(name = "id") })
    private List<User> listOfUsers;
}
