package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.entity.Role;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        log.info("Получение всех ролей из базы данных");
        List<Role> roles = roleRepository.findAll();
        log.info("Найдено {} ролей", roles.size());
        return roles;
    }

    @Transactional(readOnly = true)
    public Role getRoleById(UUID id) {
        log.info("Получение роли с ID: {}", id);
        return roleRepository.findById(id).orElseThrow(() -> {
            log.error("Роль с ID {} не найдена", id);
            return new ResourceNotFoundException("Роль с ID " + id + " не найдена");
        });
    }

    @Transactional
    public Role createRole(Role role) {
        log.info("Создание новой роли с названием: {}", role.getName());
        Role savedRole = roleRepository.save(role);
        log.info("Роль с ID {} успешно создана", savedRole.getId());
        return savedRole;
    }

    @Transactional
    public Role updateRole(UUID id, Role roleDetails) {
        log.info("Обновление роли с ID: {}", id);
        Role role = roleRepository.findById(id).orElseThrow(() -> {
            log.error("Роль с ID {} не найдена для обновления", id);
            return new ResourceNotFoundException("Роль с ID " + id + " не найдена");
        });

        role.setName(roleDetails.getName());
        Role updatedRole = roleRepository.save(role);
        log.info("Роль с ID {} успешно обновлена", updatedRole.getId());
        return updatedRole;
    }

    @Transactional
    public void deleteRole(UUID id) {
        log.info("Удаление роли с ID: {}", id);
        if (!roleRepository.existsById(id)) {
            log.error("Роль с ID {} не найдена для удаления", id);
            throw new ResourceNotFoundException("Роль с ID " + id + " не найдена");
        }
        roleRepository.deleteById(id);
        log.info("Роль с ID {} успешно удалена", id);
    }
}

