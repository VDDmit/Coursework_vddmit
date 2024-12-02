package ru.vddmit.coursework_vddmit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.entity.Task;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        log.info("Получение списка всех задач.");
        List<Task> tasks = taskRepository.findAll();
        log.info("Найдено задач: {}", tasks.size());
        return tasks;
    }

    @Transactional(readOnly = true)
    public Task getTaskById(UUID id) {
        log.info("Поиск задачи с ID: {}", id);
        return taskRepository.findById(id).orElseThrow(() -> {
            log.error("Задача с ID {} не найдена.", id);
            return new ResourceNotFoundException("Задача с ID " + id + " не найдена.");
        });
    }

    @Transactional
    public Task createTask(Task task) {
        log.info("Создание новой задачи: {}", task);
        Task savedTask = taskRepository.save(task);
        log.info("Задача успешно создана с ID: {}", savedTask.getId());
        return savedTask;
    }

    @Transactional
    public Task updateTask(UUID id, Task updatedTask) {
        log.info("Обновление задачи с ID: {}", id);
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
            existingTask.setAssignedUser(updatedTask.getAssignedUser());
            existingTask.setProject(updatedTask.getProject());
            Task savedTask = taskRepository.save(existingTask);
            log.info("Задача с ID {} успешно обновлена.", id);
            return savedTask;
        }).orElseThrow(() -> {
            log.error("Задача с ID {} не найдена для обновления.", id);
            return new ResourceNotFoundException("Задача с ID " + id + " не найдена для обновления.");
        });
    }

    @Transactional
    public void deleteTask(UUID id) {
        log.info("Удаление задачи с ID: {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            log.info("Задача с ID {} успешно удалена.", id);
        } else {
            log.error("Задача с ID {} не найдена для удаления.", id);
            throw new ResourceNotFoundException("Задача с ID " + id + " не найдена для удаления.");
        }
    }
}
