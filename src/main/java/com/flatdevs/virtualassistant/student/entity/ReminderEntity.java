package com.flatdevs.virtualassistant.student.entity;

import com.flatdevs.virtualassistant.entity.BaseEntity;
import com.flatdevs.virtualassistant.user.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ReminderEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    private String title;
    private String note;
    private LocalDateTime firstTime;
    private LocalDateTime secondTime;
    private LocalDateTime date;
    private String color;
}
