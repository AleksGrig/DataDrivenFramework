package com.automation.rough;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParametersFromXML {

	@Parameters({ "browser" })
	@Test(groups = "functional")
	public void testParameters(String browser) {
		System.out.println(browser);
	}
}
