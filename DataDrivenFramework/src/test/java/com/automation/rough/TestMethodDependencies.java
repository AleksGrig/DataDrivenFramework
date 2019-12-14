package com.automation.rough;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMethodDependencies {

	@Test(groups = "functional")
	public void init() {
		Assert.fail();
	}

	@Test(dependsOnMethods = { "init" }, groups = "functional")
	public void test1() {

	}

	@Test(groups = "functional")
	public void test2() {

	}

	@Test
	public void test3() {

	}
}
