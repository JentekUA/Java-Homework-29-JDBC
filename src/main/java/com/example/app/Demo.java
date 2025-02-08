package com.example.app;

import com.example.app.database.dao.EmployeeDAO;
import com.example.app.database.entity.EmployeeEntity;

import java.math.BigDecimal;

public class Demo {
    public void demonstrate() {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

        // Create employees
        employeeDAO.createEmployee(EmployeeEntity
                .builder()
                .name("Alice")
                .age(30)
                .position("Developer")
                .salary(BigDecimal.valueOf(75000))
                .build());
        employeeDAO.createEmployee(
                EmployeeEntity
                        .builder()
                        .name("Bob")
                        .age(25)
                        .position("Designer")
                        .salary(BigDecimal.valueOf(60000))
                        .build());

        // Get all employees
        System.out.printf("All employees: %s\n", employeeDAO.getAll());

        // Update employees
        employeeDAO.updateEmployee(1, EmployeeEntity
                .builder()
                .name("Alice Johnson")
                .age(31)
                .position("Senior Developer")
                .salary(BigDecimal.valueOf(80000))
                .build());

        // Get all employees after update
        System.out.printf("Employees after update: %s\n", employeeDAO.getAll());

        // Delete employee
        employeeDAO.deleteEmployee(2);

        // Get all employees after deletion
        System.out.printf("Employees after deletion: %s\n", employeeDAO.getAll());
    }
}
