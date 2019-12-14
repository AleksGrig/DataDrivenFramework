package com.automation.testcases;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.automation.base.TestBase;
import com.automation.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(HashMap<String, String> data)
			throws InterruptedException {
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skiping test case as Runmode for data set as NO");
		}
		click("addCustBtn_CSS");
		type("firstName_CSS", data.get("firstname"));
		type("lastName_CSS", data.get("lastname"));
		type("postCode_CSS", data.get("postcode"));
		click("addBtn_CSS");

		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
	}
}
