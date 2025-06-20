package com.example.NotifyTrafficIntegration.mapper;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;
import com.example.NotifyTrafficIntegration.entity.UserSettingsEntity;
import com.example.NotifyTrafficIntegration.rest.request.NotifyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.*;

@Mapper(componentModel = "spring")
public interface NotifyMapper {


    @Mapping(target = "homeLat", source = "home", qualifiedByName = "extractLatitude")
    @Mapping(target = "homeLon", source = "home", qualifiedByName = "extractLongitude")
    @Mapping(target = "workLat", source = "work", qualifiedByName = "extractLatitude")
    @Mapping(target = "workLon", source = "work", qualifiedByName = "extractLongitude")
    @Mapping(target = "telegramUserId", source = "request.telegramUserId")
    @Mapping(target = "timezone", source = "request.timeZone")
    @Mapping(target = "homeAddress", source = "request.homeAddress")
    @Mapping(target = "workAddress", source = "request.workAddress")
    @Mapping(target = "arrivalTime", source = "request", qualifiedByName = "toUtcArrivalTime")
    @Mapping(target = "id", ignore = true)
    UserSettingsEntity mapGeocodingResponseDtoToUserSettingsEntity(
            GeocodingResponseDto home,
            GeocodingResponseDto work,
            NotifyRequest request
    );

    @Mapping(target = "origins", expression = "java(user.getHomeLat() + \",\" + user.getHomeLon())")
    @Mapping(target = "destinations", expression = "java(user.getWorkLat() + \",\" + user.getWorkLon())")
    DistanceMatrixRequestDto mapUserSettingsEntityToDistanceMatrixRequestDto(UserSettingsEntity user);

    @Named("extractLatitude")
    default String extractLatitude(GeocodingResponseDto dto) {
        return dto.getResults().get(0).getNavigationPoints().get(0).getLocation().getLatitude();
    }

    @Named("extractLongitude")
    default String extractLongitude(GeocodingResponseDto dto) {
        return dto.getResults().get(0).getNavigationPoints().get(0).getLocation().getLongitude();
    }

    @Named("toUtcArrivalTime")
    default LocalTime  toUtcArrivalTime(NotifyRequest request) {
        LocalTime localArrival = LocalTime.parse(request.getArrivalTime());
        ZoneId userZone = ZoneId.of(request.getTimeZone());
        ZonedDateTime zoned = ZonedDateTime.of(LocalDate.now(userZone), localArrival, userZone);
        return zoned.withZoneSameInstant(ZoneOffset.UTC).toLocalTime();
    }
}


