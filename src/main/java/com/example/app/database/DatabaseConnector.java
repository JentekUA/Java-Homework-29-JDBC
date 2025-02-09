package com.example.app.database;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static final String RESOURCES_FOLDER = "src/main/resources";
    private static final String CONNECTION_STRING = "jdbc:sqlite:%s/company.db".formatted(RESOURCES_FOLDER);

    @SneakyThrows
    private DatabaseConnector() {
        var resources = Path.of(RESOURCES_FOLDER);
        if (!Files.exists(resources))
            Files.createDirectory(resources);

    }

    public static DatabaseConnector getInstance() {
        if (instance == null) instance = new DatabaseConnector();
        return instance;
    }

    public <T> T executeStaement(Function<Statement, T> statementTFunction) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
            return statementTFunction.apply(connection.createStatement());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T executePreparedStaement(String sql, Function<PreparedStatement, T> statementConsumer) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
            return statementConsumer.apply(connection.prepareStatement(sql));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
