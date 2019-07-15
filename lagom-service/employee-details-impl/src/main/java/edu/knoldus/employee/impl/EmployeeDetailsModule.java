package edu.knoldus.employee.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import edu.knoldus.employee.api.EmployeeDetailsService;
import edu.knoldus.employee.impl.consumer.EmployeeConsumer;
import edu.knoldus.employee.impl.consumer.EmployeeConsumerService;

public class EmployeeDetailsModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(EmployeeDetailsService.class, EmployeeDetailsServiceImpl.class);
        bind(EmployeeConsumer.class).asEagerSingleton();
        bindClient(EmployeeConsumerService.class);
    }
}
