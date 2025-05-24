package com.advancedbackend.module_one.configuration;

import com.advancedbackend.module_one.model.dto.ModuleMetadata;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ModuleMetadata moduleMetadata(@Value("${spring.application.name}") String applicationName) {
        var schemaName = StringUtils.isNotBlank(applicationName)
                ?  applicationName.toLowerCase().replace('-', '_')
                : "";

        return new ModuleMetadata(applicationName, schemaName);
    }

    @Bean
    public BeanPostProcessor dataSourceSchemaAdvisorBeanPostProcessor(@Lazy ModuleMetadata moduleMetadata) {
        return new DataSourceSchemaAdvisorBeanPostProcessor(moduleMetadata);
    }
}
