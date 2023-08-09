package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;

public class DashboardTest extends TestBase {

	@Test
	public void verifyEmpoyeeCount() {
		System.out.println("VERIFY - User profile is displayed");
		DashboardPage dashboardPage = new DashboardPage();
		Assert.assertTrue(dashboardPage.isUserProfileDisplayed(), "User profile was not displayed on dashboard page");
		
		System.out.println("STEP - Mouse Hover on Profile and Click on setting icon on profile");
		dashboardPage.mouseHoverOnUserProfile();
		dashboardPage.clickOnSettingButton();
		
		System.out.println("VERIFY - user menu total 2 options displayed");
		int menuSize = dashboardPage.getUserMenuListSize();
		Assert.assertEquals(menuSize, 2);
		
		System.out.println("STEP -  Click on About");
		dashboardPage.clickOnUserMenuAboutLink();
		
		System.out.println("VERIFY - Employee is more than 0");
		dashboardPage.waitForInvisibilityOfLoader();
		int actualEmpCount = dashboardPage.getEmployeeCount();
		Assert.assertTrue(actualEmpCount > 0);
		
		System.out.println("STEP - Click on OK button on popup.");
		dashboardPage.clickOnOkButton();

	}
}
