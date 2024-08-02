package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.Makes.RAM;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class RAMBrandTest extends BaseTest {
	private PolicyCenter policyCenter;
		
	@BeforeClass
	public void goToPolicyCenter() {
		startTest("UCWAR-1335: Add \"RAM\" to Brand list in Portal and Policy Center");
		basePage.selectLabel(SPOTICAR)
				.selectManagementType(INSURED);
		policyCenter = basePage.loginPolicyCenter();
	}

	@Test(description = "Validate that Make value RAM is added")
	public void test_RAMMake() {
		basePage.selectRandomCountry()
				.selectRandomCoverageType()
				.selectCar(RAM);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center with make RAM creation is failed");
	}
}





