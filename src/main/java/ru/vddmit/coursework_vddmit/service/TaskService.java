package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.dto.task.TaskCreateDto;
import ru.vddmit.coursework_vddmit.dto.task.TaskDto;
import ru.vddmit.coursework_vddmit.dto.task.TaskUpdateDto;
import ru.vddmit.coursework_vddmit.entity.Project;
import ru.vddmit.coursework_vddmit.entity.Task;
import ru.vddmit.coursework_vddmit.entity.User;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.ProjectRepository;
import ru.vddmit.coursework_vddmit.repository.TaskRepository;
import ru.vddmit.coursework_vddmit.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        log.info("Получение списка всех задач.");
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskDto getTaskById(UUID id) {
        log.info("Поиск задачи с ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Задача с ID {} не найдена.", id);
                    return new ResourceNotFoundException("Задача с ID " + id + " не найдена.");
                });
        return mapToDto(task);
    }

    @Transactional
    public TaskDto createTask(TaskCreateDto taskCreateDto) {
        log.info("Создание новой задачи: {}", taskCreateDto);
        Task task = new Task();
        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setProject(getProjectById(taskCreateDto.getProjectId()));

        if (taskCreateDto.getAssignedUserId() != null) {
            task.setAssignedUser(getUserById(taskCreateDto.getAssignedUserId()));
        }

        Task savedTask = taskRepository.save(task);
        log.info("Задача успешно создана с ID: {}", savedTask.getId());
        return mapToDto(savedTask);
    }

    @Transactional
    public TaskDto updateTask(UUID id, TaskUpdateDto taskUpdateDto) {
        log.info("Обновление задачи с ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Задача с ID {} не найдена для обновления.", id);
                    return new ResourceNotFoundException("Задача с ID " + id + " не найдена для обновления.");
                });

        if (taskUpdateDto.getTitle() != null) {
            task.setTitle(taskUpdateDto.getTitle());
        }
        if (taskUpdateDto.getDescription() != null) {
            task.setDescription(taskUpdateDto.getDescription());
        }
        if (taskUpdateDto.getCompleted() != null) {
            task.setCompleted(taskUpdateDto.getCompleted());
        }
        if (taskUpdateDto.getProjectId() != null) {
            task.setProject(getProjectById(taskUpdateDto.getProjectId()));
        }
        if (taskUpdateDto.getAssignedUserId() != null) {
            task.setAssignedUser(getUserById(taskUpdateDto.getAssignedUserId()));
        }

        Task updatedTask = taskRepository.save(task);
        log.info("Задача с ID {} успешно обновлена.", id);
        return mapToDto(updatedTask);
    }

    @Transactional
    public void deleteTask(UUID id) {
        log.info("Удаление задачи с ID: {}", id);
        if (!taskRepository.existsById(id)) {
            log.error("Задача с ID {} не найдена для удаления.", id);
            throw new ResourceNotFoundException("Задача с ID " + id + " не найдена для удаления.");
        }
        taskRepository.deleteById(id);
        log.info("Задача с ID {} успешно удалена.", id);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setProjectId(task.getProject() != null ? task.getProject().getId() : null);
        dto.setAssignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null);
        return dto;
    }

    private Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Проект с ID " + projectId + " не найден."));
    }

    private User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID " + userId + " не найден."));
    }
}
