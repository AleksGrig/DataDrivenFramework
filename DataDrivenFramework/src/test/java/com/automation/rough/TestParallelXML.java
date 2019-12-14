package com.automation.rough;

import java.util.Date;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParallelXML {

	@Parameters({ "browser" })
	@Test
	public void test(String browser) throws InterruptedException {
		Date date = new Date();
		System.out.println("Browser is: " + browser + ". Date is: " + date);
		Thread.sleep(2000);
	}
}
