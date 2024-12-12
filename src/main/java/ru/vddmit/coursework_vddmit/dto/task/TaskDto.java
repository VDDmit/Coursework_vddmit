package ru.vddmit.coursework_vddmit.dto.task;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private UUID projectId;
    private UUID assignedUserId;
}
