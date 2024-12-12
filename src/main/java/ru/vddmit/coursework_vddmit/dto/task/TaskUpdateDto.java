package ru.vddmit.coursework_vddmit.dto.task;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskUpdateDto {
    private String title;
    private String description;
    private Boolean completed;
    private UUID projectId;
    private UUID assignedUserId;
}
