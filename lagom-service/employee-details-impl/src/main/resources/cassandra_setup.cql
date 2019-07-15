CREATE KEYSPACE employee
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };

CREATE TABLE employee.employee_details (
    id varchar ,
    name varchar,
    salary varchar,
    age varchar,
    mobile_number varchar,
    designation varchar,
    PRIMARY KEY(id)
);

insert into employee_details (id,name,salary,age,mobile_number,designation) VALUES('1','Leo','120000','25','998139828','Software Consultant');
insert into employee_details (id,name,salary,age,mobile_number,designation) VALUES('2','Joe','250000','35','771291731','Software Architect');
