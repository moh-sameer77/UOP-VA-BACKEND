package com.flatdevs.virtualassistant.user.repository;

import com.flatdevs.virtualassistant.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flatdevs.virtualassistant.user.entity.EnrolledStudentEntity;

public interface EnrolledStudentRepository extends JpaRepository<EnrolledStudentEntity, Long> {

     EnrolledStudentEntity findByUser(UserEntity user);
}