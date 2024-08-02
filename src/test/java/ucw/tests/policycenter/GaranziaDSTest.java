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
import static ucw.enums.Labels.DSC;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GaranziaDSTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-680: UCW Italy - DS Certified - Virtual Product & Rate book");
        basePage.selectLabel(DSC)
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
    public static Object[][] getCoverageTypes() {
        return new Object[][] {
                new Object[] {GaranziaDS4_80_24M,  4, 10000, 80000 },
                new Object[] {GaranziaDS7_150_12M, 4, 80000, 150000},
                new Object[] {GaranziaDS7_150_12M, 7, 10000, 150000},
                new Object[] {GaranziaDS7_150_24M, 4, 80000, 150000},
                new Object[] {GaranziaDS7_150_24M, 7, 10000, 150000},
        };
    }

    @Test(description = "Italy DS Certified policy creation", dataProvider = "getCoverageTypes")
    public void test_policyCreation(String coverageType, int vehicleAge, long minMileage, long maxMileage) {
        dataSetInfo = coverageType + " with "
                + "<br>Vehicle age as " + vehicleAge
                + "<br>Minimum mileage as " + minMileage
                + "<br>Maximum mileage as " + maxMileage;

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
                new String[]{GaranziaDS4_80_24M,  "DS3", "169,24"},
                new String[]{GaranziaDS4_80_24M,  "DS4", "236,64"},
                new String[]{GaranziaDS4_80_24M,  "DS5", "271"   },
                new String[]{GaranziaDS4_80_24M,  "DS7", "271"   },
                new String[]{GaranziaDS7_150_12M, "DS3", "336,2" },
                new String[]{GaranziaDS7_150_12M, "DS4", "460,42"},
                new String[]{GaranziaDS7_150_12M, "DS5", "526,5" },
                new String[]{GaranziaDS7_150_12M, "DS7", "526,5" },
                new String[]{GaranziaDS7_150_24M, "DS3", "384,65"},
                new String[]{GaranziaDS7_150_24M, "DS4", "525,62"},
                new String[]{GaranziaDS7_150_24M, "DS5", "600,95"},
                new String[]{GaranziaDS7_150_24M, "DS7", "600,95"},
        };
    }

    @Test(description = "Validate the Rating value for DS Certified offerings with management type Insured" , dataProvider = "pricesForInsured")
    public void test_price_labelInsured(String coverageType, String carModel, String warrantyPrice) {
        dataSetInfo = coverageType + " with "
                + "<br>Car model as " + carModel
                + "<br>Warranty Premium price as " + warrantyPrice;
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
        assertEquals(actualAssistancePrice, "17,78", "Assistance Premium price isn't match as expected");
    }

    @Test(description = "Validate the Rating value for DS Certified offerings with management type Delegated")
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
    public static Object[][] getMetrics() {
        return new String[][] {
                new String[] {INSURED,   "36", "0", "0,135", "1", "0", "1", "0", "0,1"},
                new String[] {DELEGATED, "36", "0", "0,1",   "1", "0", "1", "0", "0,1"},
        };
    }

    @Test(description = "Italy DS Certified other metrics per management types", dataProvider = "getMetrics")
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
