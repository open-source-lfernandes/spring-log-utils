# Spring Log Utils

`spring-log-utils` is a library designed to enhance logging capabilities in Spring-based applications. It provides features for logging HTTP methods, specific endpoints, and methods, as well as masking sensitive data in logs. The library leverages Spring Boot's autoconfiguration mechanism to simplify integration and usage.

This library simplifies logging in Spring applications, making it easier to monitor and debug while ensuring sensitive data is protected.

## Features

1. **Global HTTP Methods Logging**: 
   - Automatically logs HTTP method executions based on configuration properties.

2. **`@HttpMethodLogExecution` Annotation**:
   - Enables logging for specific HTTP endpoints.

3. **`@LogExecution` Annotation**:
   - Logs the execution of specific methods.

4. **`@MaskSensitiveData` Annotation**:
   - Masks sensitive fields in logs to protect sensitive information.

## Integration

### Maven
To use this library in a Maven project, add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>io.github.ice-lfernandes</groupId>
    <artifactId>spring-log-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
To use this library in a Gradle project, add the following dependency to your `build.gradle`:
```groovy
implementation 'io.github.ice-lfernandes:spring-log-utils:1.0.0'
```

## Usage

### Global HTTP Methods Logging
Configure logging for HTTP methods globally using Spring Boot configuration properties. Example:
```yaml
spring:
  log-utils:
    http-methods:
      enabled: true
```

### `@HttpMethodLogExecution` Annotation
Use this annotation to log specific HTTP endpoints:
```java
@RestController
@RequestMapping("/greetings")
@RequiredArgsConstructor
public class GreetingController {

   private final GreetingService service;

   @HttpMethodLogExecution
   @GetMapping
   public ResponseEntity<List<User>> getUsers() {
      return ResponseEntity.ok(service.getUser());
   }

}
```
#### Output Log Example
```plaintext
2025-05-13 15:59:15 app=example-secret-app [http-nio-8080-exec-3] [:] io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods INFO  M=logInterceptJoinPoint, stage=init, method=getUsers, class=GreetingController, parameters=[]
2025-05-13 15:59:15 app=example-secret-app [http-nio-8080-exec-3] [:] io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods INFO  M=logInterceptJoinPoint, stage=finish, method=getUsers, class=GreetingController, parameters=[], result=<200 OK OK,[User{email=******des***.com, name=**s****es, address=*a****o*45, age=**, birthDate=***5-13, phone=***9**9, document=********911}],[]>, time-execution=5ms
```

### `@LogExecution` Annotation
Use this annotation to log the execution of specific methods:
```java
@Service
public class GreetingService {

   @LogExecution
   public User getUser(Long id) {
      return User.builder()
              .name("Lucas Fernandes")
              .email("lucas.fernandes@gmail.com")
              .document("12345678911")
              .birthDate(LocalDate.now())
              .age(31)
              .phone("11 9999-9999")
              .address("Rua Flamengo 745")
              .build();
   }
}

```
#### Output Log Example
```plaintext
2025-05-13 16:22:08 app=example-secret-app [http-nio-8080-exec-1] [:] io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods INFO  M=logInterceptJoinPoint, stage=init, method=getUser, class=GreetingService, parameters=[1]
2025-05-13 16:22:08 app=example-secret-app [http-nio-8080-exec-1] [:] io.github.ice_lfernandes.spring.log.utils.commons.LoggingCommonsMethods INFO  M=logInterceptJoinPoint, stage=finish, method=getUser, class=GreetingService, parameters=[1], result=User{email=******des***.com, name=**s****es, address=*a****o*45, age=**, birthDate=***5-13, phone=***9**9, document=********911}, time-execution=2ms
```

### `@MaskSensitiveData` Annotation
Use this annotation to mask sensitive fields in logs:
```java
public record User(
        @MaskSensitiveData(maskedType = MaskedType.EMAIL) String email,
        @MaskSensitiveData(maskedType = MaskedType.NAME) String name,
        @MaskSensitiveData(maskedType = MaskedType.ADDRESS) String address,
        @MaskSensitiveData(maskedType = MaskedType.NUMBER) Integer age,
        @MaskSensitiveData(maskedType = MaskedType.DATE) LocalDate birthDate,
        @MaskSensitiveData(maskedType = MaskedType.TELEPHONE) String phone,
        @MaskSensitiveData(maskedType = MaskedType.DOCUMENT) String document) implements LogMask {

    @Override
    public String toString() {
        return mask(this);
    }
}
```

### Expected Log Outputs

1. `ALL`: Masks all characters.
2. `EMAIL`: Masks email addresses except the last 4 characters and domain.
3. `DOCUMENT`: Masks all but the last 3 characters.
4. `NAME`: Masks all characters except the first and last two.
5. `DATE`: Masks all characters except the last 3, excluding separators.
6. `ADDRESS`: Masks all characters except the last 3, excluding commas and spaces.
7. `ZIP_CODE`: Masks all characters except the last 3, excluding hyphens.
8. `NUMBER`: Masks all numeric characters.
9. `TELEPHONE`: Masks all characters except the last 2, excluding hyphens.
