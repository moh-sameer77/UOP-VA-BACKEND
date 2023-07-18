package com.flatdevs.virtualassistant.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicPlan {
    private String name;
    private BigDecimal creditHours;
    private BigDecimal type;
}
