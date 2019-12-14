package com.automation.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.automation.utilities.MonitoringMail;
import com.automation.utilities.TestConfig;

public class TestHostAddress {

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		MonitoringMail mail = new MonitoringMail();
		String messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
				+ ":8080/job/DataDrivenFramework/Extent_20Reports/";
		System.out.println(messageBody);
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
	}

}
