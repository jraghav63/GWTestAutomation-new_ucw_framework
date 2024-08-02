package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Italy;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTerms.AssistanceTaxes;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.ARC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOGaranziaARCTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-1552: Estensione di garanzia Italy Alfa Romeo Product");
		basePage.selectLabel(ARC)
				.selectCountry(Italy)
				.selectManagementType(INSURED)
				.selectWarrantyDuration(40);
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
	public Object[][] get480Prices() {
		return new String[][] {
				new String[] {"Giulietta"           , "21,73"},
				new String[] {"Tonale"              , "27,9" },
				new String[] {"Giulia"              , "34,07"},
				new String[] {"Stelvio Quadrifoglio", "46,4" }
		};
	}

	@Test(description = "Estensione di garanzia Standalone Alfa Romeo 4/80 price", dataProvider = "get480Prices")
	public void test_basePrice4_80(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(GaranziaStandaloneARC4_80)
				.selectCoverageType(GaranziaARC4_80_24M)
				.selectRandomCar()
				.selectModel(model);
		dataFileName = GaranziaStandaloneARC4_80.replace("/","_") + "_" + model;

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
		assertEquals(actualAssistancePrice, "1,48", "Assistance Premium price isn't matched as expected");
	}

	@DataProvider
	public Object[][] get7150Prices() {
		return new Object[][] {
				new String[] {"Giulietta"        , "46,4" },
				new String[] {"Tonale"           , "63,06"},
				new String[] {"Giulia"           , "67,99"},
				new String[] {"Mito Quadrifoglio", "89,58"}
		};
	}

	@Test(description = "Estensione di garanzia Standalone Alfa Romeo 7/150 price", dataProvider = "get7150Prices")
	public void test_basePrice7_150(String model, String warrantyPrice) {
		basePage.selectEGVOCoverageType(GaranziaStandaloneARC7_150)
				.selectCoverageType(GaranziaARC7_150_12M)
				.selectRandomCar()
				.selectModel(model);
		dataFileName = GaranziaStandaloneARC7_150.replace("/","_") + "_" + model;

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
		assertEquals(actualAssistancePrice, "1,48", "Assistance Premium price isn't matched as expected");
	}

	@Test(description = "Estensione di garanzia Standalone Alfa Romeo Italy combined variable")
	public void test_combinedVariable() {
		basePage.selectCoverageType(GaranziaARC7_150_12M)
				.selectEGVOCoverageType(GaranziaStandaloneARC7_150)
				.selectRandomCar();
		dataFileName = "test_EGVOItalyARC_combinedVariable";

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
		assertEquals(actualWarrantyCV, "1", "Warranty combined variable isn't matched as expected");
	}

	@Test(description = "Estensione di garanzia Standalone Alfa Romeo Italy other rating validations")
	public void test_otherMetrics() {
		basePage.selectEGVOCoverageType(GaranziaStandaloneARC4_80)
				.selectCoverageType(GaranziaARC4_80_24M)
				.selectRandomCar();
		dataFileName = "test_EGVOItalyARC_otherMetrics";

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
		softAssert.assertEquals(actualWarrantyCommissionJV, "0,3", "Warranty JV commission isn't matched as expected");

		String actualAssistanceCommissionJV = policyCenter.atPolicyPage().getCommissionJV(AssistanceJVCommission);
		softAssert.assertEquals(actualAssistanceCommissionJV, "0", "Assistance JV commission isn't matched as expected");

		String actualWarrantyTax = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
		softAssert.assertEquals(actualWarrantyTax, "0,135", "Warranty taxes isn't matched as expected");

		String actualAssistanceTax = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
		softAssert.assertEquals(actualAssistanceTax, "0,1", "Assistance taxes isn't matched as expected");

		softAssert.assertAll();
	}
}