package com.automation.rough;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLogs {

	private static Logger log = Logger.getLogger(TestLogs.class.getName());

	public static void main(String[] args) {
		Date date = new Date();
		System.setProperty("current.date", date.toString().replace(":", "_").replace(" ", "_"));
		PropertyConfigurator.configure("src/test/resources/properties/log4j.properties");

		log.info("This is information log");
		log.error("Here is error log");
	}

}
