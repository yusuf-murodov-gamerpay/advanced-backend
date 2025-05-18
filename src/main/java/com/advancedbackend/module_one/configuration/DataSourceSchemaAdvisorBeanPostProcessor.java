package com.advancedbackend.module_one.configuration;

import com.advancedbackend.module_one.model.dto.ModuleMetadata;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;

public class DataSourceSchemaAdvisorBeanPostProcessor implements BeanPostProcessor {
    public static final String DATASOURCE_BEAN_NAME = "dataSource";

    private final ModuleMetadata moduleMetadata;

    public DataSourceSchemaAdvisorBeanPostProcessor(ModuleMetadata moduleMetadata) {
        this.moduleMetadata = moduleMetadata;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (DATASOURCE_BEAN_NAME.equals(beanName)) {
            DataSource dataSource;
            if (bean instanceof FactoryBean) {
                try {
                    if (((FactoryBean) bean).getObject() instanceof DataSource) {
                        dataSource = (DataSource) ((FactoryBean) bean).getObject();
                    } else {
                        throw unknownDatasourceException();
                    }
                } catch (Exception e) {
                    throw unknownDatasourceException();
                }
            } else {
                dataSource = (DataSource) bean;
            }
            return new DataSourceWrapper(dataSource, moduleMetadata);
        }
        return bean;
    }

    private IllegalStateException unknownDatasourceException() {
        return new IllegalStateException("Bean with dataSource name should be instance of Datsouce or FactoryBean");
    }
}
