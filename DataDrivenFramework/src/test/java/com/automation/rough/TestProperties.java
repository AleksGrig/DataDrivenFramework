package com.automation.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {

		Properties config = new Properties();
		Properties OR = new Properties();
		FileInputStream fis = new FileInputStream("src/test/resources/properties/Config.properties");
		config.load(fis);
		fis = new FileInputStream("src/test/resources/properties/OR.properties");
		OR.load(fis);
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("bmlBtn"));

		List<Integer> arr = new ArrayList<>(Arrays.asList(3, 6, 0, 1, 8));
		for (int i = 1; i < arr.size(); i++) {
			for (int j = i; j > 0; j--) {
				if (arr.get(j) < arr.get(j - 1)) {
					int t = arr.get(j - 1);
					arr.set(j - 1, arr.get(j));
					arr.set(j, t);
				}
			}
		}
		System.out.println(arr);
	}
}
