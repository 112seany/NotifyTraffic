package com.example.NotifyTrafficIntegration.repository;

import com.example.NotifyTrafficIntegration.entity.UserSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<UserSettingsEntity, Long> {

    @Query("SELECT u FROM UserSettingsEntity u " +
            "WHERE u.arrivalTime BETWEEN :minTime AND :maxTime " +
            "AND (u.lastNotified IS NULL OR CAST(u.lastNotified AS date) < CURRENT_DATE)")
    List<UserSettingsEntity> findUsersToNotify(@Param("minTime") LocalTime notificationWindowStart,
                                               @Param("maxTime") LocalTime notificationWindowEnd);
}
