package net.osgiliath.module.exam.selenium.internal;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverSetup {

  WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities);

  DesiredCapabilities getDesiredCapabilities(Proxy proxySettings);
}
