# lctrack

## Backend
This is a Spring Boot application for tracking LC problems
- Java JDK 11 or later
- Gradle

1. Clone the repository
```bash
git clone https://github.com/leesamuel423/lctrack.git
cd lcTrack
```

2. Build the project
```bash
./gradlew build
```

3. Run application
```bash
./gradlew bootRun --args='--spring.profiles.active=dev' # Run in development mode
./gradlew bootRun --args='--spring.profiles.active=prod' # Run in production mode
```

4. Run Tests
```bash
./gradlew test
```
