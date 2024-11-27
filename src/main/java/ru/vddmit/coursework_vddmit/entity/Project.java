package ru.vddmit.coursework_vddmit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();
    @ManyToMany(mappedBy = "projects")
    private List<User> users = new ArrayList<>();
}
