package com.example.app.database.mapper;

import com.example.app.database.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@RequiredArgsConstructor
public class EmployeeEntityRowMapper {

    @SneakyThrows
    public EmployeeEntity toEmployeeEntity(ResultSet resultSet) {
        return EmployeeEntity
                .builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .age(resultSet.getInt("age"))
                .position(resultSet.getString("position"))
                .salary(resultSet.getBigDecimal("salary"))
                .build();
    }
}
