package edu.knoldus.employee.impl.consumer;

import akka.Done;
import akka.stream.javadsl.Flow;
import com.typesafe.config.Config;
import edu.knoldus.employee.api.EmployeeDetails;
import edu.knoldus.employee.impl.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.String.format;

@Singleton
@Slf4j
public class EmployeeConsumer {
    
    private final EmployeeRepository repository;
    
    @Inject
    public EmployeeConsumer(EmployeeConsumerService employeeConsumerService,
                            EmployeeRepository repository,
                            Config config) {
        
        this.repository = repository;
        
        repository.createTable().toCompletableFuture().join();
        log.info("employee_details table created in cassandra.");
        
        String groupId = config.getString("kafka.employee-topic.group");
        
        log.info("Subscribing EmployeeConsumer consumer to {} topic with group ID {}",
                EmployeeConsumerService.TOPIC_NAME, groupId);
        
        employeeConsumerService.employeeTopic()
                .subscribe()
                .withGroupId(groupId)
                .atLeastOnce(Flow.fromFunction(this::insertEmployee));
    }
    
    private Done insertEmployee(EmployeeDetails employeeDetails) {
        
        log.info("Received the following employee details : {}", employeeDetails);
        
        repository.insertEmployeeDetails(employeeDetails)
                .whenComplete((done, throwable) -> {
                    
                    if (throwable != null) {
                        log.error(format("An error occurred while inserting employee details with ID : %s. {}", employeeDetails.getId()), throwable);
                        return;
                    }
                    
                    log.info("Employee details with ID : {} has been successfully inserted.", employeeDetails.getId());
                });
        
        return Done.getInstance();
    }
}
