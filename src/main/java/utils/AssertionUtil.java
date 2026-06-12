package utils;

import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertionUtil {

    private static final Logger log = LoggerFactory.getLogger(AssertionUtil.class);
    private final SoftAssert softAssert = new SoftAssert();

    // ── Soft Assertions ──────────────────────────────────────

    @Step("Soft assert equals: {actual} == {expected}")
    public void softAssertEquals(Object actual, Object expected, String message) {
        log.info("Soft Assert Equals — Expected: {} | Actual: {}", expected, actual);
        softAssert.assertEquals(actual, expected, message);
    }

    @Step("Soft assert true: {condition}")
    public void softAssertTrue(boolean condition, String message) {
        log.info("Soft Assert True — {}", message);
        softAssert.assertTrue(condition, message);
    }

    @Step("Soft assert false: {condition}")
    public void softAssertFalse(boolean condition, String message) {
        log.info("Soft Assert False — {}", message);
        softAssert.assertFalse(condition, message);
    }

    @Step("Soft assert not null")
    public void softAssertNotNull(Object object, String message) {
        log.info("Soft Assert Not Null — {}", message);
        softAssert.assertNotNull(object, message);
    }

    @Step("Soft assert contains: {actual} contains {expected}")
    public void softAssertContains(String actual, String expected, String message) {
        log.info("Soft Assert Contains — Expected: {} | Actual: {}", expected, actual);
        softAssert.assertTrue(
            actual.contains(expected),
            message + " | Expected to contain: " + expected + " but was: " + actual
        );
    }

    @Step("Assert all soft assertions")
    public void assertAll() {
        log.info("Executing assertAll()");
        softAssert.assertAll();
    }

    // ── Hard Assertions ──────────────────────────────────────

    @Step("Assert equals: {actual} == {expected}")
    public static void assertEquals(Object actual, Object expected, String message) {
        log.info("Hard Assert Equals — Expected: {} | Actual: {}", expected, actual);
        org.testng.Assert.assertEquals(actual, expected, message);
    }

    @Step("Assert true: {condition}")
    public static void assertTrue(boolean condition, String message) {
        log.info("Hard Assert True — {}", message);
        org.testng.Assert.assertTrue(condition, message);
    }

    @Step("Assert not null")
    public static void assertNotNull(Object object, String message) {
        log.info("Hard Assert Not Null — {}", message);
        org.testng.Assert.assertNotNull(object, message);
    }

    @Step("Assert contains: {actual} contains {expected}")
    public static void assertContains(String actual, String expected, String message) {
        log.info("Hard Assert Contains — Expected: {} | Actual: {}", expected, actual);
        org.testng.Assert.assertTrue(
            actual.contains(expected),
            message + " | Expected to contain: " + expected + " but was: " + actual
        );
    }

    @Step("Assert status code: {actual} == {expected}")
    public static void assertStatusCode(int actual, int expected) {
        log.info("Assert Status Code — Expected: {} | Actual: {}", expected, actual);
        org.testng.Assert.assertEquals(actual, expected,
            "Status code mismatch! Expected: " + expected + " but got: " + actual);
    }
}
