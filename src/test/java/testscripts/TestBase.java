package testscripts;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import base.ControlActions;
import pages.LoginPage;

public class TestBase {

	@BeforeMethod
	public void setup() {
		System.out.println("PREREQUISITE - Launch oranage HRM URL");
		ControlActions.start();
		System.out.println("PREREQUISITE - User Login with valid credential");
		LoginPage loginPage = new LoginPage();
		loginPage.doLogin("Admin", "yW3kfVH9J@");
	}
	
	@AfterMethod
	public void tearDown() {
		ControlActions.closeBrowser();
	}
}
