package com.automation.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() throws InterruptedException {
		Thread.sleep(3000);
		verifyEquals("abc", "xyz");

		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");
		log.debug("Login successfully executed");
		// Assert.fail();
	}
}
