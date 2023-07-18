package com.flatdevs.virtualassistant.user.model;

import com.flatdevs.virtualassistant.student.entity.AcademicPlanEntity;
import com.flatdevs.virtualassistant.student.entity.PassedCourseEntity;
import com.flatdevs.virtualassistant.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolledStudent {


    private String academicPlan;

    private BigDecimal academicYear;

//    private BigDecimal academicProgress;
//    private BigDecimal gpa;

}
