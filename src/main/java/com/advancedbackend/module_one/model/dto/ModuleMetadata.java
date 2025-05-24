package com.advancedbackend.module_one.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModuleMetadata {
    private final String moduleName;
    private final String dbSchemaName;
}
