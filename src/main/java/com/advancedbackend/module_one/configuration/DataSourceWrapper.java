package com.advancedbackend.module_one.configuration;

import com.advancedbackend.module_one.model.dto.ModuleMetadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
public class DataSourceWrapper extends DelegatingDataSource {

    private final ModuleMetadata moduleMetadata;

    public DataSourceWrapper(DataSource targetDataSource, ModuleMetadata moduleMetadata) {
        super(targetDataSource);
        this.moduleMetadata = moduleMetadata;
    }

    private Connection prepareConnectionSafe(Connection connection) throws SQLException {
        if (connection == null) {
            return null;
        }

        var schemaName = moduleMetadata.getDbSchemaName();
        try(var statement = connection.prepareStatement(String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName))) {
            log.trace("Creating schema: {}", schemaName);
            statement.execute();
        }

        schemaName = moduleMetadata.getDbSchemaName() + ", public";
        try(var statement = connection.prepareStatement(String.format("SET search_path = %s;", schemaName))) {
            log.trace("Changing search_path to {}", schemaName);
            statement.execute();
        }

        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return prepareConnectionSafe(obtainTargetDataSource().getConnection(username, password));
    }

    @Override
    public Connection getConnection() throws SQLException {
        return prepareConnectionSafe(obtainTargetDataSource().getConnection());
    }
}
