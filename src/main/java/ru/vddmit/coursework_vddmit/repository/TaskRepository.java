package ru.vddmit.coursework_vddmit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vddmit.coursework_vddmit.entity.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByAssignedUserId(UUID userId);

    List<Task> findByProjectId(UUID projectId);

    List<Task> findByCompleted(boolean completed);

}
