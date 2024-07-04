package com.ninza.hrm.api.projecttest;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.hrm.api.genericUtility.JavaUtility;
import com.ninza.hrm.pojoClass.EmployeePojo;
import com.ninza.hrm.pojoClass.ProjectPojo;

import io.restassured.http.ContentType;

public class AddEmployeeWithoutEmail {

	@Test
	public void addEmployeeTest() throws SQLException {
		
		JavaUtility jlib = new JavaUtility();

		// Create an object to POJO class
		Random ran = new Random();
		int randomNum = ran.nextInt(5000);

		String projectName = "Union_" +jlib.getRandomNumber();
		String userName = "user_" + jlib.getRandomNumber();

		// API 1 - Add a project in side server
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Subham", 10);

		given().contentType(ContentType.JSON).body(pObj)

				.when().post("http://49.249.28.218:8091/addProject").then().assertThat().statusCode(201).and()
				.assertThat().body("msg", Matchers.equalTo("Successfully Added")).log().all();

		// API 2 - Add emp to same project
		EmployeePojo empObj = new EmployeePojo("Analyst", "26/08/1997", "", userName, 5, "9876876589",
				projectName, "ROLE_EMPLOYEE", userName);

		given().contentType(ContentType.JSON).body(empObj)
		.when().post("http://49.249.28.218:8091/employees")
		.then().assertThat().statusCode(500)
		.log().all();

	}

}
