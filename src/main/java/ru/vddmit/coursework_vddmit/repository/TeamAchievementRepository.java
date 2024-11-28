package ru.vddmit.coursework_vddmit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vddmit.coursework_vddmit.entity.gamification.TeamAchievement;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamAchievementRepository extends JpaRepository<TeamAchievement, UUID> {

    List<TeamAchievement> findByTeamId(UUID teamId);

}