package com.flatdevs.virtualassistant.student.repository;

import com.flatdevs.virtualassistant.student.entity.CourseEntity;

import com.flatdevs.virtualassistant.student.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CoursesRepository extends JpaRepository<CourseEntity, Long > {
  List<CourseEntity> findByType(TypeEntity type);
//    Page<CourseEntity> findByType(String type,Pageable pageable);
//    Page<CourseEntity> findAllById(Long courseId, Pageable pageable);
//    int countByName(String name);
//    int countById(Long courseId);
//




}
