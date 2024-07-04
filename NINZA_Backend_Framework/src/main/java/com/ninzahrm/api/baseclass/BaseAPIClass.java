
package com.ninzahrm.api.baseclass;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {
	
	
	public JavaUtility jlib = new JavaUtility();
	public FileUtility flib = new FileUtility();
	public DataBaseUtility dblib = new DataBaseUtility();
	public static RequestSpecification reqSpecObj;
	public static ResponseSpecification resSpecObj;
	
	
	
	@BeforeSuite
	public void configBS() throws Throwable {
		
		dblib.connectToDB();
		System.out.println("Connect to DB");
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
	//	builder.setAuth(basic("username", "password"));
	//	builder.addHeader("", "");
		builder.setBaseUri(flib.getDataFromPropertiesFile("BASEUri"));
		reqSpecObj = builder.build();
		
		ResponseSpecBuilder resbuilder = new ResponseSpecBuilder();
		resbuilder.expectContentType(ContentType.JSON);
		 resSpecObj = resbuilder.build();
		
		
		
	}
	
	@AfterSuite 
	public void configAS() throws Throwable {
		dblib.closeDb();
		System.out.println("===Disconnect to DB===");
		
	
	}


}
