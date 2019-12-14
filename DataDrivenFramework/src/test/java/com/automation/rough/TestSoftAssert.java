package com.automation.rough;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestSoftAssert {

	@Test(groups = "functional")
	public void testSoftAssert() {
		SoftAssert softAssert = new SoftAssert();
		System.out.println("Beginnig of test");
		String expectedResult = "gmail.com";
		String actualResult = "yahoo.com";
		softAssert.assertEquals(actualResult, expectedResult);
		System.out.println("Ending of test");
		softAssert.assertAll();
	}
}
