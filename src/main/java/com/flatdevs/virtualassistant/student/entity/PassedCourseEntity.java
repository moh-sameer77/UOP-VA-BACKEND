package com.flatdevs.virtualassistant.student.entity;
import com.flatdevs.virtualassistant.entity.BaseEntity;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "passed_courses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PassedCourseEntity extends BaseEntity {
    @ManyToOne
    private EnrolledStudentEntity enrolledStudent;
    @ManyToOne
    private CourseEntity course;
    private double mark;
}



