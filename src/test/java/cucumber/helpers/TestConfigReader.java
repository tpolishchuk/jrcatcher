package cucumber.helpers;

import cucumber.enums.BrowserType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestConfigReader {

    private final String propertyFilePath = "src/test/resources/local.test.properties";
    private static TestConfigReader instance;
    private static Properties properties;

    private TestConfigReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Test properties are not found at " + propertyFilePath);
        }
    }

    public static TestConfigReader getInstance() {
        if (instance == null) {
            instance = new TestConfigReader();
        }
        return instance;
    }

    private String getProperty(String name) {
        return properties.getProperty(name);
    }

    public String getBasicUrl() {
        return getProperty("basic_url");
    }

    public BrowserType getBrowser() {
        return BrowserType.valueOf(getProperty("browser").toUpperCase());
    }

    public boolean runInHeadlessMode() {
        return Boolean.parseBoolean(getProperty("run_in_headless_mode"));
    }

    public long getWebElementWaitTimeoutSeconds() {
        return Long.parseLong(getProperty("web_element_wait_timeout_seconds"));
    }
}
