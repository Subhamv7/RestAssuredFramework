package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninja.hrm.constants.endpoints.IEndPoints;
import com.ninza.hrm.pojoClass.ProjectPojo;
import com.ninzahrm.api.baseclass.BaseAPIClass;

import io.restassured.response.Response;

public class ProjectTest extends BaseAPIClass {

	ProjectPojo pObj;

	@Test
	public void createProject() throws Throwable {

		String BASEURI = flib.getDataFromPropertiesFile("BASEUri");
		String DBURL = flib.getDataFromPropertiesFile("DBUrl");
		String DBUSERNAME = flib.getDataFromPropertiesFile("DB_Username");
		String DBPASSWORD = flib.getDataFromPropertiesFile("DB_Password");  

		// Create an object POJO class

		String expectedMsg = "Successfully Added";
		String projectName = "Sub_" + jlib.getRandomNumber();

		pObj = new ProjectPojo(projectName, "Created", "Subham", 9);

		// Verify the projectName in API layer
		Response resp = given()
				.spec(reqSpecObj)
				.when().post(IEndPoints.ADDProj);

		resp.then()
		.assertThat().statusCode(201)
		.assertThat().time(Matchers.lessThan(3000L))
		.spec(resSpecObj)
		.log().all();

		String actMsg = resp.jsonPath().getString("msg");
		Assert.assertEquals(actMsg, expectedMsg);

		// Verify the projectName in DB Layer

		boolean flag = dblib.executeQueryVerifyAndGetData("select * from project", 4, projectName);

		Assert.assertTrue(flag, "Project in DB is not verified");

	}

	@Test(dependsOnMethods = "createProject")
	public void createDuplicateTest() throws Throwable {
		String BASEURI = flib.getDataFromPropertiesFile("BASEUri");

				given()
				.spec(reqSpecObj)

				.when().post(IEndPoints.ADDProj)

				.then()

				.assertThat().statusCode(409)
				.spec(resSpecObj)
				.log().all();

	}

}
