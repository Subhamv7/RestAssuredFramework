package com.ninza.hrm.api.projecttest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninja.hrm.constants.endpoints.IEndPoints;
import com.ninza.hrm.pojoClass.EmployeePojo;
import com.ninza.hrm.pojoClass.ProjectPojo;
import com.ninzahrm.api.baseclass.BaseAPIClass;

public class EmployeeTest extends BaseAPIClass{
	
	
	@Test
	public void addEmployeeTest() throws Throwable {

		

		String BASEURI = flib.getDataFromPropertiesFile("BASEUri");
		String DBURL = flib.getDataFromPropertiesFile("DBUrl");
		String DBUSERNAME = flib.getDataFromPropertiesFile("DB_Username");
		String DBPASSWORD = flib.getDataFromPropertiesFile("DB_Password");

		// Create an object to POJO class

		String projectName = "Union_" + jlib.getRandomNumber();
		String userName = "user_" + jlib.getRandomNumber();

		// API 1 - Add a project in side server
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Subham", 10);

		given()
		.spec(reqSpecObj)

				.when().post(IEndPoints.ADDProj)
				
				.then()
				.assertThat().statusCode(201).and()
				.assertThat()
				.body("msg", Matchers.equalTo("Successfully Added"))
				.spec(resSpecObj)
				.log().all();

		// API 2 - Add emp to same project

		EmployeePojo empObj = new EmployeePojo("Analyst", "26/08/1997", "subham@gmail.com", userName, 5, "9876876589",
				projectName, "ROLE_EMPLOYEE", userName);

		given()
		.spec(reqSpecObj)
		.when().post(IEndPoints.ADDEmp)
		
		.then().assertThat()
		.statusCode(201).and().time(Matchers.lessThan(3000L))
		.spec(resSpecObj)
		.log().all();

		// Verify Employee name in DB
		boolean flag = dblib.executeQueryVerifyAndGetData("select * from project", 5, userName);

		Assert.assertTrue(flag, "Employee in DB is not verified");
	}
	
}
