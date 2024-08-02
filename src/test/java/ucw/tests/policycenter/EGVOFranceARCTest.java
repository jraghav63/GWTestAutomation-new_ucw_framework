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
import static ucw.enums.Labels.ARC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOFranceARCTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-502: France_Alfa Romeo_EGVO-SA");
		basePage.selectLabel(ARC)
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
	public Object[][] get4YearsPrices() {
		return new Object[][] {
				new Object[] {"Giulietta"          , "15,41"},
				new Object[] {"Tonale"             , "17,37"},
				new Object[] {"Giulia"             , "18,63"},
				new Object[] {"Giulia Quadrifoglio", "37,26"}
		};
	}

	@Test(description = "EGVO SA Alfa Romeo France M 4/50 price", dataProvider = "get4YearsPrices")
	public void test_basePrice_Maintenance4_50(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(EGVOMaintenanceARC4_50)
				.selectCoverageType(GVOARCertfied4_80)
				.selectRandomCar()
				.selectModel(model)
				.selectMileageLimitedAt(50000)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
		dataFileName = EGVOMaintenanceARC4_50.replace("/","_") + "_" + model;

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
		softAssert.assertEquals(actualWarrantyMargin, "6,9", "Warranty Margin isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "1,15", "Assistance Premium price isn't matched as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] getMaintenancePrices4_50() {
		return new Object[][] {
				new Object[] {"Giulietta",           30, 10000, "20,42"},
				new Object[] {"Giulietta",           36, 15000, "22,56"},
				new Object[] {"Giulietta",           40, 20000, "34,06"},
				new Object[] {"Giulietta",           48, 25000, "41,73"},
				new Object[] {"Giulietta",           49, 10000, "30,77"},
				new Object[] {"Giulietta",           52, 15000, "34,06"},
				new Object[] {"Giulietta",           57, 20000, "60,9"},
				new Object[] {"Giulietta",           60, 25000, "70,03"},
				new Object[] {"Tonale",              30, 10000, "23,01"},
				new Object[] {"Tonale",              36, 15000, "25,43"},
				new Object[] {"Tonale",              40, 20000, "40,77"},
				new Object[] {"Tonale",              48, 25000, "58,02"},
				new Object[] {"Tonale",              49, 10000, "33,36"},
				new Object[] {"Tonale",              52, 15000, "36,93"},
				new Object[] {"Tonale",              57, 20000, "70,48"},
				new Object[] {"Tonale",              60, 25000, "81,05"},
				new Object[] {"Giulia",              30, 10000, "26,74"},
				new Object[] {"Giulia",              36, 15000, "29,58"},
				new Object[] {"Giulia",              40, 20000, "45,88"},
				new Object[] {"Giulia",              48, 25000, "67,92"},
				new Object[] {"Giulia",              49, 10000, "37,09"},
				new Object[] {"Giulia",              52, 15000, "41,08"},
				new Object[] {"Giulia",              57, 20000, "72,71"},
				new Object[] {"Giulia",              60, 25000, "83,62"},
				new Object[] {"Giulia Quadrifoglio", 30, 10000, "53,48"},
				new Object[] {"Giulia Quadrifoglio", 36, 15000, "59,17"},
				new Object[] {"Giulia Quadrifoglio", 40, 20000, "91,75"},
				new Object[] {"Giulia Quadrifoglio", 48, 25000, "135,84"},
				new Object[] {"Giulia Quadrifoglio", 49, 10000, "74,18"},
				new Object[] {"Giulia Quadrifoglio", 52, 15000, "82,17"},
				new Object[] {"Giulia Quadrifoglio", 57, 20000, "145,42"},
				new Object[] {"Giulia Quadrifoglio", 60, 25000, "167,23"},
		};
	}

	@Test(description = "Maintenance pure premium prices M 4/50", dataProvider = "getMaintenancePrices4_50")
	public void test_MaintenancePremiumPriceM4_50(String model, int duration, long maintenanceMileage, String price) {
		basePage.selectEGVOCoverageType(EGVOMaintenanceARC4_50)
				.selectCoverageType(GVOARCertfied4_80)
				.selectRandomCar()
				.selectModel(model)
				.selectMileageLimitedAt(50000)
				.selectWarrantyDuration(duration)
				.selectMaintenanceDuration(duration)
				.selectMaintenanceMileage(maintenanceMileage);
		dataFileName = "maintenancePrice_" + EGVOMaintenanceARC4_50.replace("/", "_") + "_" + model;

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

		String actualPrice = policyCenter.atPolicyPage().getPrice(MaintenancePremium);
		assertEquals(actualPrice, price, "Maintenance Premium price isn't match as expected");
	}

	@Test(description = "EGVO SA Alfa Romeo France 4/80 price", dataProvider = "get4YearsPrices")
	public void test_basePrice4_80(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(EGVOARC4_80)
				.selectCoverageType(GVOARCertfied4_80)
				.selectRandomCar()
				.selectModel(model)
				.selectMileageLimitedAt(80000)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
		dataFileName = EGVOARC4_80.replace("/","_") + "_" + model;

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
		softAssert.assertEquals(actualWarrantyMargin, "6,9", "Warranty Margin isn't matched as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "1,15", "Assistance Premium price isn't matched as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] get4150_7150Prices() {
		return new Object[][] {
				new Object[] {EGVOARC4_150, 4, "Giulietta"          , "18,26"},
				new Object[] {EGVOARC7_150, 7, "Tonale"             , "20,61"},
				new Object[] {EGVOARC4_150, 4, "Giulia"             , "22,13"},
				new Object[] {EGVOARC7_150, 7, "Giulia Quadrifoglio", "44,25"}
		};
	}

	@Test(description = "EGVO SA Alfa Romeo France 4/150 & 7/150 price", dataProvider = "get4150_7150Prices")
	public void test_basePrice150(String egvoType, int vehicleAge, String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(egvoType)
				.selectCoverageType(GVOARCertfied7_150)
				.selectRandomCar()
				.selectModel(model)
				.selectRegistrationDate(vehicleAge)
				.selectMileageLimitedAt(150000)
				.selectWarrantyDuration(36);
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

		SoftAssert softAssert = new SoftAssert();
		String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't matched as expected");

		String actualWarrantyMargin = policyCenter.atPolicyPage().getBaseMargin(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyMargin, "12,65", "Warranty Margin isn't matched as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] getCombinedVariables() {
		return new Object[][] {
				new Object[] {new EnergyTypes[]{HYBRID, HYBRIDPLUGIN, GNV}, "1,25"},
				new Object[] {new EnergyTypes[]{ELECTRIC}, "0,6" },
				new Object[] {new EnergyTypes[]{GPL, ESSENCE, DIESEL}, "1"}
		};
	}

	@Test(description = "EGVO France Alfa Romeo combined variable", dataProvider = "getCombinedVariables")
	public void test_combinedVariable(EnergyTypes[] energyTypes, String maintenanceCV) {
		basePage.selectEGVOCoverageType(EGVOMaintenanceARC4_50)
				.selectCoverageType(GVOARCertfied4_80)
				.selectRandomCar()
				.selectMileageLimitedAt(50000)
				.selectEnergyTypeFromThese(energyTypes)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
		dataFileName = "test_EGVOFranceARC_combinedVariable";

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

	@Test(description = "EGVO France Alfa Romeo other rating validations")
	public void test_otherMetrics() {
		basePage.selectEGVOCoverageType(EGVOMaintenanceARC4_50)
				.selectCoverageType(GVOARCertfied4_80)
				.selectRandomCar()
				.selectMileageLimitedAt(50000)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
		dataFileName = "test_EGVOFranceARC_otherMetrics";

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