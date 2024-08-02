package ucw.tests.policycenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.Countries.Germany;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.Languages.ENGLISH;
import static ucw.enums.Languages.GERMANY;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class ReplacementMessageDisplayTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-2788: Germany_display_of_replacement_%_to_be_modified");
        basePage.selectLabel(SPOTICAR)
                .selectCountry(Germany)
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @AfterMethod
    public void removeVehicle() {
        policyCenter.atPolicyPage().removeVehicle();
        policyCenter.goBackToAccSummary();
    }

    @DataProvider
    public Object[][] prepareCoverageTypes() {
        return new Object[][] {
                new Object[] {GWSpoticarPremium1_12M, 10000, 40000, 100},
                new Object[] {GWSpoticarPremium1_24M, 10000, 40000, 100},
                new Object[] {GWSpoticarPremium2_12M, 40001, 50000, 100},
                new Object[] {GWSpoticarPremium2_12M, 50001, 60000, 90},
                new Object[] {GWSpoticarPremium2_12M, 60001, 70000, 80},
                new Object[] {GWSpoticarPremium2_12M, 70001, 80000, 70},
                new Object[] {GWSpoticarPremium2_12M, 80001, 90000, 60},
                new Object[] {GWSpoticarPremium2_12M, 90001, 100000, 50},
                new Object[] {GWSpoticarPremium2_12M, 100001, 120000, 40},
                new Object[] {GWSpoticarPremium2_24M, 40001, 50000, 100},
                new Object[] {GWSpoticarPremium2_24M, 50001, 60000, 90},
                new Object[] {GWSpoticarPremium2_24M, 60001, 70000, 80},
                new Object[] {GWSpoticarPremium2_24M, 70001, 80000, 70},
                new Object[] {GWSpoticarPremium2_24M, 80001, 90000, 60},
                new Object[] {GWSpoticarPremium2_24M, 90001, 100000, 50},
                new Object[] {GWSpoticarPremium2_24M, 100001, 120000, 40},
                new Object[] {GWSpoticarAdvanced12M, 150001, 200000, 40},
                new Object[] {GWSpoticarAdvanced24M, 120001, 150000, 40},
        };
    }

    @Test(description = "Replacement % information", dataProvider = "prepareCoverageTypes", priority = 1)
    public void test_messageAndValue(String type, long minMileage, long maxMileage, int reimbursementPercent) {
        basePage.selectCoverageType(type)
                .selectMileageInRange(minMileage, maxMileage);
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().clickCoverageTab();
        policyCenter.atPolicyPage().selectType();
        String sparePartsLabel = policyCenter.atPolicyPage().getSparePartsReimbursementLabel();
        assertEquals(sparePartsLabel, "Spare parts reimbursement % at the date of subscription (this % will evolve with km)",
                "Current spare part reimbursement label is not matched as expected");
        int actualReimbursementPercent = policyCenter.atPolicyPage().getReimbursementPercentageValue();
        assertEquals(actualReimbursementPercent, reimbursementPercent, "Current spare part reimbursement % is not matched as expected");
    }

    @Test(description = "Check the localization of replacement % information message", priority = 2)
    public void test_messageLocalization() {
        basePage.selectRandomCoverageType();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().clickCoverageTab();
        policyCenter.atPolicyPage().selectType();
        policyCenter.setLanguage(GERMANY);
        String sparePartsLabel = policyCenter.atPolicyPage().getSparePartsReimbursementLabel();
        policyCenter.setLanguage(ENGLISH);
        assertEquals(sparePartsLabel, "Maximale Erstattung der garantiebedingten Materialkosten am Tag der Vertragserfassung in %",
                "Current spare part reimbursement label is not matched as expected");
    }
}
