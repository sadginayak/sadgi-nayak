package com.mobileautomation.platform;

import org.openqa.selenium.By;

public class AndroidPlatformHook implements PlatformHook {

    @Override
    public By select(By androidLocator, By iosLocator) {
        return androidLocator;
    }
}