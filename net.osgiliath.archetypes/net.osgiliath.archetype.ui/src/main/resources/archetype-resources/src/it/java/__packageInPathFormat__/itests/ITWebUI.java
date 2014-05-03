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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITWebUI {
	private static Logger LOG = LoggerFactory.getLogger(ITWebUI.class);
	
	@Test
	public void testMyUI()  {
		@Test
		public void testSayHello() throws Exception {
			final WebDriver driver;
			driver = new FirefoxDriver();

			// Sleep until the elements we want is visible or 5 seconds is over
			(new WebDriverWait(driver, 200))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							driver.get("http://localhost:8181/${package}");
							boolean ret = d.getTitle().toLowerCase()
									.startsWith("${package}");
							if (!ret) {
								driver.navigate().refresh();
							}
							return ret;
						}
					});
			//TODO your test here
			driver.close();
	}
}