package net.osgiliath.messaging.repository.impl.itests;

/*
 * #%L
 * net.osgiliath.hello.business.impl
 * %%
 * Copyright (C) 2013 Osgiliath
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.CharSequenceUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestContainer;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.PaxExamRuntime;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.ops4j.pax.exam.karaf.container.internal.runner.*;
//import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO example of an integration test
 * 
 * @author charliemordant
 * 
 */
public class ITHelloWebUITest {
	private static Logger LOG = LoggerFactory.getLogger(ITHelloWebUITest.class);

	@Inject
	private BundleContext bundleContext;
	private static TestContainer container;
	/**
	 * . A correct {@link AbstractUtilisateur} Name
	 */
	private static final String CORRECT_NAME = "CorrectName";

	// Exported service via blueprint.xml
	// @BeforeClass
	// public static void startKarafContainer() throws IOException {
	// //KarafJavaRunner runner = new KarafJavaRunner();
	// ExamSystem system = PaxExamRuntime.createTestSystem(config());
	// container = PaxExamRuntime.createContainer(system);
	//
	// container.start();
	// }
	//
	// @AfterClass
	// public static void stopKarafContainer() {
	// container.stop();
	// }

	@Test
	public void testSayHello() throws Exception {
		final WebDriver driver;
		driver = new FirefoxDriver();

		// Sleep until the elements we want is visible or 5 seconds is over
		long end = System.currentTimeMillis() + 10000;
		(new WebDriverWait(driver, 120))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						driver.get("http://localhost:8181/net.osgiliath.hello.ui");
						boolean ret = d.getTitle().toLowerCase()
								.startsWith("hello");
						if (!ret) {
							driver.navigate().refresh();
						}
						return ret;
					}
				});
		WebElement element;
		element = driver.findElement(By.id("helloInput"));
		element.click();
		element.sendKeys(CORRECT_NAME);
		element = driver.findElement(By.id("helloButton"));
		element.click();
		(new WebDriverWait(driver, 20))
		.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				
				Collection<WebElement> cells =  d.findElements(By.xpath("//table//tr//td"));
				
				return cells.size() == 1;
			}
		});
		end = System.currentTimeMillis() + 10000;
		
		driver.close();
	}

}
