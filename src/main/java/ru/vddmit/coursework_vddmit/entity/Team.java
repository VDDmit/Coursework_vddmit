package ru.vddmit.coursework_vddmit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vddmit.coursework_vddmit.entity.gamification.TeamAchievement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamAchievement> achievements = new ArrayList<>();
}
