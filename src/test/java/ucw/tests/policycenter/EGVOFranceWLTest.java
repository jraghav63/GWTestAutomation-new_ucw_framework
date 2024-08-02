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
import static ucw.enums.Labels.WHITELABEL;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOFranceWLTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-1955: White Label Product - SA France");
		basePage.selectLabel(WHITELABEL)
				.selectCountry(France)
				.selectManagementType(INSURED)
				.selectWarrantyDuration(36)
				.selectMaintenanceDuration(36);
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
	public Object[][] pricePremiumOffers() {
		return new Object[][] {
				new Object[] {EGVOCare4_40,  4, 40000,  1,   100, "4,24",  "6,93"},
				new Object[] {EGVOCare4_60,  4, 60000,  101, 140, "9,12",  "4,26"},
				new Object[] {EGVOCare7_150, 7, 150000, 141, 300, "14,68", "1,45"},
				new Object[] {EGVOCare4_40,  4, 40000,  301, 400, "20,24", "4,12"},
				new Object[] {EGVOCare4_60,  4, 60000,  401, 999, "25,8",  "6,93"}
		};
	}

	@Test(description = "Warranty price, warranty margin and assistance price", dataProvider = "pricePremiumOffers")
	public void test_WarrantyAndAssistancePrices(String egvoType,
								  int vehicleAge, long maxMileage,
								  int minPower, int maxPower,
								  String warrantyPrice, String warrantyMargin) {
		basePage.selectCoverageType(GVOCarePremium7_150_12M)
				.selectEGVOCoverageType(egvoType)
				.selectRandomCar()
				.selectRegistrationDate(vehicleAge)
				.selectMileageLimitedAt(maxMileage)
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = egvoType.replace("/", "_");

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
		softAssert.assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualWarrantyMargin = policyCenter.atPolicyPage().getBaseMargin(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyMargin, warrantyMargin, "Warranty Margin isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "0,37", "Assistance Premium price isn't match as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] priceCarePlusOffers() {
		return new Object[][] {
				new Object[] {1,   100, "4,66",  "6,43"},
				new Object[] {101, 140, "10",    "3,3" },
				new Object[] {141, 300, "16,05", "3,3" },
				new Object[] {301, 400, "22,1",  "3,13"},
				new Object[] {401, 999, "28,15", "6,43"}
		};
	}

	@Test(description = "Care 10/200 prices", dataProvider = "priceCarePlusOffers")
	public void test_egvo_CarePlus(int minPower, int maxPower, String warrantyPrice, String warrantyMargin) {
		basePage.selectCoverageType(GVOCarePlus10_200)
				.selectEGVOCoverageType(EGVOCare10_200)
				.selectRandomCar()
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = EGVOCare10_200.replace("/", "_");

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
		softAssert.assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualWarrantyMargin = policyCenter.atPolicyPage().getBaseMargin(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyMargin, warrantyMargin, "Warranty Margin isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "0,37", "Assistance Premium price isn't match as expected");

		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] getMaintenancePrices4_40() {
		return new Object[][] {
				new Object[] {1,   100, 36, 10000, "17,61"},
				new Object[] {1,   100, 36, 20000, "30,11"},
				new Object[] {1,   100, 48, 25000, "50,11"},
				new Object[] {101, 140, 48, 15000, "18,95"},
				new Object[] {101, 140, 36, 20000, "33,95"},
				new Object[] {101, 140, 48, 25000, "46,45"},
				new Object[] {141, 999, 48, 15000, "20,74"},
				new Object[] {141, 999, 36, 20000, "34,07"},
				new Object[] {141, 999, 48, 25000, "50,74"},
				new Object[] {1,   100, 60, 10000, "22,61"},
				new Object[] {1,   100, 60, 20000, "35,94"},
				new Object[] {101, 140, 60, 15000, "24,79"},
				new Object[] {101, 140, 60, 20000, "40,62"},
				new Object[] {141, 999, 60, 10000, "28,24"},
				new Object[] {141, 999, 60, 20000, "40,74"},
		};
	}

	@Test(description = "Maintenance pure premium prices 4/40", dataProvider = "getMaintenancePrices4_40")
	public void test_MaintenancePremiumPrice4_40(int minPower, int maxPower, int duration, long mileage, String price) {
		basePage.selectCoverageType(GVOCarePremium7_150_24M)
				.selectEGVOCoverageType(EGVOCare4_40)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageLimitedAt(40000)
				.selectCarPowerInRange(minPower, maxPower)
				.selectWarrantyDuration(duration)
				.selectMaintenanceDuration(duration)
				.selectMaintenanceMileage(mileage);
		dataFileName = "maintenancePrice_" + EGVOCare4_40.replace("/", "_");

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

	@DataProvider
	public Object[][] getMaintenancePrices4_60() {
		return new Object[][] {
				new Object[] {1,   100, 36, 10000, "24,82"},
				new Object[] {1,   100, 36, 20000, "43,15"},
				new Object[] {1,   100, 48, 25000, "40,65"},
				new Object[] {101, 140, 48, 15000, "28,37"},
				new Object[] {101, 140, 36, 20000, "47.53"},
				new Object[] {101, 140, 48, 25000, "59.20"},
				new Object[] {141, 999, 48, 15000, "25.69"},
				new Object[] {141, 999, 36, 20000, "44.03"},
				new Object[] {141, 999, 48, 25000, "58.19"},
				new Object[] {1,   100, 60, 10000, "29.82"},
				new Object[] {1,   100, 60, 20000, "52.32"},
				new Object[] {101, 140, 60, 15000, "35.03"},
				new Object[] {101, 140, 60, 20000, "51.70"},
				new Object[] {141, 999, 60, 10000, "32.36"},
				new Object[] {141, 999, 60, 20000, "52.36"},
		};
	}

	@Test(description = "Maintenance pure premium prices 4/60", dataProvider = "getMaintenancePrices4_60")
	public void test_MaintenancePremiumPrice4_60(int minPower, int maxPower, int duration, long mileage, String price) {
		basePage.selectCoverageType(GVOCarePremium7_150_12M)
				.selectEGVOCoverageType(EGVOCare4_60)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageLimitedAt(60000)
				.selectCarPowerInRange(minPower, maxPower)
				.selectWarrantyDuration(duration)
				.selectMaintenanceDuration(duration)
				.selectMaintenanceMileage(mileage);
		dataFileName = "maintenancePrice_" + EGVOCare4_60.replace("/", "_");

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

	@DataProvider
	public Object[][] getCombinedVariables() {
		return new Object[][] {
				new Object[] {new EnergyTypes[]{HYBRID, HYBRIDPLUGIN, GNV}, "1,25"},
				new Object[] {new EnergyTypes[]{ELECTRIC}, "0,6" },
				new Object[] {new EnergyTypes[]{GPL, ESSENCE, DIESEL}, "1"}
		};
	}

	@Test(description = "Combined variable", dataProvider = "getCombinedVariables")
	public void test_combinedVariable(EnergyTypes energyType, String vehicleSpecific, String maintenanceCV) {
		basePage.selectCoverageType(GVOCarePremium7_150_24M)
				.selectEGVOCoverageType(EGVOCare7_150)
				.selectRandomCar()
				.selectEnergyType(energyType)
				.selectVehicleSpecific(vehicleSpecific);
		dataFileName = "test_EGVOFranceSpoticar_combinedVariable";

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

	@Test(description = "Other rating validations")
	public void test_otherMetrics() {
		basePage.selectCoverageType(GVOCarePlus10_200)
				.selectEGVOCoverageType(EGVOCare10_200)
				.selectRandomCar();
		dataFileName = "test_EGVOFranceWL_otherMetrics";

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
		softAssert.assertEquals(actualWarrantyCommissionJV, "0,35", "Warranty JV commission isn't matched as expected");

		String actualAssistanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(AssistanceJVCommission);
		softAssert.assertEquals(actualAssistanceCommissionJV, "0", "Assistance JV commission isn't matched as expected");

		String actualMaintenanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(MaintenanceJVCommission);
		softAssert.assertEquals(actualMaintenanceCommissionJV, "0", "Maintenance JV commission isn't matched as expected");

		String actualWarrantyTax = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
		softAssert.assertEquals(actualWarrantyTax, "0,18", "Warranty taxes isn't matched as expected");

		String actualAssistanceTax = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
		softAssert.assertEquals(actualAssistanceTax, "0,2", "Assistance taxes isn't matched as expected");

		String actualMaintenanceTax = policyCenter.atPolicyPage().getTaxes(MaintenanceTaxes);
		softAssert.assertEquals(actualMaintenanceTax, "0,2", "Maintenance taxes isn't matched as expected");

		softAssert.assertAll();
	}
}