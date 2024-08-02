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
import static ucw.enums.Countries.Germany;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.*;
import static ucw.enums.VehicleTypes.LIGHTTRUCK;
import static ucw.enums.VehicleTypes.PASSENGERCAR;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCWAR-141 and UCWAR-2653
public class GWGarantieSpoticarTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-141: Germany-GW Garantie SPOTICAR Product Deployment");
        basePage.selectLabel(SPOTICAR)
                .selectCountry(Germany);
        BillingCenter billingCenter = basePage.loginBillingCenter();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public static Object[][] getCoverageTypes() {
        return new Object[][] {
                new Object[] {GWSpoticarPremium1_12M, 0, 59, "97,81", "1"},
                new Object[] {GWSpoticarPremium1_12M, 60, 81, "131,09", "1"},
                new Object[] {GWSpoticarPremium1_12M, 82, 110, "165,38", "1"},
                new Object[] {GWSpoticarPremium1_12M, 111, 154, "229,92", "1"},
                new Object[] {GWSpoticarPremium1_12M, 155, 300, "330,76", "1"},
                new Object[] {GWSpoticarPremium1_24M, 0, 59, "97,81", "2"},
                new Object[] {GWSpoticarPremium1_24M, 60, 81, "131,09", "2"},
                new Object[] {GWSpoticarPremium1_24M, 82, 110, "165,38", "2"},
                new Object[] {GWSpoticarPremium1_24M, 111, 154, "229,91", "2"},
                new Object[] {GWSpoticarPremium1_24M, 155, 300, "330,75", "2"},
                new Object[] {GWSpoticarPremium2_12M, 0, 59, "145,38", "1"},
                new Object[] {GWSpoticarPremium2_12M, 60, 81, "182,35", "1"},
                new Object[] {GWSpoticarPremium2_12M, 82, 110, "255,46", "1"},
                new Object[] {GWSpoticarPremium2_12M, 111, 154, "365,55", "1"},
                new Object[] {GWSpoticarPremium2_12M, 155, 300, "547,9", "1"},
                new Object[] {GWSpoticarPremium2_24M, 0, 59, "132,35", "2"},
                new Object[] {GWSpoticarPremium2_24M, 60, 81, "168,9", "2"},
                new Object[] {GWSpoticarPremium2_24M, 82, 110, "228,57", "2"},
                new Object[] {GWSpoticarPremium2_24M, 111, 154, "320,17", "2"},
                new Object[] {GWSpoticarPremium2_24M, 155, 300, "457,14", "2"},
                new Object[] {GWSpoticarAdvanced12M, 0, 59, "182,35", "1"},
                new Object[] {GWSpoticarAdvanced12M, 60, 81, "210,08", "1"},
                new Object[] {GWSpoticarAdvanced12M, 82, 110, "297,48", "1"},
                new Object[] {GWSpoticarAdvanced12M, 111, 154, "393,28", "1"},
                new Object[] {GWSpoticarAdvanced12M, 155, 300, "576,47", "1"},
                new Object[] {GWSpoticarAdvanced24M, 0, 59, "150,84", "2"},
                new Object[] {GWSpoticarAdvanced24M, 60, 81, "182,77", "2"},
                new Object[] {GWSpoticarAdvanced24M, 82, 110, "249,58", "2"},
                new Object[] {GWSpoticarAdvanced24M, 111, 154, "334,03", "2"},
                new Object[] {GWSpoticarAdvanced24M, 155, 300, "501,68", "2"},
        };
    }

    @Test(description = "GW Garantie price and duration coefficient", dataProvider = "getCoverageTypes")
    public void test_price(String coverageType,
                                int minPower, int maxPower,
                                String expectedWarrantyPrice,
                                String durationCoefficient) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minPower
                + "<br>Maximum car power is " + maxPower
                + "<br>Warranty premium price is " + expectedWarrantyPrice
                + "<br>Assistance premium price is 0"
                + "<br>Duration coefficient is " + durationCoefficient;
        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectCarPowerInRange(minPower, maxPower);

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().clickQuoteTab();
        policyCenter.atPolicyPage().goToRatingWorksheet();
        policyCenter.atPolicyPage().expandAllNodes();

        SoftAssert softAssert = new SoftAssert();
        String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
        softAssert.assertEquals(actualWarrantyPrice, expectedWarrantyPrice, "Warranty Premium price isn't match as expected");

        String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
        softAssert.assertEquals(actualAssistancePrice, "0", "Assistance Premium price isn't match as expected");

        String actualWarrantyDuration = policyCenter.atPolicyPage().getDurationCoefficient(WarrantyPremium);
        softAssert.assertEquals(actualWarrantyDuration, durationCoefficient, "Duration Coefficient isn't match as expected");

        String actualAssistanceDuration = policyCenter.atPolicyPage().getDurationCoefficient(AssistancePremium);
        softAssert.assertEquals(actualAssistanceDuration, durationCoefficient, "Duration Coefficient isn't match as expected");

        softAssert.assertAll();
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "0",  "0", "0,19", "1", "0", "0,03", "0,19", "1"},
                new String[] {DELEGATED, "30", "0", "0,19", "1", "0", "0",    "0,19", "1"},
        };
    }

    @Test(description = "GW Garantie other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyCommissions,
                                              String warrantyTaxes,
                                              String warrantyLossRatio,
                                              String assistanceManagementFee,
                                              String assistanceCommissions,
                                              String assistanceTaxes,
                                              String assistanceLossRatio) {
        basePage.selectRandomCoverageType()
                .selectRandomCar()
                .selectManagementType(managementType);
        dataSetInfo = basePage.getDataObject().getPolicyCenter().getCoverageType() + " with "
                + "<br>Management type as " + managementType
                + "<br>Warranty Management fee as " + warrantyManagementFee
                + "<br>Warranty commissions as " + warrantyCommissions
                + "<br>Warranty taxes as " + warrantyTaxes
                + "<br>Warranty loss ratio as " + warrantyLossRatio
                + "<br>Assistance management fee as " + assistanceManagementFee
                + "<br>Assistance loss ratio as " + assistanceLossRatio
                + "<br>Assistance commissions as " + assistanceCommissions
                + "<br>Assistance taxes as " + assistanceTaxes;

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().clickQuoteTab();
        policyCenter.atPolicyPage().goToRatingWorksheet();
        policyCenter.atPolicyPage().expandAllNodes();

        SoftAssert softAssert = new SoftAssert();
        String actualWarrantyFee = policyCenter.atPolicyPage().getManagementFee(WarrantyManagementFee);
        softAssert.assertEquals(actualWarrantyFee, warrantyManagementFee, "Warranty Management Fee isn't match as expected");

        String actualWarrantyCommission = policyCenter.atPolicyPage().getCommissions(WarrantyCommission);
        softAssert.assertEquals(actualWarrantyCommission, warrantyCommissions, "Warranty Commissions isn't match as expected");

        String actualWarrantyTaxes = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
        softAssert.assertEquals(actualWarrantyTaxes, warrantyTaxes, "Warranty Taxes isn't match as expected");

        String actualWarrantyLossRatio = policyCenter.atPolicyPage().getLossRatio(WarrantyPremium);
        softAssert.assertEquals(actualWarrantyLossRatio, warrantyLossRatio, "Warranty Loss ratio isn't match as expected");

        String actualAssistanceFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
        softAssert.assertEquals(actualAssistanceFee, assistanceManagementFee, "Assistance Management Fee isn't match as expected");

        String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
        softAssert.assertEquals(actualAssistanceCommission, assistanceCommissions, "Assistance Commissions isn't match as expected");

        String actualAssistanceTaxes = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
        softAssert.assertEquals(actualAssistanceTaxes, assistanceTaxes, "Assistance Taxes isn't match as expected");

        String actualAssistanceLossRatio = policyCenter.atPolicyPage().getLossRatio(AssistancePremium);
        softAssert.assertEquals(actualAssistanceLossRatio, assistanceLossRatio, "Assistance Loss ratio isn't match as expected");

        softAssert.assertAll();
    }

    @DataProvider
    public static Object[][] getVehicleTypes() {
        return new String[][] {
                new String[] {INSURED,   LIGHTTRUCK,   "1,41"},
                new String[] {INSURED,   PASSENGERCAR, "1"},
                new String[] {DELEGATED, LIGHTTRUCK,   "1"},
                new String[] {DELEGATED, PASSENGERCAR, "1"},
        };
    }

    @Test(description = "GW Garantie combined variable validation with different management types", dataProvider = "getVehicleTypes")
    public void test_combinedVariable(String managementType, String vehicleType, String warrantyCV) {
        dataSetInfo = basePage.getDataObject().getPolicyCenter().getCoverageType() + " with "
                + "<br>Management type as " + managementType
                + "<br>Vehicle type as " + vehicleType
                + "<br>Warranty Combined variable as " + warrantyCV
                + "<br>Assistance Combined variable as 1";

        basePage.selectRandomCoverageType()
                .selectRandomCar()
                .selectManagementType(managementType)
                .selectVehicleType(vehicleType);

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().clickQuoteTab();
        policyCenter.atPolicyPage().goToRatingWorksheet();
        policyCenter.atPolicyPage().expandAllNodes();
        String warrantyCombinedVariable = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
        assertEquals(warrantyCombinedVariable, warrantyCV, "Warranty Combined variable isn't match as expected");
        String assistanceCombinedVariable = policyCenter.atPolicyPage().getCombinedVariable(AssistancePremium);
        assertEquals(assistanceCombinedVariable, "1", "Assistance Combined variable isn't match as expected");
    }
}
