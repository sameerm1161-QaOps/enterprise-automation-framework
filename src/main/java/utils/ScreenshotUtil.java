package utils;

import core.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "screenshots/";

    public static byte[] takeScreenshotAsBytes() {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            return ts.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to take screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }

    public static String takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

            Path path = Paths.get(fileName);
            Files.createDirectories(path.getParent());
            Files.copy(screenshot.toPath(), path);

            log.info("Screenshot saved: {}", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage());
            return "";
        }
    }
}