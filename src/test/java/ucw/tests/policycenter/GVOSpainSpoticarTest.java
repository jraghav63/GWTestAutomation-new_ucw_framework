package ucw.tests.policycenter;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Spain;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTerms.AssistanceTaxes;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.CoverageTypes.GVOAdvanced10_150;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GVOSpainSpoticarTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-2803: GVO Spain - Spoticar and Certified - price increase 2024 January");
        basePage.selectLabel(SPOTICAR)
                .selectCountry(Spain);
        BillingCenter billingCenter = basePage.loginBillingCenter();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public static Object[][] pricesForInsured() {
        return new Object[][]{
                new Object[]{GVOPremium2_60,    1,   100, "93,69"},
                new Object[]{GVOPremium2_60,    101, 140, "135,3"},
                new Object[]{GVOPremium2_60,    141, 300, "224,99"},
                new Object[]{GVOPremium2_100,   1,   100, "120,51"},
                new Object[]{GVOPremium2_100,   101, 140, "172,29"},
                new Object[]{GVOPremium2_100,   141, 300, "308,21"},
                new Object[]{GVOPremium5_150,   1,   100, "162,12"},
                new Object[]{GVOPremium5_150,   101, 140, "245,34"},
                new Object[]{GVOPremium5_150,   141, 300, "445,98"},
                new Object[]{GVOPremium7_150,   1,   100, "304,51"},
                new Object[]{GVOPremium7_150,   101, 140, "421,94"},
                new Object[]{GVOPremium7_150,   141, 300, "717,83"},
                new Object[]{GVOPremium7_200,   1,   100, "362,77"},
                new Object[]{GVOPremium7_200,   101, 140, "481,12"},
                new Object[]{GVOPremium7_200,   141, 300, "776,08"},
                new Object[]{GVOAdvanced10_150, 1,   100, "318,38"},
                new Object[]{GVOAdvanced10_150, 101, 140, "422,87"},
                new Object[]{GVOAdvanced10_150, 141, 300, "684,54"},
                new Object[]{GVOAdvanced10_200, 1,   100, "381,26"},
                new Object[]{GVOAdvanced10_200, 101, 140, "548,62"},
                new Object[]{GVOAdvanced10_200, 141, 300, "947,14"},
        };
    }

    @Test(description = "Validate price value with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, int minCarPower, int maxCarPower, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Minimum car power is " + minCarPower
                + "<br>Maximum car power is " + maxCarPower
                + "<br>Warranty Premium price is " + warrantyPrice;

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
        assertEquals(actualAssistancePrice, "2,17", "Assistance Premium price isn't match as expected");
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
        assertEquals(actualAssistancePrice, "2,17", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public Object[][] getCombinedVariable() {
        return new String[][] {
                new String[] {INSURED,   "No 4X4", "1"   },
                new String[] {INSURED,   "4x4",    "1,25"},
                new String[] {DELEGATED, "No 4X4", "1"   },
                new String[] {DELEGATED, "4x4",    "1"   },
        };
    }

    @Test(description = "Warranty and Assistance Combined variable", dataProvider = "getCombinedVariable")
    public void test_combinedVariable(String managementType, String vehicleSpecific, String expectedAssistanceCV) {
        basePage.selectManagementType(managementType)
                .selectRandomCar()
                .selectVehicleSpecific(vehicleSpecific)
                .selectRandomCoverageType();
        dataSetInfo = basePage.getDataObject().getPolicyCenter().getCoverageType() + " with "
                + "<br>Management type is " + managementType
                + "<br>Vehicle specific is " + vehicleSpecific
                + "<br>Warranty Combined variable is 1"
                + "<br>Assistance Combined variable is 1";
        dataFileName = "Combined variable_" + managementType + "_" + vehicleSpecific;

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

        String actualWarrantyCV = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
        assertEquals(actualWarrantyCV, "1", "Warranty combined variable isn't match as expected");

        String actualAssistanceCV = policyCenter.atPolicyPage().getCombinedVariable(AssistancePremium);
        assertEquals(actualAssistanceCV, expectedAssistanceCV, "Assistance combined variable isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "28",    "0", "0,0815", "0", "1", "3", "0,0815"},
                new String[] {DELEGATED, "27,84", "0", "0,21",   "0", "1", "3", "0,0815"},
        };
    }

    @Test(description = "Spain Spoticar other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyCommissions,
                                              String warrantyTaxes,
                                              String assistanceManagementFee,
                                              String assistanceLossRatio,
                                              String assistanceCommissions,
                                              String assistanceTaxes) {
        basePage.selectRandomCoverageType()
                .selectRandomCar()
                .selectManagementType(managementType);
        dataSetInfo = basePage.getDataObject().getPolicyCenter().getCoverageType() + " with "
                + "<br>Management type as " + managementType
                + "<br>Warranty Management fee as " + warrantyManagementFee
                + "<br>Warranty commissions as " + warrantyCommissions
                + "<br>Warranty taxes as " + warrantyTaxes
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

        String actualAssistanceFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
        softAssert.assertEquals(actualAssistanceFee, assistanceManagementFee, "Assistance Management Fee isn't match as expected");

        String actualAssistanceLossRatio = policyCenter.atPolicyPage().getLossRatio(AssistancePremium);
        softAssert.assertEquals(actualAssistanceLossRatio, assistanceLossRatio, "Assistance Loss ratio isn't match as expected");

        String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
        softAssert.assertEquals(actualAssistanceCommission, assistanceCommissions, "Assistance Commissions isn't match as expected");

        String actualAssistanceTaxes = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
        softAssert.assertEquals(actualAssistanceTaxes, assistanceTaxes, "Assistance Taxes isn't match as expected");

        softAssert.assertAll();
    }
}