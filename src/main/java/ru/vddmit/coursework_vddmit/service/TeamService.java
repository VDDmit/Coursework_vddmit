package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.entity.Team;
import ru.vddmit.coursework_vddmit.entity.User;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.TeamRepository;
import ru.vddmit.coursework_vddmit.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        log.info("Запрос на получение всех команд из базы данных.");
        List<Team> teams = teamRepository.findAll();
        log.info("Успешно получено {} команд(ы).", teams.size());
        return teams;
    }

    @Transactional(readOnly = true)
    public Team getTeamById(UUID id) {
        log.info("Запрос на получение команды с ID: {}", id);
        return teamRepository.findById(id).orElseThrow(() -> {
            log.error("Команда с ID {} не найдена.", id);
            return new ResourceNotFoundException("Команда с указанным ID не найдена: " + id);
        });
    }

    @Transactional
    public Team createTeam(Team team) {
        log.info("Создание команды: {}", team.getName());
        Optional<Team> existingTeam = teamRepository.findByName(team.getName());
        if (existingTeam.isPresent()) {
            throw new IllegalArgumentException("Команда с таким именем уже существует");
        } else {
            return teamRepository.save(team);
        }
    }

    @Transactional
    public void addMemberToTeam(UUID teamId, UUID userId) {
        log.info("Запрос на добавление пользователя с ID {} в команду с ID {}", userId, teamId);
        Team team = getTeamById(teamId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID " + userId + " не найден."));
        team.getMembers().add(user);
        user.setTeam(team);
        teamRepository.save(team);
        userRepository.save(user);

        log.info("Пользователь с ID {} успешно добавлен в команду с ID {}", userId, teamId);
    }

    @Transactional
    public void deleteTeamById(UUID id) {
        teamRepository.deleteById(id);
    }
}
