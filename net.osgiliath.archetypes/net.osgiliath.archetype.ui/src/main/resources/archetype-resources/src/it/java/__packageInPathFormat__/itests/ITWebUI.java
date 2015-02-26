#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.itests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITWebUI {
	private static Logger LOG = LoggerFactory.getLogger(ITWebUI.class);
	private static final String ARTIFACTID = "project.artifactId";
	@Test
	public void testMyUI()  {
			final WebDriver driver;
			driver = new FirefoxDriver();
			
		    // Sleep until the elements we want is visible or 5 seconds is over
		    long end = System.currentTimeMillis() + 10000;
		    (new WebDriverWait(driver, 300)).until(new ExpectedCondition<Boolean>() {
		      public Boolean apply(WebDriver d) {
		        driver.get("http://localhost:8181/" + System.getProperty(ARTIFACTID));
		        boolean ret = d.getTitle().toLowerCase().startsWith(System.getProperty(ARTIFACTID));
		        if (!ret) {
		          driver.navigate().refresh();
		        }
		        return ret;
		      }
		    });
		   
		    WebElement element;
			
			//TODO your test here
			driver.close();
	}
}