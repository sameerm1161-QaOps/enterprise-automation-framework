package tests.api;

import api.base.BaseApiClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("JSONPlaceholder API")
@Feature("User API")
public class UserApiTest extends BaseApiClient {

    @Test(priority = 1, description = "Get all users - verify 200")
    @Severity(SeverityLevel.BLOCKER)
    public void testGetAllUsers() {
        Response response = get("/users");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertFalse(response.jsonPath().getList("$").isEmpty());
    }

    @Test(priority = 2, description = "Get single user - verify 200")
    @Severity(SeverityLevel.NORMAL)
    public void testGetSingleUser() {
        Response response = get("/users/1");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
    }

    @Test(priority = 3, description = "Create user - verify 201")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        String body = """
            {
                "name": "Fazil",
                "username": "fazil_sdet",
                "email": "fazil@test.com"
            }
            """;
        Response response = post("/users", body);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "Fazil");
    }

    @Test(priority = 4, description = "Update user - verify 200")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUser() {
        String body = """
            {
                "name": "Fazil Updated",
                "username": "fazil_senior_sdet"
            }
            """;
        Response response = put("/users/1", body);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Fazil Updated");
    }

    @Test(priority = 5, description = "Delete user - verify 200")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteUser() {
        Response response = delete("/users/1");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 6, description = "Get invalid user - verify 404")
    @Severity(SeverityLevel.NORMAL)
    public void testGetInvalidUser() {
        Response response = get("/users/999");
        Assert.assertEquals(response.statusCode(), 404);
    }
}