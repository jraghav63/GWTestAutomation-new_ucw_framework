package ucw.tests.policycenter;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ucw.enums.EnergyTypes;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Spain;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.EnergyTypes.*;
import static ucw.enums.EnergyTypes.DIESEL;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOSpainSpoticarTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWPOLICY-433: [R2.5-SPAIN][EGVO Standalone] Rating Spoticar");
		basePage.selectLabel(SPOTICAR)
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
	public Object[][] premiumPrices() {
		return new Object[][] {
				new Object[] {GVOPremium2_60,  EGVOMPremium2_40, 2, 40000,  1,   100, "3,85"},
				new Object[] {GVOPremium2_60,  EGVOMPremium2_60, 2, 60000,  101, 139, "9,06"},
				new Object[] {GVOPremium2_100, EGVOPremium2_100, 2, 100000, 140, 380, "15,66"},
				new Object[] {GVOPremium5_150, EGVOMPremium4_40, 4, 40000,  1,   100, "4,52"},
				new Object[] {GVOPremium5_150, EGVOMPremium4_60, 4, 60000,  101, 139, "10,62"},
				new Object[] {GVOPremium5_150, EGVOPremium5_150, 5, 150000, 140, 380, "18,37"}
		};
	}

	@Test(description = "EGVO SA Spain Spoticar Premium price", dataProvider = "premiumPrices")
	public void test_basePrices(String gvoType, String egvoType, int vehicleAge, long maxMileage, int minPower, int maxPower, String warrantyPrice) {
		basePage.selectCoverageType(gvoType)
				.selectEGVOCoverageType(egvoType)
				.selectRandomCar()
				.selectCarPowerInRange(minPower, maxPower)
				.selectMileageLimitedAt(maxMileage)
				.selectRegistrationDate(vehicleAge);
		dataFileName = egvoType.replace("/", "_") + "_" + minPower + "_" + maxPower;

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
		assertEquals(actualAssistancePrice, "0,37", "Assistance Premium price isn't match as expected");
	}

	@DataProvider
	public Object[][] getCombinedVariables() {
		return new Object[][] {
				new Object[] {new EnergyTypes[] {HYBRID, HYBRIDPLUGIN, GNV}, "1,25", "1,25"},
				new Object[] {new EnergyTypes[] {ELECTRIC},                  "1",    "0,6" },
				new Object[] {new EnergyTypes[] {GPL, ESSENCE, DIESEL},      "1",    "1"   },
		};
	}

	@Test(description = "EGVO SA Spain Spoticar combined variable", dataProvider = "getCombinedVariables")
	public void test_combinedVariable(EnergyTypes[] energyTypes, String warrantyCV, String maintenanceCV) {
		basePage.selectCoverageType(GVOPremium2_60)
				.selectEGVOCoverageType(EGVOMPremium2_60)
				.selectRandomCar()
				.selectEnergyTypeFromThese(energyTypes);
		dataFileName = "test_EGVOSpainSpoticar_combinedVariable";

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
		assertEquals(actualWarrantyCV, warrantyCV, "Warranty combined variable isn't match as expected");

		String actualMaintenanceCV = policyCenter.atPolicyPage().getCombinedVariable(MaintenancePremium);
		assertEquals(actualMaintenanceCV, maintenanceCV, "Maintenance combined variable isn't match as expected");
	}

	@Test(description = "EGVO SA Spain Spoticar other rating validations")
	public void test_otherMetrics() {
		basePage.selectCoverageType(GVOPremium5_150)
				.selectEGVOCoverageType(EGVOPremium5_150)
				.selectRandomCar();
		dataFileName = "test_EGVOSpainSpoticar_otherMetrics";

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
		softAssert.assertEquals(actualWarrantyManagementFee, "1,93", "Warranty Management Fee isn't matched as expected");

		String actualAssistanceManagementFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
		softAssert.assertEquals(actualAssistanceManagementFee, "0", "Assistance Management Fee isn't matched as expected");

		String actualWarrantyCommission = policyCenter.atPolicyPage().getCommissions(WarrantyCommission);
		softAssert.assertEquals(actualWarrantyCommission, "0", "Warranty commission isn't matched as expected");

		String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
		softAssert.assertEquals(actualAssistanceCommission, "0", "Assistance commission isn't matched as expected");

		String actualWarrantyCommissionJV = policyCenter.atPolicyPage().getCommissionJV(WarrantyJVCommission);
		softAssert.assertEquals(actualWarrantyCommissionJV, "0", "Warranty JV commission isn't matched as expected");

		String actualAssistanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(AssistanceJVCommission);
		softAssert.assertEquals(actualAssistanceCommissionJV, "0", "Assistance JV commission isn't matched as expected");

		String actualWarrantyTax = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
		softAssert.assertEquals(actualWarrantyTax, "0,0615", "Warranty taxes isn't matched as expected");

		String actualAssistanceTax = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
		softAssert.assertEquals(actualAssistanceTax, "0,0615", "Assistance taxes isn't matched as expected");

		softAssert.assertAll();
	}
}