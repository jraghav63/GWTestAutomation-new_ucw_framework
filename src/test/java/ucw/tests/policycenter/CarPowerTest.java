package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.enums.Environments.DEV;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class CarPowerTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-1975: Car power modification - Alfa Romeo Certified - All countries");
		policyCenter = basePage.loginPolicyCenter();
		basePage.selectManagementType(INSURED);
	}

	@Test(priority = 1, description = "Decline car power in France if beyond maximum value in Policy Center")
	public void test_declineCarPowerInFrance_PolicyCenter() {
		int carPower;
		String errorMsg;
		if (basePage.getCurrentEnvironment().equals(DEV)) {
			carPower = 1000;
			errorMsg = "Car Power (CV Din) : Car Power can not be more than 999.";
		} else {
			carPower = 371;
			errorMsg = "Car Power (CV Din) : Car Power can not be more than 370.";
		}
		basePage.selectLabel(SPOTICAR)
				.selectCountry(France)
				.selectRandomCoverageType()
				.selectCarPower(carPower);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().inputVehicleDetailsInfo();
		String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMsg, errorMsg, "Actual error message of car power validation is not matching with expected");
	}

	@Test(priority = 2, description = "Accept max car power in France country in Policy Center")
	public void test_acceptCarPowerInFrance_PolicyCenter() {
		int carPower = (basePage.getCurrentEnvironment().equals(DEV)) ? 999 : 370;
		basePage.selectLabel(SPOTICAR)
				.selectCountry(France)
				.selectRandomCoverageType()
				.selectCarPower(carPower);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center policy creation with max car power is failed");
	}

	@Test(priority = 3, description = "Accept max car power in France with ARC in Policy Center")
	public void test_acceptMaxCarPowerInFrance_WithARC_PolicyCenter() {
		basePage.selectLabel(ARC)
				.selectCountry(France)
				.selectRandomCoverageType()
				.selectCarPower(999);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center policy creation with max car power for ARC is failed");
	}

	@Test(priority = 4, description = "Decline car power in Spain if beyond maximum value")
	public void test_declineCarPowerInSpain_PolicyCenter() {
		int carPower;
		String errorMsg;
		if (basePage.getCurrentEnvironment().equals(DEV)) {
			carPower = 1000;
			errorMsg = "Car Power (CV Din) : Car Power can not be more than 999.";
		} else {
			carPower = 371;
			errorMsg = "Car Power (CV Din) : Car Power can not be more than 370.";
		}
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Spain)
				.selectRandomCoverageType()
				.selectCarPower(carPower);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().inputVehicleDetailsInfo();
		String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMsg, errorMsg);
	}

	@Test(priority = 5, description = "Accept max car power in Spain")
	public void test_acceptMaxCarPowerInSpain_PolicyCenter() {
		int carPower = (basePage.getCurrentEnvironment().equals(DEV)) ? 999 : 370;
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Spain)
				.selectRandomCoverageType()
				.selectCarPower(carPower);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
	}

	@Test(priority = 6, description = "Accept max car power in Spain with ARC")
	public void test_acceptMaxCarPowerInSpain_WithARC_PolicyCenter() {
		basePage.selectLabel(ARC)
				.selectCountry(Spain)
				.selectRandomCoverageType()
				.selectCarPower(999);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
	}

	@Test(priority = 7, description = "Decline car power in Italy if beyond maximum value in Policy Center")
	public void test_declineCarPowerInItaly_PolicyCenter() {
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Italy)
				.selectRandomCoverageType()
				.selectCarPower(250);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().addVehicleBody();
		policyCenter.atPolicyPage().setCarPower();
		String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMsg, "Car Power (kW) : Car Power can not be more than 249 kW.");
	}

	@Test(priority = 8, description = "Accept max car power in Italy country in Policy Center")
	public void test_acceptMaxCarPowerInItaly_PolicyCenter() {
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Italy)
				.selectRandomCoverageType()
				.selectCarPower(249);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
	}

	@Test(priority = 9, description = "Accept max car power in Italy with ARC in Policy Center")
	public void test_acceptMaxCarPowerInItaly_WithARC_PolicyCenter() {
		basePage.selectLabel(ARC)
				.selectCountry(Italy)
				.selectRandomCoverageType()
				.selectCarPower(999);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
	}

	@Test(priority = 10, description = "Decline car power in Germany if beyond maximum value in Policy Center")
	public void test_declineCarPowerInGermany_PolicyCenter() {
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Germany)
				.selectRandomCoverageType()
				.selectCarPower(301);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
		policyCenter.atPolicyPage().inputVehicleDetailsInfo();
		String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
		assertEquals(actualMsg, "Car Power (kW) : Car Power can not be more than 300KWH.");
	}

	@Test(priority = 11, description = "Accept max car power in Germany country in Policy Center")
	public void test_acceptMaxCarPowerInGermany_PolicyCenter() {
		basePage.selectLabel(SPOTICAR)
				.selectCountry(Germany)
				.selectRandomCoverageType()
				.selectCarPower(300);

		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().createNewPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
	}
}