package com.flatdevs.virtualassistant.student.repository;

import com.flatdevs.virtualassistant.student.entity.AcademicPlanCoursesEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface AcademicPlanCoursesRepository extends JpaRepository<AcademicPlanCoursesEntity, Long > {
}