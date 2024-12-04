package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.entity.User;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.UserRepository;
import ru.vddmit.coursework_vddmit.dto.user.UserCreateOrUpdateDto;

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
    public User createUser(UserCreateOrUpdateDto userDto) {
        log.info("Создание пользователя: {}", userDto.getEmail());
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.warn("Попытка создать пользователя с уже существующим email: {}", userDto.getEmail());
            throw new IllegalArgumentException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }
        User user = mapToEntity(userDto);
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Ошибка при сохранении пользователя с email {}: {}", userDto.getEmail(), e.getMessage());
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
    public User updateUser(UUID id, UserCreateOrUpdateDto userDto) {
        log.info("Обновление пользователя с ID: {}", id);
        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(userDto.getEmail()) &&
            userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.warn("Попытка обновить email на уже существующий: {}", userDto.getEmail());
            throw new IllegalArgumentException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }

        existingUser.setEmail(userDto.getEmail());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setXp(userDto.getXp());
        existingUser.setLvl(userDto.getLvl());

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

    private User mapToEntity(UserCreateOrUpdateDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setXp(userDto.getXp());
        user.setLvl(userDto.getLvl());
        return user;
    }
}
