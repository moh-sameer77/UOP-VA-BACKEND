package com.flatdevs.virtualassistant.user.repository;

import com.flatdevs.virtualassistant.user.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
}
