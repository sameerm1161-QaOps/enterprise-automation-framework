FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Cache dependencies first (layer optimization)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source
COPY src ./src
COPY resources ./resources
COPY testng-suites ./testng-suites

# Install Playwright browsers
RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI \
    -D exec.args="install --with-deps" || true

CMD ["mvn", "clean", "test"]