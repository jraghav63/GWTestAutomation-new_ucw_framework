package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.enums.EnergyTypes;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.France;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.EnergyTypes.*;
import static ucw.enums.EnergyTypes.DIESEL;
import static ucw.enums.Labels.DSC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOFranceDSCTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-436: France EGVO and EGVO + Maintenance JV & SA - DS Certified");
		basePage.selectLabel(DSC)
				.selectCountry(France)
				.selectManagementType(INSURED);
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
	public Object[][] get4YearPrices() {
		return new Object[][] {
				new Object[] {"DS3", "15,41"},
				new Object[] {"DS4", "17,37"},
				new Object[] {"DS5", "18,63"},
				new Object[] {"DS7", "37,26"},
				new Object[] {"DS9", "18,63"},
		};
	}

	@Test(description = "EGVO SA DS Certified France M 4/50", dataProvider = "get4YearPrices")
	public void test_basePrice4_50(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(EGVOMaintenanceDSC4_50)
				.selectCoverageType(GVODSC4_80)
				.selectRandomCar()
				.selectModel(model)
				.selectMileageLimitedAt(50000)
				.selectMaintenanceDuration(40)
				.selectWarrantyDuration(40);
		dataFileName = EGVOMaintenanceDSC4_50.replace("/","_") + "_" + model;

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
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "1,15", "Assistance Premium price isn't matched as expected");
	}

	@Test(description = "EGVO SA DS Certified France 4/80 price", dataProvider = "get4YearPrices")
	public void test_basePrice4_80(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(EGVODSC4_80)
				.selectCoverageType(GVODSC4_80)
				.selectRandomCar()
				.selectModel(model)
				.selectMileageLimitedAt(80000)
				.selectWarrantyDuration(40);
		dataFileName = EGVODSC4_80.replace("/","_") + "_" + model;

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
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "1,15", "Assistance Premium price isn't matched as expected");
	}

	@DataProvider
	public Object[][] get4150_7150Prices() {
		return new Object[][] {
				new Object[] {EGVODSC4_150, 4, "DS3", "18,26"},
				new Object[] {EGVODSC7_150, 7, "DS4", "20,61"},
				new Object[] {EGVODSC4_150, 4, "DS5", "22,13"},
				new Object[] {EGVODSC7_150, 7, "DS7", "44,25"},
				new Object[] {EGVODSC7_150, 4, "DS9", "44,25"}
		};
	}

	@Test(description = "EGVO SA DS Certified France 4/150 & 7/150 price", dataProvider = "get4150_7150Prices")
	public void test_basePrice7_150(String egvoType, int vehicleAge, String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(egvoType)
				.selectCoverageType(GVODSC7_150)
				.selectRandomCar()
				.selectModel(model)
				.selectRegistrationDate(vehicleAge)
				.selectWarrantyDuration(40);
		dataFileName = egvoType.replace("/","_") + "_" + model;

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
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "1,15", "Assistance Premium price isn't matched as expected");
	}

	@DataProvider
	public Object[][] getCombinedVariables() {
		return new Object[][] {
				new Object[] {new EnergyTypes[]{HYBRID, HYBRIDPLUGIN, GNV}, "1,25"},
				new Object[] {new EnergyTypes[]{ELECTRIC                 }, "0,6" },
				new Object[] {new EnergyTypes[]{GPL, ESSENCE, DIESEL     }, "1"}
		};
	}

	@Test(description = "EGVO France DS Certified combined variable", dataProvider = "getCombinedVariables")
	public void test_combinedVariable(EnergyTypes[] energyTypes, String vehicleSpecific, String maintenanceCV) {
		basePage.selectCoverageType(GVODSC7_150)
				.selectEGVOCoverageType(EGVODSC7_150)
				.selectRandomCar()
				.selectRegistrationDate(7)
				.selectEnergyTypeFromThese(energyTypes)
				.selectVehicleSpecific(vehicleSpecific)
				.selectWarrantyDuration(40);
		dataFileName = "test_EGVOFranceDSC_combinedVariable";

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
		String actualWarrantyCV = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
		assertEquals(actualWarrantyCV, "1", "Warranty combined variable isn't match as expected");

		String actualMaintenanceCV = policyCenter.atPolicyPage().getCombinedVariable(MaintenancePremium);
		assertEquals(actualMaintenanceCV, maintenanceCV, "Maintenance combined variable isn't match as expected");
	}

	@Test(description = "EGVO France DS Certified other rating validations")
	public void test_otherMetrics() {
		basePage.selectCoverageType(GVODSC7_150)
				.selectEGVOCoverageType(EGVODSC4_150)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectWarrantyDuration(40);
		dataFileName = "test_EGVOFranceDSC_otherMetrics";

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
		String actualWarrantyManagementFee = policyCenter.atPolicyPage().getManagementFee(WarrantyManagementFee);
		softAssert.assertEquals(actualWarrantyManagementFee, "1,22", "Warranty Management Fee isn't matched as expected");

		String actualAssistanceManagementFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
		softAssert.assertEquals(actualAssistanceManagementFee, "0", "Assistance Management Fee isn't matched as expected");

		String actualMaintenanceManagementFee = policyCenter.atPolicyPage().getManagementFee(MaintenanceManagementFee);
		softAssert.assertEquals(actualMaintenanceManagementFee, "1,22", "Maintenance Management Fee isn't matched as expected");

		String actualWarrantyCommission = policyCenter.atPolicyPage().getCommissions(WarrantyCommission);
		softAssert.assertEquals(actualWarrantyCommission, "0", "Warranty commission isn't matched as expected");

		String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
		softAssert.assertEquals(actualAssistanceCommission, "0", "Assistance commission isn't matched as expected");

		String actualMaintenanceCommission = policyCenter.atPolicyPage().getCommissions(MaintenanceCommission);
		softAssert.assertEquals(actualMaintenanceCommission, "0", "Maintenance commission isn't matched as expected");

		String actualWarrantyCommissionJV = policyCenter.atPolicyPage().getCommissionJV(WarrantyJVCommission);
		softAssert.assertEquals(actualWarrantyCommissionJV, "0", "Warranty JV commission isn't matched as expected");

		String actualAssistanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(AssistanceJVCommission);
		softAssert.assertEquals(actualAssistanceCommissionJV, "0", "Assistance JV commission isn't matched as expected");

		String actualMaintenanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(MaintenanceJVCommission);
		softAssert.assertEquals(actualMaintenanceCommissionJV, "0", "Maintenance JV commission isn't matched as expected");

		String actualWarrantyTax = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
		softAssert.assertEquals(actualWarrantyTax, "0,18", "Warranty taxes isn't matched as expected");

		String actualAssistanceTax = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
		softAssert.assertEquals(actualAssistanceTax, "0,09", "Assistance taxes isn't matched as expected");

		String actualMaintenanceTax = policyCenter.atPolicyPage().getTaxes(MaintenanceTaxes);
		softAssert.assertEquals(actualMaintenanceTax, "0,2", "Maintenance taxes isn't matched as expected");

		softAssert.assertAll();
	}
}