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
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.LC;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GaranziaLanciaTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-587: GVO Italy - Lancia Certified Evolution-Virtual Product & Rate book");
        basePage.selectLabel(LC)
                .selectCountry(Italy);
        BillingCenter billingCenter = basePage.loginBillingCenter();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public static Object[][] getLanciaCoverageTypes() {
        return new Object[][] {
                new Object[] {GaranziaLancia4_80_24M,  4, 10000, 80000},
                new Object[] {GaranziaLancia7_150_12M, 4, 80000, 150000},
                new Object[] {GaranziaLancia7_150_12M, 7, 10000, 150000},
                new Object[] {GaranziaLancia7_150_24M, 4, 80000, 150000},
                new Object[] {GaranziaLancia7_150_24M, 7, 10000, 150000}
        };
    }

    @Test(description = "Italy Lancia Certified policy creation", dataProvider = "getLanciaCoverageTypes")
    public void test_policyCreation_matchedLanciaEligibility(String coverageType, int vehicleAge, int minMileage, int maxMileage) {
        dataSetInfo = coverageType + " with "
                + "<br>Vehicle age as " + vehicleAge
                + "<br>Minimum mileage as " + minMileage
                + "<br>Maximum mileage as " + maxMileage;

        dataFileName = coverageType.replace("/", "_")
                + "_age" + vehicleAge
                + "_mileage" + basePage.getDataObject().getPolicyCenter().getMileage();

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectModel("Ypsilon")
                .selectRegistrationDate(vehicleAge)
                .selectMileageInRange(minMileage, maxMileage);

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
    }

    @DataProvider
    public static Object[][] getDurationCoefficient() {
        return new Object[][]{
                new Object[]{GaranziaLancia4_80_24M,  "2"},
                new Object[]{GaranziaLancia7_150_12M, "1"},
                new Object[]{GaranziaLancia7_150_24M, "2"}
        };
    }

    @Test(description = "Italy Lancia Certified duration coefficient", dataProvider = "getDurationCoefficient")
    public void test_duration(String coverageType, String duration) {
        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectModel("Ypsilon");
        dataSetInfo = coverageType.replace("/","_") + " with "
                + "<br>Duration coefficient as " + duration;
        dataFileName = "test_duration_" + coverageType.replace("/", " ");

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

        String actualDuration = policyCenter.atPolicyPage().getDurationCoefficient(WarrantyPremium);
        assertEquals(actualDuration, duration, "Duration coefficient isn't match as expected");
    }

    @DataProvider
    public static Object[][] pricesForInsured() {
        return new String[][]{
                new String[]{GaranziaLancia4_80_24M,  "114,61"},
                new String[]{GaranziaLancia7_150_12M, "216,37"},
                new String[]{GaranziaLancia7_150_24M, "245,45"},
        };
    }

    @Test(description = "Validate the Rating value for Lancia offerings with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Warranty Premium price as " + warrantyPrice;
        dataFileName = "test_price_" + coverageType.replace("/", " ");
        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectModel("Ypsilon");
        dataFileName = coverageType.replace("/","_") + "_Insured_Ypsilon";

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
        assertEquals(actualAssistancePrice, "17,78", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Validate the Rating value for Lancia offerings with management type Delegated")
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
        assertEquals(actualAssistancePrice, "17,78", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "36", "0", "0,135", "1", "0", "1", "0", "0,1"},
                new String[] {DELEGATED, "36", "0", "0,1",   "1", "0", "1", "0", "0,1"},
        };
    }

    @Test(description = "Italy Lancia Certified other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyCommissions,
                                              String warrantyTaxes,
                                              String warrantyCV,
                                              String assistanceManagementFee,
                                              String assistanceLossRatio,
                                              String assistanceCommissions,
                                              String assistanceTaxes) {
        basePage.selectManagementType(managementType)
                .selectRandomCar()
                .selectRandomCoverageType();
        dataSetInfo = basePage.getDataObject().getPolicyCenter().getCoverageType() + " with "
                + "<br>Management type as " + managementType
                + "<br>Warranty Management fee as " + warrantyManagementFee
                + "<br>Warranty commissions as " + warrantyCommissions
                + "<br>Warranty taxes as " + warrantyTaxes
                + "<br>Warranty Combined variable as " + warrantyCV
                + "<br>Assistance management fee as " + assistanceManagementFee
                + "<br>Assistance loss ratio as " + assistanceLossRatio
                + "<br>Assistance commissions as " + assistanceCommissions
                + "<br>Assistance taxes as " + assistanceTaxes;
        dataFileName = "test_metrics_" + managementType;

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

        String actualCombinedVariable = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
        softAssert.assertEquals(actualCombinedVariable, warrantyCV, "Warranty Combined variable isn't match as expected");

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
