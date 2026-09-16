package com.mobileautomation.capabilities;

import java.util.HashMap;
import java.util.Map;

/** * Builds provider-specific extra capabilities for the selected execution * environment. 
 * * * This class isolates knowledge of cloud-provider capability structures such * as Sauce Labs "sauce:options" and BrowserStack "bstack:options". 
 * * DriverFactory remains provider-agnostic and only consumes the resulting * capabilities map. 
 * * * Provider credentials are read from environment variables and are never *
 *  stored * in source code or static configuration files. */
public enum CapabilitiesProvider {

    LOCAL {
        @Override
        public Map<String, Object> build() {
            return Map.of();
        }
    },

    SAUCE_LABS {
        @Override
        public Map<String, Object> build() {
            Map<String, Object> sauceOptions = new HashMap<>();
            sauceOptions.put("username", requiredEnv("SAUCE_USERNAME"));
            sauceOptions.put("accessKey", requiredEnv("SAUCE_ACCESS_KEY"));
            sauceOptions.put("appiumVersion", "stable");

            Map<String, Object> capabilities = new HashMap<>();
            capabilities.put("sauce:options", sauceOptions);
            return capabilities;
        }
    },

    BROWSERSTACK {
        @Override
        public Map<String, Object> build() {
            Map<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("userName", requiredEnv("BROWSERSTACK_USERNAME"));
            bstackOptions.put("accessKey", requiredEnv("BROWSERSTACK_ACCESS_KEY"));

            Map<String, Object> capabilities = new HashMap<>();
            capabilities.put("bstack:options", bstackOptions);
            return capabilities;
        }
    };

    public abstract Map<String, Object> build();

    /** * Reads a required secret from an environment variable and fails fast if it * is missing or blank. 
     * * * @param name environment variable name 
     * * @return the environment variable value 
     * * @throws RuntimeException if the variable is missing or blank */
    public static CapabilitiesProvider from(String environment) {
        return switch (environment.toLowerCase()) {
            case "local" -> LOCAL;
            case "saucelabs" -> SAUCE_LABS;
            case "browserstack" -> BROWSERSTACK;
            default -> throw new IllegalArgumentException("Unknown environment: " + environment);
        };
    }

    static String requiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing required environment variable: " + name);
        }
        return value;
    }
}