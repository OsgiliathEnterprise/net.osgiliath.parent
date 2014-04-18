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
//	@BeforeClass
//	public static void startKarafContainer() throws IOException {
//		//KarafJavaRunner runner = new KarafJavaRunner();
//		ExamSystem system = PaxExamRuntime.createTestSystem(config());
//		container = PaxExamRuntime.createContainer(system);
//		
//		container.start();
//	}
//
//	@AfterClass
//	public static void stopKarafContainer() {
//		container.stop();
//	}

	@Test
	public void testSayHello() throws Exception {
		WebDriver driver;
		driver = new FirefoxDriver();
		driver.get("http://localhost:8181/net.osgiliath.hello.ui");
		// Sleep until the elements we want is visible or 5 seconds is over
		long end = System.currentTimeMillis() + 10000;
		WebElement element;
		element = driver.findElement(By.id("helloInputDiv"));
		element.click();
		element.sendKeys(CORRECT_NAME);
		element = driver.findElement(By.id("helloButton"));
		element.click();
		end = System.currentTimeMillis() + 10000;
//		while (System.currentTimeMillis() < end) {
//			try {
//				element = driver.findElement(By.id("result"));
//				break;
//			} catch (Exception e) {
//			}
//		}
//				assertEquals(
//				driver.getTitle(),
//				"Error - java.lang.IllegalArgumentException: a User already exist with this key");
		driver.close();
	}

}
