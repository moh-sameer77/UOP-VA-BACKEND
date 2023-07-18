package com.flatdevs.virtualassistant.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String password;
    private String studentType;
    private String token;
    private Boolean isAdmin;
}
