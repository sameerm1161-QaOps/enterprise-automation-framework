package listeners;

import core.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Automatically retries flaky tests.
 * Max retries configurable via config.properties.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private final int maxRetry = Integer.parseInt(
            ConfigManager.getInstance().get("retry.max", "2")
    );

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess() && retryCount < maxRetry) {
            retryCount++;
            log.warn("Retrying test '{}' — attempt {}/{}",
                    result.getName(), retryCount, maxRetry);
            return true;
        }
        return false;
    }
}