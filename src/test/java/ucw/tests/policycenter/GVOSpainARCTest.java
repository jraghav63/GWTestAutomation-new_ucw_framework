package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;
import static ucw.enums.Countries.Spain;
import static ucw.enums.CoverageTerms.*;
import static ucw.enums.CoverageTerms.AssistancePremium;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.CoverageTypes.GVOARC7_150;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCW-31
public class GVOSpainARCTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-2803: GVO Spain - Spoticar and Certified - price increase 2024 January");
        basePage.selectLabel(ARC)
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
    public static Object[][] getCoverageTypes() {
        return new Object[][]{
                new Object[]{GVOARC4_80,  4, 100,   80000},
                new Object[]{GVOARC7_150, 4, 80000, 150000},
                new Object[]{GVOARC7_150, 7, 100,   150000}
        };
    }

    @Test(description = "Spain Alfa Romeo Certified policy creation", dataProvider = "getCoverageTypes")
    public void test_policyCreation_matchedARCEligibility(String coverageType, int vehicleAge, long minMileage, long maxMileage) {
        dataSetInfo = coverageType + " with "
                + "<br>Vehicle age as " + vehicleAge
                + "<br>Minimum mileage is " + minMileage
                + "<br>Maximum mileage is " + maxMileage;

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
                new String[]{GVOARC4_80,  "Giulietta",            "140,65" },
                new String[]{GVOARC4_80,  "Tonale",               "216,01" },
                new String[]{GVOARC4_80,  "Stelvio",              "399,09" },
                new String[]{GVOARC4_80,  "Giulia",               "399,09" },
                new String[]{GVOARC4_80,  "Giulia GTA",           "839,22" },
                new String[]{GVOARC4_80,  "Giulia Quadrifoglio",  "839,22" },
                new String[]{GVOARC7_150, "Giulietta",            "313,1"  },
                new String[]{GVOARC7_150, "Tonale",               "435,15" },
                new String[]{GVOARC7_150, "Stelvio",              "743,06" },
                new String[]{GVOARC7_150, "Giulia",               "743,06" },
                new String[]{GVOARC7_150, "Giulia GTA",           "1527,15"},
                new String[]{GVOARC7_150, "Stelvio Quadrifoglio", "1527,15"},
        };
    }

    @Test(description = "Validate the Rating value for Alfa Romeo offerings with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, String carModel, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Car model is " + carModel
                + "<br>Warranty premium price is " + warrantyPrice;

        basePage.selectManagementType(INSURED)
                .selectRandomCar()
                .selectCoverageType(coverageType)
                .selectModel(carModel);
        dataFileName = coverageType.replace("/","_") + "Insured_" + carModel;

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
        assertEquals(actualWarrantyPrice, warrantyPrice, "Warranty Premium price of model " + carModel + " isn't match as expected");

        String actualAssistancePrice = policyCenter.atPolicyPage().getPrice(AssistancePremium);
        assertEquals(actualAssistancePrice, "3,26", "Assistance Premium price of model " + carModel + " isn't match as expected");
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
        assertEquals(actualAssistancePrice, "3,26", "Assistance Premium price isn't match as expected");
    }

    @DataProvider
    public static Object[][] getManagementTypes() {
        return new String[][] {
                new String[] {INSURED,   "28",    "0", "0,0815", "1", "0", "1", "3", "0,0815", "1"},
                new String[] {DELEGATED, "27,84", "0", "0,21",   "1", "0", "1", "3", "0,0815", "1"},
        };
    }

    @Test(description = "Spain Alfa Romeo Certified other metrics per management types", dataProvider = "getManagementTypes")
    public void test_metricsPerManagementType(String managementType,
                                              String warrantyManagementFee,
                                              String warrantyCommissions,
                                              String warrantyTaxes,
                                              String warrantyCV,
                                              String assistanceManagementFee,
                                              String assistanceLossRatio,
                                              String assistanceCommissions,
                                              String assistanceTaxes,
                                              String assistanceCV) {
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

        String actualWarrantyCV = policyCenter.atPolicyPage().getCombinedVariable(WarrantyPremium);
        softAssert.assertEquals(actualWarrantyCV, warrantyCV, "Warranty combined variable isn't match as expected");

        String actualAssistanceFee = policyCenter.atPolicyPage().getManagementFee(AssistanceManagementFee);
        softAssert.assertEquals(actualAssistanceFee, assistanceManagementFee, "Assistance Management Fee isn't match as expected");

        String actualAssistanceLossRatio = policyCenter.atPolicyPage().getLossRatio(AssistancePremium);
        softAssert.assertEquals(actualAssistanceLossRatio, assistanceLossRatio, "Assistance Loss ratio isn't match as expected");

        String actualAssistanceCommission = policyCenter.atPolicyPage().getCommissions(AssistanceCommission);
        softAssert.assertEquals(actualAssistanceCommission, assistanceCommissions, "Assistance Commissions isn't match as expected");

        String actualAssistanceTaxes = policyCenter.atPolicyPage().getTaxes(AssistanceTaxes);
        softAssert.assertEquals(actualAssistanceTaxes, assistanceTaxes, "Assistance Taxes isn't match as expected");

        String actualAssistanceCV = policyCenter.atPolicyPage().getCombinedVariable(AssistancePremium);
        softAssert.assertEquals(actualAssistanceCV, assistanceCV, "Assistance Assistance combined variable isn't match as expected");

        softAssert.assertAll();
    }
}
