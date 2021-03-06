package com.automation.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.automation.utilities.ExcelReader;
import com.automation.utilities.ExtentManager;
import com.automation.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 *  	Initializing: 
 * 		WebDriver
 * 		Properties
 * 		Logs - log4j.jar, *.log file/s, log4j.properties, Logger class
 * 		ExtentReports
 * 		Excel
 * 		Mail
 * 		Jenkins
 * 		DataBase
 */

public class TestBase {

	protected static WebDriver driver;
	protected static Properties config = new Properties();
	protected static Properties OR = new Properties();
	protected static FileInputStream fis;
	protected static Logger log = LogManager.getLogger(TestBase.class.getName());
	protected static ExcelReader excel = new ExcelReader("src/test/resources/excel/testdata.xlsx");
	protected static WebDriverWait wait;
	protected static ExtentReports report = ExtentManager.getInstance();
	protected static ExtentTest test;
	protected static String browser;

	public static ExtentTest getTest() {
		return test;
	}

	@BeforeSuite
	public void setUp() {

		// Setting up log4j logger
		Date date = new Date();
		System.setProperty("current.date", date.toString().replace(":", "_").replace(" ", "_"));
		PropertyConfigurator.configure("src/test/resources/properties/log4j.properties");

		if (driver == null) {
			try {
				fis = new FileInputStream("src/test/resources/properties/Config.properties");
				config.load(fis);
				log.debug("Config file loaded");
				fis = new FileInputStream("src/test/resources/properties/OR.properties");
				OR.load(fis);
				log.debug("OR file loaded");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Jenkins environment
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		} else {
			browser = config.getProperty("browser");
		}
		config.setProperty("browser", browser);

		if (config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); // If use WebDriver version 2, for version 3 use
											// System.setProperty("webdriver.gecko.driver", "gecko.exe");
			log.info("Firefox launched");
		} else if (config.getProperty("browser").equals("chrome")) {
//			System.setProperty("webdriver.chrome.driver", "src/test/resources/executables/chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("Chrome launched");
		} else if (config.getProperty("browser").equals("ie")) {
			// System.setProperty("webdriver.ie.driver",
			// "src/test/resources/executables/IEDriverServer.exe");
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			log.debug("Explorer launched");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("Test execution completed");
	}

	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on: " + locator);
	}

	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in: " + locator + ". Entered value is: " + value);
	}

	private static WebElement dropdown;

	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown: " + locator + ". Value is: " + value);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public static void verifyEquals(String expected, String actual) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();

			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");

			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}
}
