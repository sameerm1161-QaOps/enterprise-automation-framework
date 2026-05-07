package core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton ConfigManager.
 * Loads environment-specific properties.
 * Priority: System Property > env.properties > config.properties
 *
 * Usage: ConfigManager.get("base.url")
 */
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        String env = System.getProperty("env", "dev");
        loadProperties("config/config.properties");
        loadProperties("config/" + env + ".properties");
        log.info("ConfigManager initialized for environment: {}", env);
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties(String filePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                properties.load(is);
                log.debug("Loaded properties from: {}", filePath);
            }
        } catch (IOException e) {
            log.warn("Could not load properties file: {}", filePath);
        }
    }

    public String get(String key) {
        // System property overrides config file (useful for CI/CD)
        String sysProp = System.getProperty(key);
        if (sysProp != null) return sysProp;

        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Config key not found: " + key);
        return value;
    }

    public String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}