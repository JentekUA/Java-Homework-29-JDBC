package com.example.app.database.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity {
    Integer id;
    String name;
    Integer age;
    String position;
    BigDecimal salary;
}
