# Terminal-Assessment-Final-Code-Submission-Group-9
MotorPH Payroll System

## Dependencies

This project uses the `opencsv`, `commons-lang3`, and `jcalendar` libraries for various functionalities.

### Downloading JAR Files

You can download the JAR files from their respective repositories:

- For `opencsv`, download it from [OpenCSV Maven Repository](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.9).
- For `commons-lang3`, download it from [Commons Lang Maven Repository](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.14.0).
- For `jcalendar`, download it from [JCalendar Maven Repository](https://mvnrepository.com/artifact/com.toedter/jcalendar/1.4).

### Adding Dependencies Using Maven

Alternatively, you can include these dependencies in your Maven `pom.xml` file. Add the following to the `<dependencies>` section:

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

