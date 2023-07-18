package com.flatdevs.virtualassistant.student.entity;

import com.flatdevs.virtualassistant.entity.BaseEntity;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseEntity extends BaseEntity {
    private String name;
    private BigDecimal creditHours;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> prerequisites;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity type;

}
