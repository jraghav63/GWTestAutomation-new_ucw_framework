package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.France;
import static ucw.enums.Environments.DEV;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCWAR-2011
public class MarketingFeeTest extends BaseTest {
    private PolicyCenter policyCenter;
    private static String expectedFee;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-2011: New Coverage \"Redevance Marketing\" with UCW Spoticar and Certified Label France");
        basePage.selectManagementType(INSURED);
        expectedFee = (basePage.getCurrentEnvironment().equals(DEV)) ? "21,00 €" : "0";
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "With Spoticar label should be 18.00 € + 20% VAT")
    public void test_marketingFee_SpoticarLabel() {
        basePage.selectLabel(SPOTICAR)
                .selectCountry(France)
                .selectRandomCoverageType();

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        assertEquals(policyCenter.atPolicyPage().getQuoteMarketingFee(), expectedFee, "Actual Marketing fee value at Quote page does not match with expected");
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        String actualMarketingFee = policyCenter.atPolicyPage().getMarketingFeeValue();
        assertEquals(actualMarketingFee, expectedFee, "Actual Marketing fee value at Summary page does not match with expected");
    }

    @Test(description = "With other labels should be 0")
    public void test_marketingFee_otherLabels() {
        basePage.selectRandomLabelExceptThese(new String[]{LC, SPOTICAR, WHITELABEL})
                .selectCountry(France)
                .selectRandomCoverageType();

        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        assertEquals(policyCenter.atPolicyPage().getQuoteMarketingFee(), "0", "Actual Marketing fee value at Quote page does not match with expected");
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        String actualMarketingFee = policyCenter.atPolicyPage().getMarketingFeeValue();
        assertEquals(actualMarketingFee, "0", "Actual Marketing fee value at Summary page does not match with expected");
    }
}
