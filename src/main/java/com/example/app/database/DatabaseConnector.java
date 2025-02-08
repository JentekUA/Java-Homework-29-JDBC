package com.example.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private static final String connectionString = "jdbc:sqlite:src/main/resources/company.db";

    private DatabaseConnector() {
        //Private constructor for singleton
    }

    public static DatabaseConnector getInstance() {
        if(instance == null) instance = new DatabaseConnector();
        return instance;
    }

    public <T> T executeStaement(Function<Statement, T> statementTFunction) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            return statementTFunction.apply(connection.createStatement());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T executePreparedStaement(String sql, Function<PreparedStatement, T> statementConsumer) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            return statementConsumer.apply(connection.prepareStatement(sql));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
