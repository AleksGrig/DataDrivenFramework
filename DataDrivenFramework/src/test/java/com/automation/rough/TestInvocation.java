package com.automation.rough;

import org.testng.annotations.Test;

public class TestInvocation {

	// Number of parallelly working threads, without threadPoolSize - consecutively
	@Test(invocationCount = 5, threadPoolSize = 5)
	public void test() throws InterruptedException {
		System.out.println("Invocation test");
		Thread.sleep(2000);
	}
}
