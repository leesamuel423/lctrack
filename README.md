# lctrack

This is a Spring Boot application with a React frontend for tracking LeetCode problems.

## Prerequisites

- Java JDK 17 or later
- Gradle
- Node.js 20.x
- Yarn 1.22.x

## Setup

1. Clone the repository
   ```bash
   git clone https://github.com/leesamuel423/lctrack.git
   cd lctrack
   ```

2. Create a `.env` file in the project root and add necessary environment variables. You can utilize `example.env` as a template.

## Building and Running the Application

### Full Stack Application

To build and run both the backend and frontend together:

1. Build the project
   ```bash
   ./gradlew build
   ```

2. Run the application
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=dev' # Run in development mode
   ./gradlew bootRun --args='--spring.profiles.active=prod' # Run in production mode
   ```

   The application will be available at `http://localhost:8080`

### Backend Only

If you want to run only the Spring Boot backend:

1. Build and run the backend
   ```bash
   ./gradlew bootRun
   ```

### Frontend Development

For frontend development with hot-reloading:

1. Navigate to the frontend directory
   ```bash
   cd frontend
   ```

2. Install dependencies
   ```bash
   yarn install
   ```

3. Start the development server
   ```bash
   yarn dev
   ```

   The development server will start, typically at `http://localhost:5173`

## Testing

To run tests:

```bash
./gradlew test
```

## Additional Information

- The frontend is built using Vite and React
- The backend uses Spring Boot with MongoDB
- The Gradle build automatically handles building the frontend and integrating it with the backend

