package ru.vddmit.coursework_vddmit.dto.user;

import lombok.Data;

@Data
public class UserCreateOrUpdateDto {
    private String email;
    private String username;
    private String password;
    private int xp;
    private int lvl;
}
