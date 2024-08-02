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
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOFranceSpoticarTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-3139: EGVO SA Spoticar France - price update - 1June2024");
		basePage.selectLabel(SPOTICAR)
				.selectCountry(France)
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
	public Object[][] price4Years() {
		return new Object[][] {
				new Object[] {1,   100, "19,87"},
				new Object[] {101, 140, "24,11"},
				new Object[] {141, 300, "36,82"},
				new Object[] {301, 400, "41,06"},
				new Object[] {401, 510, "45,3" }
		};
	}

	@Test(description = "EGVO Premium coverage 4 years and 40k mileage", dataProvider = "price4Years")
	public void test_premium4Years40k(int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_40)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(10000, 40000)
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = "test_EGVOFranceSpoticar_4years40k";

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
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "0,93", "Assistance Premium price isn't match as expected");
	}

	@DataProvider
	public Object[][] getMaintenancePrices4_40() {
		return new Object[][] {
				new Object[] {1,   100, 36, 15000, "35"   },
				new Object[] {1,   100, 36, 20000, "38,34"},
				new Object[] {1,   100, 48, 25000, "41,66"},
				new Object[] {101, 140, 48, 15000, "37,09"},
				new Object[] {101, 140, 36, 20000, "41,67"},
				new Object[] {101, 140, 48, 25000, "45"   },
				new Object[] {141, 300, 48, 15000, "38,33"},
				new Object[] {141, 300, 36, 20000, "44,17"},
				new Object[] {141, 300, 48, 25000, "47,5" },
				new Object[] {1,   100, 60, 15000, "39,16"},
				new Object[] {1,   100, 60, 20000, "40,84"},
				new Object[] {1,   100, 60, 25000, "0"    },
				new Object[] {101, 140, 60, 15000, "41,66"},
				new Object[] {101, 140, 60, 20000, "43,34"},
				new Object[] {101, 140, 60, 25000, "0"    },
				new Object[] {141, 300, 60, 15000, "44,16"},
				new Object[] {141, 300, 60, 20000, "45,84"},
				new Object[] {141, 300, 60, 25000, "0"    }
		};
	}

	@Test(description = "Maintenance pure premium prices 4/40", dataProvider = "getMaintenancePrices4_40")
	public void test_MaintenancePremiumPrice4_40(int minPower, int maxPower, int duration, long mileage, String price) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_40)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(10000, 40000)
				.selectCarPowerInRange(minPower, maxPower)
				.selectWarrantyDuration(duration)
				.selectMaintenanceDuration(duration)
				.selectMaintenanceMileage(mileage);
		dataFileName = "maintenancePrice_" + EGVOSpoticarPremium4_40.replace("/", "_");

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

	@Test(description = "EGVO Premium coverage 4 years and 60k mileage", dataProvider = "price4Years")
	public void test_premium4Years60k(int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_60)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(40001, 60000)
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = "test_EGVOFranceSpoticar_4years60k";

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
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "0,93", "Assistance Premium price isn't match as expected");
	}

	@DataProvider
	public Object[][] getMaintenancePrices4_60() {
		return new Object[][] {
				new Object[] {1,   100, 36, 15000, "39,17"},
				new Object[] {1,   100, 36, 20000, "41,67"},
				new Object[] {1,   100, 48, 25000, "45,83"},
				new Object[] {101, 140, 48, 15000, "40"   },
				new Object[] {101, 140, 36, 20000, "45,84"},
				new Object[] {101, 140, 48, 25000, "50"   },
				new Object[] {141, 300, 48, 15000, "42,5" },
				new Object[] {141, 300, 36, 20000, "48,34"},
				new Object[] {141, 300, 48, 25000, "52,5" },
				new Object[] {1,   100, 60, 15000, "42,5" },
				new Object[] {1,   100, 60, 20000, "45"   },
				new Object[] {1,   100, 60, 25000, "0"   },
				new Object[] {101, 140, 60, 15000, "45,83"},
				new Object[] {101, 140, 60, 20000, "48,34"},
				new Object[] {101, 140, 60, 25000, "0"},
				new Object[] {141, 300, 60, 15000, "49,16"},
				new Object[] {141, 300, 60, 20000, "50,83"},
				new Object[] {141, 300, 60, 25000, "0"}
		};
	}

	@Test(description = "Maintenance pure premium prices 4/60", dataProvider = "getMaintenancePrices4_60")
	public void test_MaintenancePremiumPrice4_60(int minPower, int maxPower, int duration, long mileage, String price) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_60)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(40001, 60000)
				.selectCarPowerInRange(minPower, maxPower)
				.selectWarrantyDuration(duration)
				.selectMaintenanceDuration(duration)
				.selectMaintenanceMileage(mileage);
		dataFileName = "maintenancePrice_" + EGVOSpoticarPremium4_60.replace("/", "_");

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
	public Object[][] price4to7Years() {
		return new Object[][] {
				new Object[] {1,   100, "19,86"},
				new Object[] {101, 140, "24,09"},
				new Object[] {141, 300, "36,8"},
				new Object[] {301, 400, "41,04"},
				new Object[] {401, 510, "45,27"}
		};
	}

	@Test(description = "EGVO Premium coverage 4 years and 150k mileage", dataProvider = "price4to7Years")
	public void test_premium4Years150k(int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium7_150)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(60001, 150000)
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = "test_EGVOFranceSpoticar_4years150k";

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
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "0,95", "Assistance Premium price isn't match as expected");
	}

	@Test(description = "EGVO Premium from 4 to 7 years", dataProvider = "price4to7Years")
	public void test_premium4to7Years(int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium7_150)
				.selectRandomCar()
				.selectRegistrationDateInRange(5, 7)
				.selectMileageLimitedAt(150000)
				.selectCarPowerInRange(minPower, maxPower);
		dataFileName = "test_EGVOFranceSpoticar_4to7years";

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
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "0,95", "Assistance Premium price isn't match as expected");
	}

	@DataProvider
	public Object[][] price10Years() {
		return new Object[][] {
				new Object[] {1,   100, "24"},
				new Object[] {101, 140, "28,24"},
				new Object[] {141, 300, "40,95"},
				new Object[] {301, 400, "45,19"},
				new Object[] {401, 510, "50,27"}
		};
	}

	@Test(description = "EGVO Advanced 10 years", dataProvider = "price10Years")
	public void test_premium10Years(int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(GVOAdvanced10_200)
				.selectEGVOCoverageType(EGVOAdvanced10_200)
				.selectRandomCar()
				.selectRegistrationDate(8)
				.selectMileageLimitedAt(200000)
				.selectCarPowerInRange(minPower, maxPower)
				.selectWarrantyDuration(24);
		dataFileName = "test_EGVOFranceSpoticar_10years";

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
		assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		assertEquals(actualAssistancePrice, "1,04", "Assistance Premium price isn't match as expected");
	}

	@DataProvider
	public Object[][] getCombinedVariables() {
		return new Object[][] {
				new Object[] {new EnergyTypes[]{HYBRID, HYBRIDPLUGIN, GNV}, "1,25"},
				new Object[] {new EnergyTypes[]{ELECTRIC}, "0,6" },
				new Object[] {new EnergyTypes[]{GPL, ESSENCE, DIESEL}, "1"}
		};
	}

	@Test(description = "EGVO SA France Spoticar combined variable", dataProvider = "getCombinedVariables")
	public void test_combinedVariable(EnergyTypes[] energyTypes, String maintenanceCV) {
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_60)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(40001, 60000)
				.selectEnergyTypeFromThese(energyTypes)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);
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
		basePage.selectCoverageType(GVOPremium7_150)
				.selectEGVOCoverageType(EGVOSpoticarPremium4_40)
				.selectRandomCar()
				.selectRegistrationDate(4)
				.selectMileageInRange(10000, 40000)
				.selectWarrantyDuration(40)
				.selectMaintenanceDuration(40);;
		dataFileName = "test_EGVOFranceSpoticar_otherMetrics";

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
		softAssert.assertEquals(actualAssistanceTax, "0,2", "Assistance taxes isn't matched as expected");

		String actualMaintenanceTax = policyCenter.atPolicyPage().getTaxes(MaintenanceTaxes);
		softAssert.assertEquals(actualMaintenanceTax, "0,2", "Maintenance taxes isn't matched as expected");

		softAssert.assertAll();
	}
}