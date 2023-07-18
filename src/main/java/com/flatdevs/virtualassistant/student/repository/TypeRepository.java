package com.flatdevs.virtualassistant.student.repository;
import com.flatdevs.virtualassistant.student.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long > {
}