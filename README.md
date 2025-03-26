# MotorPH Payroll System Repository - Group 9

Welcome to the MotorPH Payroll System repository! This repository houses the source code for our Java-based payroll management application. For a comprehensive understanding of the system, including detailed setup instructions, step-by-step usage guides for all user roles (Employee, HR, IT, Finance), technical specifications, and future improvement plans, please consult our dedicated documentation website. Explore the full documentation here: [MotorPH Documentation](https://therese.craft.me/MotorPH-G9-Official-Docs).

## Project Dependencies

This project relies on the following external libraries:

- **OpenCSV**: For handling CSV file operations.
- **Commons Lang**: Utility functions to enhance the core Java classes.
- **JCalendar**: For date and calendar functionalities.

### Obtaining JAR Files

You can download the required JAR files from their respective Maven repositories:

- [OpenCSV Maven Repository](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.9)
- [Commons Lang Maven Repository](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.14.0)
- [JCalendar Maven Repository](https://mvnrepository.com/artifact/com.toedter/jcalendar/1.4)

### Adding Dependencies to Maven

Alternatively, you can add these libraries directly to your Maven `pom.xml` file by including the following dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.9</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.14.0</version>
    </dependency>
    <dependency>
        <groupId>com.toedter</groupId>
        <artifactId>jcalendar</artifactId>
        <version>1.4</version>
    </dependency>
</dependencies>
