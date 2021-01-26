package com.gb.zettro.market.controllers;

import com.gb.zettro.market.dto.ProfileDto;
import com.gb.zettro.market.entities.Profile;
import com.gb.zettro.market.entities.User;
import com.gb.zettro.market.exceptions.MarketError;
import com.gb.zettro.market.exceptions.ResourceNotFoundException;
import com.gb.zettro.market.services.ProfileService;
import com.gb.zettro.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(produces = "application/json")
    public ProfileDto getCurrentProfile(Principal principal) {
        Profile p = profileService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to find profile for current user"));
        return new ProfileDto(p);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> getCurrentProfile(Principal principal, @RequestBody ProfileDto profileDto) {
        User currentUser = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to find current user"));
        if (profileDto.getConfirmationPassword() == null || !passwordEncoder.matches(profileDto.getConfirmationPassword(), currentUser.getPassword())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Incorrect password"), HttpStatus.BAD_REQUEST);
        }
        Profile p = profileService.findById(profileDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Unable to find profile for current user"));
        p.setFirstname(profileDto.getFirstname());
        p.setSecondname(profileDto.getSecondname());
        p.setBirthyear(profileDto.getBirthyear());
        p.setPhone(profileDto.getPhone());
        p.setCity(profileDto.getCity());
        p.setAddress(profileDto.getAddress());
        p.setSex(profileDto.getSex());
        p.setHobbies(profileDto.getHobbies());
        profileService.saveOrUpdate(p);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
