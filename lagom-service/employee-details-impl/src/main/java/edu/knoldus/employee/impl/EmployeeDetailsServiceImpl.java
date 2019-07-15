package edu.knoldus.employee.impl;

import akka.NotUsed;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import edu.knoldus.employee.api.EmployeeDetails;
import edu.knoldus.employee.api.EmployeeDetailsService;
import edu.knoldus.employee.api.ServiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(onConstructor = @_(@Inject))
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {
    
    EmployeeRepository employeeRepository;
    
    @Override
    public ServiceCall<NotUsed, EmployeeDetails> getEmployee(String id) {
        
        return req -> employeeRepository.fetchById(id)
                .thenApply(employee -> employee.orElseGet(() -> {
                    throw new NotFound("User with given id was not found");
                }));
    }
    
    @Override
    public ServiceCall<NotUsed, ServiceStatus> health() {
        
        return req -> CompletableFuture.completedFuture(new ServiceStatus("Service is up"));
        
    }
}
