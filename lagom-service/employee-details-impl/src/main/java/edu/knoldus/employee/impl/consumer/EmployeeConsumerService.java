package edu.knoldus.employee.impl.consumer;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.typesafe.config.ConfigFactory;
import edu.knoldus.employee.api.EmployeeDetails;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.topic;

public interface EmployeeConsumerService extends Service {
    
    String TOPIC_NAME = ConfigFactory.load().getString("kafka.employee-topic.name");
    
    Topic<EmployeeDetails> employeeTopic();
    
    @Override
    default Descriptor descriptor() {
        
        return named("employee-details").withTopics(
                topic(TOPIC_NAME, this::employeeTopic)
        ).withAutoAcl(true);
        
    }
}
