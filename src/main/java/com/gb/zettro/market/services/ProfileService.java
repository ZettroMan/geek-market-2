package com.gb.zettro.market.services;

import com.gb.zettro.market.entities.Profile;
import com.gb.zettro.market.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    public Profile saveOrUpdate(Profile profile) {
        return profileRepository.save(profile);
    }
}
