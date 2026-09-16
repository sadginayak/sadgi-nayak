package com.mobileautomation.monefy.config;

import java.util.HashMap;
import java.util.Map;

public final class MonefyCapabilities {

    private MonefyCapabilities() {
    }

    public static Map<String, Object> build() {
        Map<String, Object> capabilities = new HashMap<>();

        capabilities.put(
                "appWaitActivity",
                "com.monefy.activities.onboarding.OnboardingActivity_"
        );
        capabilities.put("noReset", false);
        return capabilities;
    }
}