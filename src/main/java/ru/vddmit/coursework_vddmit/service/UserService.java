package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.UserRepository;
import ru.vddmit.coursework_vddmit.entity.User;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Получение всех пользователей");
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        log.info("Создание пользователя: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Попытка создать пользователя с уже существующим email: {}", user.getEmail());
            throw new IllegalArgumentException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Ошибка при сохранении пользователя с email {}: {}", user.getEmail(), e.getMessage());
            throw new IllegalStateException("Не удалось сохранить пользователя");
        } catch (Exception e) {
            log.error("Неожиданная ошибка при создании пользователя: {}", e.getMessage());
            throw new RuntimeException("Ошибка при создании пользователя");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        log.info("Получение пользователя по ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", id);
                    return new ResourceNotFoundException("Пользователь с ID " + id + " не найден");
                });
    }

    @Transactional
    public User updateUser(UUID id, User updatedUser) {
        log.info("Обновление пользователя с ID: {}", id);
        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
            userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
            log.warn("Попытка обновить email на уже существующий: {}", updatedUser.getEmail());
            throw new IllegalArgumentException("Пользователь с email " + updatedUser.getEmail() + " уже существует");
        }

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setXp(updatedUser.getXp());
        existingUser.setLvl(updatedUser.getLvl());
        existingUser.setTeam(updatedUser.getTeam());
        existingUser.setAchievements(updatedUser.getAchievements());
        existingUser.setProjects(updatedUser.getProjects());
        existingUser.setRoles(updatedUser.getRoles());

        try {
            return userRepository.save(existingUser);
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя с ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Ошибка при обновлении пользователя");
        }
    }

    @Transactional
    public void deleteUser(UUID id) {
        log.info("Удаление пользователя с ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Попытка удалить несуществующего пользователя с ID: {}", id);
            throw new ResourceNotFoundException("Пользователь с ID " + id + " не найден");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователя с ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Ошибка при удалении пользователя");
        }
    }
}
