package com.example.NotifyTrafficIntegration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserInformation")
public class UserSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramUserId;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(name = "work_address")
    private String workAddress;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "home_lat")
    private String homeLat;

    @Column(name = "home_lon")
    private String homeLon;

    @Column(name = "work_lat")
    private String workLat;

    @Column(name = "work_lon")
    private String workLon;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "last_notify_time")
    private ZonedDateTime lastNotified;
}
