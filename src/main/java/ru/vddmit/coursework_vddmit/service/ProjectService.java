package ru.vddmit.coursework_vddmit.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vddmit.coursework_vddmit.entity.Project;
import ru.vddmit.coursework_vddmit.exception.ResourceNotFoundException;
import ru.vddmit.coursework_vddmit.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;


    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        log.info("Получение списка всех проеуктов. ");
        List<Project> projects = projectRepository.findAll();
        log.info("Найдено проектов: {}", projects.size());
        return projects;
    }

    @Transactional(readOnly = true)
    public Project getProjectById(UUID id) {
        log.info("Поиск проекта с ID: {}", id);
        return projectRepository.findById(id).orElseThrow(() -> {
            log.error("Проект с ID {} не найден", id);
            return new ResourceNotFoundException("Проект с ID " + id + " не найден");
        });
    }

    @Transactional
    public Project createProject(Project project) {
        log.info("Создание нового проекта: {}", project);
        Project savedProject = projectRepository.save(project);
        log.info("Проект успешно создан с ID: {}", savedProject.getId());
        return savedProject;
    }

    @Transactional
    public Project updateProject(UUID id, Project project) {
        log.info("Обновление проекта с ID: {}", id);
        return projectRepository.findById(id).map(existingProject -> {
            existingProject.setName(project.getName());
            Project savedProject = projectRepository.save(existingProject);
            log.info("Проект с ID {} успешно обновлен. ", id);
            return savedProject;
        }).orElseThrow(() -> {
            log.error("Проект с ID {} не найден для обновления.", id);
            return new ResourceNotFoundException("Проект с ID " + id + " не найден для обновления.");
        });
    }
    @Transactional
    public void deleteProject(UUID id) {
        log.info("Удвление проекта с ID: {}", id);
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            log.info("Проект с ID {} успешно удален.",id);
        } else{
            log.error("Проект с ID {} не найден для удаления.", id);
            throw new ResourceNotFoundException("Проект с ID " + id + " не найден для удаления.");

        }
    }

}
