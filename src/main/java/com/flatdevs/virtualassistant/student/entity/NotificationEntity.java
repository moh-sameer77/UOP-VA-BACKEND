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
@Table(name = "notifications")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class NotificationEntity extends BaseEntity {

    private String title;
    private String description;

}
