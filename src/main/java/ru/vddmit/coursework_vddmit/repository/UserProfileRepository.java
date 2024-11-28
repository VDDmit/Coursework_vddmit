package ru.vddmit.coursework_vddmit.repository;


import org.springframework.stereotype.Repository;
import ru.vddmit.coursework_vddmit.entity.gamification.UserProfile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository {

    Optional<UserProfile> findByUserId(UUID userId);

}
