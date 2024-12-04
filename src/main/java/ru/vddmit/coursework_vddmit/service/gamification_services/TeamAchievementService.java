package ru.vddmit.coursework_vddmit.service.gamification_services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vddmit.coursework_vddmit.repository.TeamAchievementRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamAchievementService {
    private final TeamAchievementRepository teamAchievementRepository;


}
