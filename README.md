# Gemtastic Attendance System
A simple attendance management system produced in JavaEE for my JavaEE class in school.

## Technical specification

- JavaEE: Web + EJB
- JSF
- PrimeFaces
- PostgreSQL
- JPA

## Setup

1. Clone this repository

    git clone https://github.com/Gemtastic/JavaEEProject.git

2. Install postgres.
3. Run the SQL code found in the [db schema](https://github.com/Gemtastic/JavaEEProject/blob/master/AttendanceSystem-ejb/src/main/resources/other/dbschema.txt) in the postgres terminal.
4. Set up JMS resource:

    **Connection factory name:** jms/myconnectionfactory
    
    **Resource type:** javax.jms.QueueConnectionFactory
    
    **Destination resource name:** jms/myQueue
    
    **Physical Destination Name:** attendance
    
    **Resource type:** Queue

5. Setup jdbc resources:

    **Resource name:** jdbc/attendance
    
    **Connection pool name:** post-gre-sql_attendance_appPool
    
    **Resource type:** Data source
    
    **Data source class name:** org.postgresql.ds.PGSimpleDataSource
    
6. Clean and build the maven project
7. Clean and build the `.RAR`
8. Clean and build the `.WAR`
9. Clean and build the `.EAR`
10. Run the `.EAR`
