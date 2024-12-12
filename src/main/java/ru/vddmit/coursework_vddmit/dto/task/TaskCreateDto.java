package ru.vddmit.coursework_vddmit.dto.task;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskCreateDto {
    private String title;
    private String description;
    private UUID projectId;
    private UUID assignedUserId;
}
