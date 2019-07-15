package edu.knoldus.employee.impl;

import akka.Done;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import edu.knoldus.employee.api.EmployeeDetails;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(onConstructor = @_(@Inject))
public class EmployeeRepository {
    
    static String FETCH_EMPLOYEE_BY_ID = "select * from employee_details where id = ?";
    static String INSERT_EMPLOYEE = "insert into employee_details (id,name,salary,age,mobile_number,designation) VALUES(?,?,?,?,?,?)";
    static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS employee_details ( id varchar , name varchar," +
            " salary varchar, age varchar, mobile_number varchar, designation varchar, PRIMARY KEY(id) )";
    
    CassandraSession session;
    
    public CompletionStage<Optional<EmployeeDetails>> fetchById(String id) {
        
        return session.selectOne(FETCH_EMPLOYEE_BY_ID, id)
                .thenApply(row -> row.map(this::mapRowToEmployee));
    }
    
    public CompletionStage<Done> createTable() {
        
        return session.executeCreateTable(CREATE_TABLE);
    }
    
    public CompletionStage<Done> insertEmployeeDetails(EmployeeDetails employeeDetails) {
        
        return session.prepare(INSERT_EMPLOYEE)
                .thenApply(PreparedStatement::bind)
                .thenApply(bs -> bs
                        .setString("id", employeeDetails.getId())
                        .setString("name", employeeDetails.getName())
                        .setString("salary", employeeDetails.getSalary())
                        .setString("age", employeeDetails.getAge())
                        .setString("mobile_number", employeeDetails.getMobile())
                        .setString("designation", employeeDetails.getDesignation())
                )
                .thenComposeAsync(session::executeWrite);
    }
    
    private EmployeeDetails mapRowToEmployee(Row row) {
        
        return EmployeeDetails.builder()
                .age(row.getString("age"))
                .name(row.getString("name"))
                .designation(row.getString("designation"))
                .id(row.getString("id"))
                .mobile(row.getString("mobile_number"))
                .salary(row.getString("salary"))
                .build();
    }
}
