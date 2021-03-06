package com.gb.zettro.market.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthyear")
    private Integer birthyear;

    @Column(name = "sex")
    private Character sex;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "hobbies")
    private String hobbies;
}
