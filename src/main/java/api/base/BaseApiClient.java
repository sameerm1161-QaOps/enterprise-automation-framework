package api.base;

import core.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base API Client — all API clients inherit this.
 * Handles: base URL, auth, headers, logging, Allure integration.
 */
public abstract class BaseApiClient {

    protected static final Logger log = LoggerFactory.getLogger(BaseApiClient.class);
    protected RequestSpecification requestSpec;
    protected final ConfigManager config = ConfigManager.getInstance();

    public BaseApiClient() {
        buildRequestSpec();
    }

    private void buildRequestSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(config.get("api.base.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())      // Allure logging
                .addFilter(new RequestLoggingFilter())   // Console logging
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    protected Response get(String endpoint) {
        return RestAssured.given()
                .spec(requestSpec)
                .get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .post(endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .put(endpoint);
    }

    protected Response delete(String endpoint) {
        return RestAssured.given()
                .spec(requestSpec)
                .delete(endpoint);
    }

    protected Response get(String endpoint, String token) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .get(endpoint);
    }
}