package com.item.lostandfound.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * User is persistent java object class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId" , unique = true)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email" , unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;
}
