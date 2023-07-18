package com.flatdevs.virtualassistant.student.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flatdevs.virtualassistant.entity.BaseEntity;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "academic_plan_courses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AcademicPlanCoursesEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "academic_plan_id")
    @JsonBackReference
    private AcademicPlanEntity academicPlan;
    @ManyToOne
    private CourseEntity course;
}



