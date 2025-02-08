package com.example.app.database.dao;

import com.example.app.database.DatabaseConnector;
import com.example.app.database.entity.EmployeeEntity;
import com.example.app.database.mapper.EmployeeEntityRowMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static EmployeeDAO instance;
    private final DatabaseConnector databaseConnector = DatabaseConnector.getInstance();

    private EmployeeDAO() {
        databaseConnector.executeStaement(statement -> {
            var sql = """
                    CREATE TABLE IF NOT EXISTS employees (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        age INTEGER NOT NULL,
                        position TEXT,
                        salary REAL
                    );""";
            try {
                return statement.executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static EmployeeDAO getInstance() {
        if (instance == null) instance = new EmployeeDAO();
        return instance;
    }

    public void createEmployee(EmployeeEntity employeeEntity) {
        var sql = """
                INSERT INTO employees (name, age, position, salary) VALUES (?, ?, ?, ?);
                """;
        databaseConnector.executePreparedStaement(sql, statement -> {
            try {
                statement.setString(1, employeeEntity.getName());
                statement.setInt(2, employeeEntity.getAge());
                statement.setString(3, employeeEntity.getPosition());
                statement.setBigDecimal(4, employeeEntity.getSalary());
                return statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateEmployee(int id, EmployeeEntity employeeEntity) {
        var sql = """
                UPDATE employees 
                SET name = ?, age = ?, position = ?, salary = ? 
                WHERE id = ?;
                """;
        databaseConnector.executePreparedStaement(sql, statement -> {
            try {
                statement.setString(1, employeeEntity.getName());
                statement.setInt(2, employeeEntity.getAge());
                statement.setString(3, employeeEntity.getPosition());
                statement.setBigDecimal(4, employeeEntity.getSalary());
                statement.setInt(5, id);
                return statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteEmployee(int id) {
        var sql = """
                DELETE FROM employees
                WHERE id = ?;
                """;
        databaseConnector.executePreparedStaement(sql, statement -> {
            try {
                statement.setInt(1, id);
                return statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public EmployeeEntity getById(int id) {
        var sql = """
                SELECT * FROM employees
                WHERE id = ?;
                """;
        return databaseConnector.executePreparedStaement(sql, statement -> {
            try {
                statement.setInt(1, id);
                try (var resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return new EmployeeEntityRowMapper().toEmployeeEntity(resultSet);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<EmployeeEntity> getAll() {

        return databaseConnector.executeStaement(statement -> {
            var sql = """
                SELECT * FROM employees;
                """;
            var result = new ArrayList<EmployeeEntity>();
            var mapper = new EmployeeEntityRowMapper();

            try (var resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()){
                    result.add(mapper.toEmployeeEntity(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return result;
        });
    }
}
