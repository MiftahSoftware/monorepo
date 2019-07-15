package edu.knoldus.employee.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface EmployeeDetailsService extends Service {
    
    ServiceCall<NotUsed, EmployeeDetails> getEmployee(String id);
    
    ServiceCall<NotUsed, ServiceStatus> health();
    
    @Override
    default Descriptor descriptor() {
        
        return named("employee-details").withCalls(
                pathCall("/employee/:id", this::getEmployee),
                pathCall("/health", this::health)
        ).withAutoAcl(true);
        
    }
}
