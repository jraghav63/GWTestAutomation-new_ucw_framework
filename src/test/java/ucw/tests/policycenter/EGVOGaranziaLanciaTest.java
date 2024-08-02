package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Italy;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.LC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOGaranziaLanciaTest extends BaseTest {
	private PolicyCenter policyCenter;

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-1554: Estensione di garanzia Italy Lancia Certified Product");
		basePage.selectLabel(LC)
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
	public Object[][] getPrices() {
		return new String[][] {
				new String[] {GaranziaLancia4_80_24M,  GaranziaStandaloneLancia4_80,  "14,33"},
				new String[] {GaranziaLancia7_150_24M, GaranziaStandaloneLancia7_150, "30,98"},
		};
	}

	@Test(description = "Estensione di garanzia Standalone Lancia Certified prices", dataProvider = "getPrices")
	public void test_allRatingMetrics(String gvoType, String egvoType, String warrantyPrice) {
		basePage.selectEGVOCoverageType(egvoType)
				.selectCoverageType(gvoType)
				.selectRandomCar();
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

		String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
		softAssert.assertEquals(actualAssistancePrice, "1,48", "Assistance Premium price isn't matched as expected");

		String actualWarrantyCV = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
		softAssert.assertEquals(actualWarrantyCV, "1", "Warranty combined variable isn't matched as expected");

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