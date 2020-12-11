package com.gb.zettro.market.dto;

import com.gb.zettro.market.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDto {
    private Long id;
    private String username;
    private String firstname;
    private String secondname;
    private Integer birthyear;
    private String phone;
    private String city;
    private String address;
    private Character sex;
    private String hobbies;
    private String confirmationPassword;

    public ProfileDto(Profile p) {
        this.id = p.getId();
        this.username = p.getUser().getUsername();
        this.firstname = p.getFirstname();
        this.secondname = p.getSecondname();
        this.birthyear = p.getBirthyear();
        this.phone = p.getPhone();
        this.city = p.getCity();
        this.address = p.getAddress();
        this.sex = p.getSex();
        this.hobbies = p.getHobbies();
    }
}
