package com.automation.rough;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.automation.utilities.DatabaseManager;

public class TestDatabase {

	public static void main(String[] args) throws UnknownHostException, SQLException, ClassNotFoundException {
		DatabaseManager.setMysqlDbConnection();
		System.out.println(DatabaseManager.getMysqlQuery("select email from user where first_name='Dheeru'"));
	}

}
