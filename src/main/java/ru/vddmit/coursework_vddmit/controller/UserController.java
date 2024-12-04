package ru.vddmit.coursework_vddmit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vddmit.coursework_vddmit.dto.user.UserCreateOrUpdateDto;
import ru.vddmit.coursework_vddmit.entity.User;
import ru.vddmit.coursework_vddmit.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        log.info("Получен запрос на получение пользователя с ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreateOrUpdateDto userDto) {
        log.info("Получен запрос на создание пользователя: {}", userDto.getEmail());
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id,
                                           @RequestBody UserCreateOrUpdateDto userDto) {
        log.info("Получен запрос на обновление пользователя с ID: {}", id);
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("Получен запрос на удаление пользователя с ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
