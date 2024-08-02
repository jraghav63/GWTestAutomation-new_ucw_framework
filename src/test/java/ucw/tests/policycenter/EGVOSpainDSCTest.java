package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Spain;
import static ucw.enums.CoverageTerms.AssistancePremium;
import static ucw.enums.CoverageTerms.WarrantyPremium;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.DSC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOSpainDSCTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWPOLICY-434: [R2.5-SPAIN][EGVO Standalone]Rating DSC");
		basePage.selectLabel(DSC)
				.selectCountry(Spain)
				.selectManagementType(INSURED)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
		BillingCenter billingCenter = basePage.loginBillingCenter();
		billingCenter.navigateToAccountsPage();
		billingCenter.atAccountsPage().createAccount();
		assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

		policyCenter = basePage.loginPolicyCenter();
		policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
		policyCenter.navigateToAccountPage();
		policyCenter.atAccountPage().createNewAccount();
	}

	@DataProvider
	public Object[][] getRating2() {
		return new Object[][] {
				new Object[] {100,   40000,  EGVOWTDS2_40, new String[]{"DS3"},        "4,43" , "15,88"},
				new Object[] {40001, 60000,  EGVOWTDS2_60, new String[]{"DS4"},        "10,42", "19,31"},
				new Object[] {60001, 80000,  EGVODS2_100,  new String[]{"DS5", "DS7"}, "18,01", "23,03"},
		};
	}

	@Test(description = "EGVO SA DS Certified Spain premium prices 2 years", dataProvider = "getRating2")
	public void test_basePriceAge2(long minMileage,
								   long maxMileage,
								   String egvoType,
								   String[] models,
								   String warrantyPrice, String warrantyMargin) {
		basePage.selectEGVOCoverageType(egvoType)
				.selectCoverageType(GVODSC4_80)
				.selectRandomCar()
				.selectRegistrationDate(2)
				.selectMileageInRange(minMileage, maxMileage)
				.selectModelFromThese(models);
		dataFileName = egvoType.replace("/","_");

		policyCenter.goBackToAccSummary();
		policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().addVehicleDetails();
		policyCenter.atPolicyPage().validateCoverageType();
		policyCenter.atPolicyPage().reviewPolicy();
		policyCenter.atPolicyPage().goToPayment();
		policyCenter.atPolicyPage().issuePolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
		policyCenter.goBackToAccSummary();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectEGVO();
		policyCenter.atPolicyPage().createEGVOPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "EGVO SA policy creation is failed");

		policyCenter.atPolicyPage().clickQuoteTab();
		policyCenter.atPolicyPage().goToRatingWorksheet();
		policyCenter.atPolicyPage().expandAllNodes();

		SoftAssert softAssert = new SoftAssert();
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualWarrantyMargin = policyCenter.atPolicyPage().getBaseMargin(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyMargin, warrantyMargin, "Warranty Margin isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "0,37", "Assistance Premium price isn't matched as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] getRating4_5() {
		return new Object[][] {
				new Object[] {GVODSC4_80,  4, 100,   40000,  EGVOWTDS4_40, new String[]{"DS3"},       "5,2"  , "17"   },
				new Object[] {GVODSC4_80,  4, 40001, 60000,  EGVOWTDS4_60, new String[]{"DS4"},       "12,21", "19,4" },
				new Object[] {GVODSC7_150, 5, 80000, 150000, EGVODS5_150,  new String[]{"DS5","DS7"}, "21,13", "23,68"},
		};
	}

	@Test(description = "EGVO SA DS Certified Spain premium prices", dataProvider = "getRating4_5")
	public void test_basePriceAge4_5(String coverageType,
								  int vehicleAge,
								  long minMileage,
								  long maxMileage,
								  String egvoType,
								  String[] models,
								  String warrantyPrice, String warrantyMargin) {
		basePage.selectEGVOCoverageType(egvoType)
				.selectCoverageType(coverageType)
				.selectRandomCar()
				.selectRegistrationDate(vehicleAge)
				.selectMileageInRange(minMileage, maxMileage)
				.selectModelFromThese(models);
		dataFileName = egvoType.replace("/","_");

		policyCenter.goBackToAccSummary();
		policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
		policyCenter.atPolicyPage().addVehicleDetails();
		policyCenter.atPolicyPage().validateCoverageType();
		policyCenter.atPolicyPage().reviewPolicy();
		policyCenter.atPolicyPage().goToPayment();
		policyCenter.atPolicyPage().issuePolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
		policyCenter.goBackToAccSummary();
		policyCenter.atAccountPage().navigateToSubmissionPage();
		policyCenter.atAccountPage().selectEGVO();
		policyCenter.atPolicyPage().createEGVOPolicy();
		assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "EGVO SA policy creation is failed");

		policyCenter.atPolicyPage().clickQuoteTab();
		policyCenter.atPolicyPage().goToRatingWorksheet();
		policyCenter.atPolicyPage().expandAllNodes();

		SoftAssert softAssert = new SoftAssert();
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualWarrantyMargin = policyCenter.atPolicyPage().getBaseMargin(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyMargin, warrantyMargin, "Warranty Margin isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "0,37", "Assistance Premium price isn't matched as expected");

		softAssert.assertAll();
	}
}