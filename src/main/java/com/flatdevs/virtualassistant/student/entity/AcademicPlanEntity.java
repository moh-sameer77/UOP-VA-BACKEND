package com.flatdevs.virtualassistant.student.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flatdevs.virtualassistant.entity.BaseEntity;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "academic_plans")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AcademicPlanEntity extends BaseEntity {
    private String name;
    private BigDecimal creditHours;

    @OneToMany(mappedBy = "academicPlan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AcademicPlanCoursesEn  tity> academicPlanCoursesEntity = new ArrayList<>();
}

