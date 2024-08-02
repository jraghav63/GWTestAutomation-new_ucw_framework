package ucw.tests.policycenter;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.*;
import static ucw.enums.Countries.France;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTerms.AssistancePremium;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCW-10 and UCW-1193
public class GVOFranceARCTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-10: GVO France - Alfa Romeo Certified Evolution-Virtual Product & Rate book");
        basePage.selectLabel(ARC)
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
    public static Object[][] getCoverageTypes() {
        return new Object[][]{
                new Object[]{GVOARCertfied4_80,  4, 100,   80000},
                new Object[]{GVOARCertfied7_150, 4, 80000, 150000},
                new Object[]{GVOARCertfied7_150, 7, 100,   150000}
        };
    }

    @Test(description = "France Alfa Romeo Certified policy creation", dataProvider = "getCoverageTypes")
    public void test_policyCreation_matchedARCEligibility(String coverageType, int vehicleAge, long minMileage, long maxMileage) {
        dataSetInfo = coverageType + " with vehicle age as "
                + vehicleAge + " years and mileage range from "
                + minMileage + " to " + maxMileage;
        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
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
    public static Object[][] pricesForInsured() {
        return new String[][]{
                new String[]{GVOARCertfied4_80,  "Giulietta",            "228,64"},
                new String[]{GVOARCertfied4_80,  "Tonale",               "283,69"},
                new String[]{GVOARCertfied4_80,  "Stelvio",              "405,25"},
                new String[]{GVOARCertfied4_80,  "Giulia",               "405,25"},
                new String[]{GVOARCertfied4_80,  "Giulia GTA",           "852,49"},
                new String[]{GVOARCertfied4_80,  "Giulia Quadrifoglio",  "852,49"},
                new String[]{GVOARCertfied7_150, "Giulietta",            "407,54"},
                new String[]{GVOARCertfied7_150, "Tonale",               "517,63"},
                new String[]{GVOARCertfied7_150, "Stelvio",              "719,47"},
                new String[]{GVOARCertfied7_150, "Giulia",               "719,47"},
                new String[]{GVOARCertfied7_150, "Giulia GTA",           "1480,94"},
                new String[]{GVOARCertfied7_150, "Stelvio Quadrifoglio", "1480,94"},
        };
    }

    @Test(description = "Validate the Rating value for Alfa Romeo offerings with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, String carModel, String warrantyPrice) {
        dataSetInfo = coverageType + " with car model as "
                + carModel + " and Warranty Premium price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectModel(carModel);
        dataFileName = coverageType.replace("/","_") + "_Insured_" + carModel;

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
        assertEquals(actualAssistancePrice, "30,36", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Validate the Rating value for Alfa Romeo offerings with management type Delegated")
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
        assertEquals(actualAssistancePrice, "30,36", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "42", "0", "0,09", "0", "1", "0", "0,2", "1"},
                new String[] {DELEGATED, "36", "0", "0,2",  "0", "1", "0", "0,2", "1"},
        };
    }

    @Test(description = "France Alfa Romeo Certified other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyCommissions,
                                              String warrantyTaxes,
                                              String assistanceManagementFee,
                                              String assistanceLossRatio,
                                              String assistanceCommissions,
                                              String assistanceTaxes,
                                              String assistanceCV) {

        basePage.selectManagementType(managementType)
                .selectRandomCar()
                .selectRandomCoverageType();
        dataSetInfo = "Validate following metrics of coverage type "
                + basePage.getDataObject().getPolicyCenter().getCoverageType()
                + " with Management type as " + managementType + " if"
                + "<br>Warranty Management fee as " + warrantyManagementFee
                + "<br>Warranty commissions as " + warrantyCommissions
                + "<br>Warranty taxes as " + warrantyTaxes
                + "<br>Assistance management fee as " + assistanceManagementFee
                + "<br>Assistance loss ratio as " + assistanceLossRatio
                + "<br>Assistance commissions as " + assistanceCommissions
                + "<br>Assistance taxes as " + assistanceTaxes
                + "<br>Assistance Combined variable as " + assistanceCV;
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

        String actualCombinedVariable = policyCenter.atPolicyPage().getCombinedVariable(AssistancePremium);
        softAssert.assertEquals(actualCombinedVariable, assistanceCV, "Assistance Combined variable isn't match as expected");

        softAssert.assertAll();
    }
}
