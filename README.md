# Enterprise Automation Framework

![CI](https://github.com/sameerm1161-QaOps/enterprise-automation-framework/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9-red)

A production-level hybrid automation framework for UI and API testing, built with Java, Selenium, Playwright, and Rest Assured.

**Live Report:** https://sameerm1161-qaops.github.io/enterprise-automation-framework

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| UI Automation | Selenium 4 + Playwright |
| API Testing | Rest Assured 5 |
| Test Runner | TestNG 7.9 |
| Build Tool | Maven |
| Reporting | Allure + Extent Reports |
| CI/CD | GitHub Actions |
| Containerization | Docker + Selenium Grid |
| Self-Healing | Healenium |
| Visual AI | Applitools Eyes |
| Performance | JMeter |
| Logging | Log4j2 + SLF4J |

---

## Project Structure

    src/main/java/com/framework/
        core/
            base/           BaseTest — lifecycle management
            config/         ConfigManager — environment config
            driver/         DriverFactory, DriverManager — ThreadLocal
        ui/
            base/           BasePage — wrapped Selenium actions
            pages/          Page Object Model classes
            playwright/     Playwright driver support
        api/
            base/           BaseApiClient — Rest Assured spec
            clients/        API client classes
        utils/              Screenshot, Wait, DB, Visual utilities
        listeners/          TestNG listeners, RetryAnalyzer

    src/test/java/com/tests/
        ui/                 SauceDemo UI tests
        api/                JSONPlaceholder API tests

    testng-suites/          smoke.xml, regression.xml, parallel.xml
    docker/                 Docker Compose + Selenium Grid
    performance-tests/      JMeter test plans
    .github/workflows/      GitHub Actions CI/CD


    ---

## Test Results

| Suite | Tests | Passed | Failed | Duration |
|---|---|---|---|---|
| Smoke | 9 | 9 | 0 | ~30s |
| UI (SauceDemo) | 3 | 3 | 0 | ~20s |
| API (JSONPlaceholder) | 6 | 6 | 0 | ~10s |

---

## Quick Start

Prerequisites: Java 17, Maven 3.9+, Chrome

```bash
# Clone the repository
git clone https://github.com/sameerm1161-QaOps/enterprise-automation-framework.git
cd enterprise-automation-framework

# Run smoke suite
mvn clean test -Dsuite.file=testng-suites/smoke.xml

# Run with specific browser
mvn clean test -Dsuite.file=testng-suites/smoke.xml -Dbrowser=firefox

# Run headless
mvn clean test -Dsuite.file=testng-suites/smoke.xml -Dheadless=true

# Run against specific environment
mvn clean test -Denv=staging
```

## Docker Execution

```bash
# Start Selenium Grid
docker compose -f docker/docker-compose.yml up -d

# Run tests on Grid
mvn clean test -Dselenium.grid.url=http://localhost:4444/wd/hub -Dheadless=true
```

## Allure Report

```bash
mvn allure:serve
```

---

## Design Patterns

| Pattern | Implementation |
|---|---|
| Page Object Model | All UI page classes |
| Factory | DriverFactory — multi-browser support |
| Singleton | ConfigManager — single config instance |
| ThreadLocal | DriverManager — parallel execution safety |
| Builder | RequestSpecBuilder — API request specs |
| Observer | TestListener — test lifecycle hooks |

---

## Key Capabilities

**Parallel Execution** — TestNG parallel methods with ThreadLocal WebDriver ensures thread safety across concurrent test runs.

**Retry Mechanism** — RetryAnalyzer automatically retries failed tests. Configurable via `retry.max` in config.properties.

**Self-Healing** — Healenium detects broken locators and auto-heals them, reducing maintenance overhead.

**Multi-Environment** — ConfigManager loads environment-specific properties with JVM argument override support.

**Visual Testing** — Applitools Eyes captures and compares visual snapshots using AI-powered image comparison.

---

## Configuration

```properties
browser=chrome
headless=false
base.url=https://www.saucedemo.com
api.base.url=https://jsonplaceholder.typicode.com
retry.max=2
healing.enabled=false
applitools.enabled=false
selenium.grid.url=
```

---

## CI/CD

Every push to `main` triggers the pipeline:
1. Checkout and setup Java 17
2. Run full smoke suite on headless Chrome
3. Generate Allure report
4. Deploy report to GitHub Pages

---

## Author

Fazil — Senior SDET
LinkedIn: https://linkedin.com/in/your-profile
