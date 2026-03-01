package com.github.scripting.programming.language.interview_sim_backend.mapper;

import org.mapstruct.Mapper;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DateMapper {
    default OffsetDateTime mapZonedDateTimeToOffsetDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toOffsetDateTime();
    }
}
