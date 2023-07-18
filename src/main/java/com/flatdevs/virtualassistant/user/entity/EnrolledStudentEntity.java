package com.flatdevs.virtualassistant.user.entity;
import com.flatdevs.virtualassistant.student.entity.AcademicPlanEntity;
import com.flatdevs.virtualassistant.student.entity.PassedCourseEntity;
import com.flatdevs.virtualassistant.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "enrolled_users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EnrolledStudentEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @ManyToOne
    private AcademicPlanEntity academicPlan;

    private String major;
    private BigDecimal academicYear;

    @OneToMany(mappedBy = "enrolledStudent", cascade = CascadeType.ALL)
    private List<PassedCourseEntity> passedCourseEntities = new ArrayList<>();

    private BigDecimal creditHours;
    private BigDecimal academicProgress;
    private BigDecimal gpa;

    public static class GradeConverter {
        private Map<String, BigDecimal> gradeValues;

        public GradeConverter() {
            gradeValues = new HashMap<>();
            gradeValues.put("A", new BigDecimal("4.00"));
            gradeValues.put("A-", new BigDecimal("3.67"));
            gradeValues.put("B+", new BigDecimal("3.33"));
            gradeValues.put("B", new BigDecimal("3.00"));
            gradeValues.put("B-", new BigDecimal("2.67"));
            gradeValues.put("C+", new BigDecimal("2.33"));
            gradeValues.put("C", new BigDecimal("2.00"));
            gradeValues.put("C-", new BigDecimal("1.67"));
            gradeValues.put("D+", new BigDecimal("1.33"));
            gradeValues.put("D", new BigDecimal("1.00"));
            gradeValues.put("D-", new BigDecimal("0.67"));
            gradeValues.put("F", BigDecimal.ZERO);
        }

        public BigDecimal convert(String grade) {
            return gradeValues.get(grade);
        }
    }
}
