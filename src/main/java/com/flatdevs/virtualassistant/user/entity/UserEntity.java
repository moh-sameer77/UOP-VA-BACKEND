package com.flatdevs.virtualassistant.user.entity;


import com.flatdevs.virtualassistant.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserEntity extends BaseEntity
{
    @OneToOne
    private ProfileImageEntity image;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private StudentType studentType;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDateTime dateOfBirth;
    private Boolean isAdmin;

    public String getUsername() {
        return email;
    }

    public enum StudentType
    {
        ENROLLED, NOT_ENROLLED
    }

    public enum Gender
    {
        MALE, FEMALE
    }
}
