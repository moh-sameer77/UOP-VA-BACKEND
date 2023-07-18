package com.flatdevs.virtualassistant.student.repository;

import com.flatdevs.virtualassistant.student.entity.NotificationEntity;
import com.flatdevs.virtualassistant.student.entity.ReminderEntity;
import com.flatdevs.virtualassistant.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
