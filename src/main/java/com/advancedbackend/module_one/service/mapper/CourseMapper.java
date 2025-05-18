package com.advancedbackend.module_one.service.mapper;

import com.advancedbackend.module_one.domain.dto.Metadata;
import com.advancedbackend.module_one.model.entity.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "metadata", expression = "java(toCourseMetadata(entity))")
    com.advancedbackend.module_one.domain.dto.Course toCourseDto(Course entity);

    @InheritInverseConfiguration
    Course toCourseEntity(com.advancedbackend.module_one.domain.dto.Course dto);

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "updatedDate", source = "updatedDate")
    Metadata toCourseMetadata(Course entity);
}
