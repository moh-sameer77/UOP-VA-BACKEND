package com.flatdevs.virtualassistant.student.repository;

import com.flatdevs.virtualassistant.student.entity.AcademicPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicPlanRepository extends JpaRepository<AcademicPlanEntity, Long> {
    List<AcademicPlanEntity> findByName(String name);
    AcademicPlanEntity findUserById(Long id);
    int countByName(String name);
    int countById(Long courseId);


}
