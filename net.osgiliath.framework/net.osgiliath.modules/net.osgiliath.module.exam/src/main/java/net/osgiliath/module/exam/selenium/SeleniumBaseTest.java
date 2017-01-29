package net.osgiliath.module.exam.selenium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.osgiliath.module.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.module.exam.selenium.internal.DriverFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

public abstract class SeleniumBaseTest{

  private static List<DriverFactory> webDriverThreadPool = Collections
      .synchronizedList(new ArrayList<DriverFactory>());
  private static ThreadLocal<DriverFactory> driverFactory;

  @BeforeClass
  public static void instantiateDriverObject() {
    driverFactory = new ThreadLocal<DriverFactory>() {
      @Override
      protected DriverFactory initialValue() {
        DriverFactory driverFactory = new DriverFactory();
        webDriverThreadPool.add(driverFactory);
        return driverFactory;
      }
    };
  }

  public static WebDriver getDriver() throws Exception {
    return driverFactory.get().getDriver();
  }

  @After
  public static void clearCookies() throws Exception {
    getDriver().manage().deleteAllCookies();
  }

  @AfterClass
  public static void closeDriverObjects() {
    for (DriverFactory driverFactory : webDriverThreadPool) {
      driverFactory.quitDriver();
    }
  }

}
