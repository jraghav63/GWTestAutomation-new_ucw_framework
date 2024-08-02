package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Germany;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// Covered by UCWAR-2788
public class PercentageReplacementTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-953: DE - EGVO & GVO - Change Request - Obsolescence thresholds to apply on % of reimbursement of spare parts");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Germany);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public Object[][] preparePolicyData() {
        return new Object[][] {
                new Object[] {GWSpoticarPremium1_12M, 40000, 100},
                new Object[] {GWSpoticarPremium2_12M, 55000, 90},
                new Object[] {GWSpoticarPremium2_12M, 65000, 80},
                new Object[] {GWSpoticarPremium2_24M, 75000, 70},
                new Object[] {GWSpoticarPremium2_24M, 85000, 60},
                new Object[] {GWSpoticarPremium2_24M, 95000, 50},
                new Object[] {GWSpoticarPremium2_24M, 110000, 40}
        };
    }

    @Test(description = "Percentage replacement validation", dataProvider = "preparePolicyData")
    public void test_percentageReplacement(String type, long mileage, int percent) {
        basePage.selectCoverageType(type)
                .selectRandomCar()
                .selectMileageLimitedAt(mileage);
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        assertEquals(policyCenter.atPolicyPage().getReplacementPercentage(), percent, "Replacement percentage is not matched as expected");
    }
}
