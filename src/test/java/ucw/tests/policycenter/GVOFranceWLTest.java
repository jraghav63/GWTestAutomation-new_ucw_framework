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
import static ucw.enums.Countries.France;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.WHITELABEL;
import static ucw.enums.ManagementType.*;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCWAR-1959
public class GVOFranceWLTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1959: White Label Product - IMM France");
        basePage.selectLabel(WHITELABEL)
                .selectCountry(France);
        BillingCenter billingCenter = basePage.loginBillingCenter();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public static Object[][] getOfferDescription() {
        return new Object[][]{
                new Object[]{GVOCarePremium2_60,      2,  100,    60000,  "Care Premium", "3"},
                new Object[]{GVOCarePremium7_150_12M, 7,  100,    150000, "Care Premium", "3"},
                new Object[]{GVOCarePremium7_150_24M, 7,  100,    150000, "Care Premium", "3"},
                new Object[]{GVOCarePlus10_200,       7,  80000,  200000, "Care Plus"   , "2"},
                new Object[]{GVOCarePlus10_200,       10, 100,    200000, "Care Plus"   , "2"},
                new Object[]{GVOCare15_200,           10, 120000, 200000, "Care"        , "1"},
        };
    }

    @Test(description = "UCWAR-3120: GVO White Label France offer description" , dataProvider = "getOfferDescription")
    public void test_checkOfferDescription(String coverageType, int vehicleAge, long minMileage, long maxMileage, String expectedOfferLevel, String waitingPeriod) {
        basePage.selectRandomCar()
                .selectManagementType(INSURED)
                .selectCoverageType(coverageType)
                .selectRegistrationDate(vehicleAge)
                .selectMileageInRange(minMileage, maxMileage);

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().clickCoverageTab();
        policyCenter.atPolicyPage().selectType();

        SoftAssert softAssert = new SoftAssert();

        String actualOfferLabel = policyCenter.atPolicyPage().getCurrentOfferLabel();
        softAssert.assertEquals(actualOfferLabel, "Garantie Panne Auto", "Actual offer label isn't match as expected");

        String actualOfferLevel = policyCenter.atPolicyPage().getCurrentOfferLevel();
        softAssert.assertEquals(actualOfferLevel, expectedOfferLevel, "Actual offer level isn't match as expected");

        String actualWaitingPeriod = policyCenter.atPolicyPage().getWarrantyWaitingPeriod(false);
        softAssert.assertEquals(actualWaitingPeriod, waitingPeriod, "Actual waiting period isn't match as expected");

        softAssert.assertAll();
    }

    @DataProvider
    public static Object[][] pricesForInsured() {
        return new Object[][]{
                new Object[]{GVOCarePremium2_60,      1,   100, "117,77" },
                new Object[]{GVOCarePremium2_60,      101, 140, "170,47"},
                new Object[]{GVOCarePremium2_60,      141, 300, "216,76"},
                new Object[]{GVOCarePremium2_60,      301, 400, "242,63"},
                new Object[]{GVOCarePremium2_60,      401, 510, "268,5"},
                new Object[]{GVOCarePremium7_150_12M, 1,   100, "174,97"},
                new Object[]{GVOCarePremium7_150_12M, 101, 140, "239,74"},
                new Object[]{GVOCarePremium7_150_12M, 141, 300, "400,84"},
                new Object[]{GVOCarePremium7_150_12M, 301, 400, "445,16"},
                new Object[]{GVOCarePremium7_150_12M, 401, 510, "489,38"},
                new Object[]{GVOCarePremium7_150_24M, 1,   100, "191,25"},
                new Object[]{GVOCarePremium7_150_24M, 101, 140, "260,89"},
                new Object[]{GVOCarePremium7_150_24M, 141, 300, "434,05"},
                new Object[]{GVOCarePremium7_150_24M, 301, 400, "481,71"},
                new Object[]{GVOCarePremium7_150_24M, 401, 510, "529,24"},
                new Object[]{GVOCarePlus10_200,       1,   100, "95,98"},
                new Object[]{GVOCarePlus10_200,       101, 140, "141,21"},
                new Object[]{GVOCarePlus10_200,       141, 300, "231,3"},
                new Object[]{GVOCarePlus10_200,       301, 400, "258,55"},
                new Object[]{GVOCarePlus10_200,       401, 510, "285,89"},
                new Object[]{GVOCare15_200,           1,   100, "61,58"},
                new Object[]{GVOCare15_200,           101, 140, "129,28"},
                new Object[]{GVOCare15_200,           141, 300, "185,71" },
                new Object[]{GVOCare15_200,           301, 400, "208,46" },
                new Object[]{GVOCare15_200,           401, 510, "231,21"},
        };
    }

    @Test(description = "Base price with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;
        basePage.selectRandomCar()
                .selectManagementType(INSURED)
                .selectCoverageType(coverageType)
                .selectCarPowerInRange(minCarPower, maxCarPower);
        dataFileName = coverageType.replace("/","_") + "_Insured";

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
        String actualWarrantyPrice = policyCenter.atPolicyPage().getPrice(WarrantyPremium);
        assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price isn't match as expected");

        String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
        assertEquals(actualAssistancePrice, "15,72", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Base price with management type Delegated")
    public void test_price_labelDelegated() {
        basePage.selectRandomCar()
                .selectManagementType(DELEGATED)
                .selectCoverageType(GVOCarePremium7_150_24M);

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().clickCoverageTab();
        policyCenter.atPolicyPage().selectType();

        SoftAssert softAssert = new SoftAssert();

        String actualWaitingPeriod = policyCenter.atPolicyPage().getWarrantyWaitingPeriod(false);
        softAssert.assertEquals(actualWaitingPeriod, "0", "Actual waiting period isn't match as expected");

        policyCenter.atPolicyPage().goToReview();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().clickQuoteTab();
        policyCenter.atPolicyPage().goToRatingWorksheet();
        policyCenter.atPolicyPage().expandAllNodes();

        String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
        softAssert.assertEquals(actualAssistancePrice, "15,72", "Assistance Premium price isn't match as expected");

        softAssert.assertAll();
    }

    @Test(description = "Base price with management type Self-Managed")
    public void test_price_labelSelfManaged() {
        basePage.selectRandomCar()
                .selectManagementType(SELFMANAGED)
                .selectRandomCoverageType();

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

        String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
        assertEquals(actualAssistancePrice, "0", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "42", "0,09"},
                new String[] {DELEGATED, "36", "0,2" },
        };
    }

    @Test(description = "Other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyTaxes) {
        basePage.selectRandomCar()
                .selectManagementType(managementType)
                .selectRandomCoverageType();
        dataSetInfo = "Validate following metrics of coverage type "
                + basePage.getDataObject().getPolicyCenter().getCoverageType()
                + " with Management type as " + managementType + " if"
                + "<br>Warranty Management fee as " + warrantyManagementFee
                + "<br>Warranty commissions as 0"
                + "<br>Warranty taxes as " + warrantyTaxes
                + "<br>Warranty Combined variable as 1"
                + "<br>Assistance management fee as 0"
                + "<br>Assistance loss ratio as 1"
                + "<br>Assistance commissions as 0"
                + "<br>Assistance taxes as 0,2"
                + "<br>Assistance Combined variable as 1";

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
        softAssert.assertEquals(actualWarrantyCommission, "0", "Warranty Commissions isn't match as expected");

        String actualWarrantyTaxes = policyCenter.atPolicyPage().getTaxes(WarrantyTaxes);
        softAssert.assertEquals(actualWarrantyTaxes, warrantyTaxes, "Warranty Taxes isn't match as expected");

        String actualWarrantyCV = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
        softAssert.assertEquals(actualWarrantyCV, "1", "Warranty combined variable isn't match as expected");

        String actualAssistanceFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
        softAssert.assertEquals(actualAssistanceFee, "0", "Assistance Management Fee isn't match as expected");

        String actualAssistanceLossRatio = policyCenter.atPolicyPage().getLossRatio(AssistancePremium);
        softAssert.assertEquals(actualAssistanceLossRatio, "1", "Assistance Loss ratio isn't match as expected");

        String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
        softAssert.assertEquals(actualAssistanceCommission, "0", "Assistance Commissions isn't match as expected");

        String actualAssistanceTaxes = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
        softAssert.assertEquals(actualAssistanceTaxes, "0,2", "Assistance Taxes isn't match as expected");

        String actualCombinedVariable = policyCenter.atPolicyPage().getCombinedVariable(AssistancePremium);
        softAssert.assertEquals(actualCombinedVariable, "1", "Assistance combined variable isn't match as expected");

        softAssert.assertAll();
    }
}
