package ru.vddmit.coursework_vddmit.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String username;
    private int xp;
    private int lvl;
}
