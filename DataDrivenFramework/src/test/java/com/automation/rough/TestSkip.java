package com.automation.rough;

import org.testng.SkipException;
import org.testng.annotations.Test;

public class TestSkip {

	@Test(groups = "functional")
	public void test() {
		throw new SkipException("Skipping test");
	}
}
