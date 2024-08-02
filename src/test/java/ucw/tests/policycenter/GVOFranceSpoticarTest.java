package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.*;
import static ucw.enums.Countries.France;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.*;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class GVOFranceSpoticarTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-3088: New segmentation for pricing - France DW and EW");
        basePage.selectLabel(SPOTICAR)
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
    public static Object[][] getCRSPrices() {
        return new String[][]{
                new String[]{GVOPremium2_60,        "13" },
                new String[]{GVOPremium5_100_12,    "6,5"},
                new String[]{GVOPremium5_100_24,    "13" },
                new String[]{GVOPremium7_150_12,    "6,5"},
                new String[]{GVOPremium7_150_24,    "13"},
                new String[]{GVOAdvanced10_200_8,   "6,5"},
                new String[]{GVOAdvanced10_200_12,  "6,5"},
                new String[]{GVOEssential15_200_6,  "6,5"},
                new String[]{GVOEssential15_200_12, "6,5"}
        };
    }

    @Test(description = "CRS availability", dataProvider = "getCRSPrices")
    public void test_CRS(String coverageType, String crsPrice) {
        dataSetInfo = coverageType + " with"
                + "<br>CRS price as " + crsPrice
                + "<br>CRS taxes as 0,2";
        basePage.selectCoverageType(coverageType)
                .selectRandomCar();

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

        String actualCRSPrice = policyCenter.atPolicyPage().getClientRelationServicePrice();
        assertEquals(actualCRSPrice, crsPrice, "Client Relation Service price isn't match with expected");

        String actualCRSTaxes = policyCenter.atPolicyPage().getClientRelationServiceTaxes();
        assertEquals(actualCRSTaxes, "0,2", "Client Relation Service taxes isn't match with expected");
    }

    @DataProvider
    public static Object[][] prices2_60() {
        return new Object[][]{
                new Object[]{GVOPremium2_60, 1,   90,  "110,18"},
                new Object[]{GVOPremium2_60, 91,  109, "160,38"},
                new Object[]{GVOPremium2_60, 110, 139, "204,43"},
                new Object[]{GVOPremium2_60, 140, 199, "229,07"},
                new Object[]{GVOPremium2_60, 200, 510, "253,71"},
        };
    }

    @Test(description = "Pure premium price for GVO Premium 2/60" , dataProvider = "prices2_60")
    public void test_price2_60(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] prices5_100() {
        return new Object[][]{
                new Object[]{GVOPremium5_100_12, 1,   90,  "229,07"},
                new Object[]{GVOPremium5_100_12, 91,  109, "253,72"},
                new Object[]{GVOPremium5_100_12, 110, 139, "155,21"},
                new Object[]{GVOPremium5_100_12, 140, 199, "214,15"},
                new Object[]{GVOPremium5_100_12, 200, 510, "360,59"},
                new Object[]{GVOPremium5_100_24, 1,   90,  "400,84"},
                new Object[]{GVOPremium5_100_24, 91,  109, "441,11"},
                new Object[]{GVOPremium5_100_24, 110, 139, "170,28"},
                new Object[]{GVOPremium5_100_24, 140, 199, "233,72"},
                new Object[]{GVOPremium5_100_24, 200, 510, "391,36"},
        };
    }

    @Test(description = "Pure premium price for GVO Premium 5/100 12 and 24 months" , dataProvider = "prices5_100")
    public void test_price5_100(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] prices7_150() {
        return new Object[][]{
                new Object[]{GVOPremium7_150_12, 1,   90,  "155,21"},
                new Object[]{GVOPremium7_150_12, 91,  109, "214,15"},
                new Object[]{GVOPremium7_150_12, 110, 139, "360,59"},
                new Object[]{GVOPremium7_150_12, 140, 199, "400,84"},
                new Object[]{GVOPremium7_150_12, 200, 510, "441,11"},
                new Object[]{GVOPremium7_150_24, 1,   90,  "170,28"},
                new Object[]{GVOPremium7_150_24, 91,  109, "233,72"},
                new Object[]{GVOPremium7_150_24, 110, 139, "391,36"},
                new Object[]{GVOPremium7_150_24, 140, 199, "434,68"},
                new Object[]{GVOPremium7_150_24, 200, 510, "478,03"},
        };
    }

    @Test(description = "Pure premium price for GVO Premium 7/150 12 and 24 months" , dataProvider = "prices7_150")
    public void test_price7_150(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] prices10_200() {
        return new Object[][]{
                new Object[]{GVOAdvanced10_200_8,  1,   90,  "171,85"},
                new Object[]{GVOAdvanced10_200_8,  91,  109, "241,85"},
                new Object[]{GVOAdvanced10_200_8,  110, 139, "381,39"},
                new Object[]{GVOAdvanced10_200_8,  140, 199, "423,69"},
                new Object[]{GVOAdvanced10_200_8,  200, 510, "466,07"},
                new Object[]{GVOAdvanced10_200_12, 1,   90,  "171,85"},
                new Object[]{GVOAdvanced10_200_12, 91,  109, "241,85"},
                new Object[]{GVOAdvanced10_200_12, 110, 139, "381,39"},
                new Object[]{GVOAdvanced10_200_12, 140, 199, "423,69"},
                new Object[]{GVOAdvanced10_200_12, 200, 510, "466,07"},
        };
    }

    @Test(description = "Pure premium price for GVO Advanced 10/200 8 and 12 months" , dataProvider = "prices10_200")
    public void test_price10_200(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] prices15_200() {
        return new Object[][]{
                new Object[]{GVOEssential15_200_6,  1,   90,  "173,04"},
                new Object[]{GVOEssential15_200_6,  91,  109, "313,69"},
                new Object[]{GVOEssential15_200_6,  110, 139, "430,75"},
                new Object[]{GVOEssential15_200_6,  140, 199, "478"   },
                new Object[]{GVOEssential15_200_6,  200, 510, "525,34"},
                new Object[]{GVOEssential15_200_12, 1,   90,  "173,04"},
                new Object[]{GVOEssential15_200_12, 91,  109, "313,69"},
                new Object[]{GVOEssential15_200_12, 110, 139, "430,75"},
                new Object[]{GVOEssential15_200_12, 140, 199, "478"   },
                new Object[]{GVOEssential15_200_12, 200, 510, "525,34"},
        };
    }

    @Test(description = "Pure premium price for GVO Essential 15/200 6 and 12 months" , dataProvider = "prices15_200")
    public void test_price15_200(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Expected Warranty price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Validate the Rating value for Spoticar offerings with management type Delegated")
    public void test_price_labelDelegated() {
        basePage.selectManagementType(DELEGATED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Validate the Rating value for Spoticar offerings with management type Self-Managed")
    public void test_price_labelSelfManaged() {
        basePage.selectManagementType(SELFMANAGED)
                .selectRandomCar()
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
        assertEquals(actualAssistancePrice, "14,48", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "42", "0,09"},
                new String[] {DELEGATED, "36", "0,2" },
        };
    }

    @Test(description = "France Spoticar other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyTaxes) {
        basePage.selectManagementType(managementType)
                .selectRandomCar()
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
