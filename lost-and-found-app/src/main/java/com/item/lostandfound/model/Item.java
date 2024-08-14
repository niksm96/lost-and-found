package com.item.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * Item is persistent java object class.
 */
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
    @Column(name = "itemId" , unique = true)
    private Integer itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "place")
    private String place;

    @Column(name = "userIds")
    private Set<Integer> setOfUsersIds;
}
