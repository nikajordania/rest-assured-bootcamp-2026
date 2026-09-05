package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
    private final String apiKey;

    public Config() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("secret.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find secret.properties");
            }
            properties.load(input);
            apiKey = properties.getProperty("reqres.key");
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    public String getApiKey() {
        return apiKey;
    }
}
