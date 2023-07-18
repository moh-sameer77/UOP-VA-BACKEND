package com.flatdevs.virtualassistant.student.repository;

import com.flatdevs.virtualassistant.student.entity.CourseEntity;
import com.flatdevs.virtualassistant.student.entity.PassedCourseEntity;
import com.flatdevs.virtualassistant.student.entity.TypeEntity;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassedCourseRepository extends JpaRepository<PassedCourseEntity, Long> {
    List<PassedCourseEntity> findByCourse(CourseEntity courses);
    List<PassedCourseEntity> findByEnrolledStudent(EnrolledStudentEntity enrolledStudentEntity);
}
