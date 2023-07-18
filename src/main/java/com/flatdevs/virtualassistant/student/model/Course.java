package com.flatdevs.virtualassistant.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String name;
    private BigDecimal creditHours;
    private String academicPlan;
    private List<String> prerequisites;
    private String type;
}
