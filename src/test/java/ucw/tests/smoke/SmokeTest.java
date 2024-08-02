package ucw.tests.smoke;


import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.enums.Environments;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.contactmanager.ContactManager;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.enums.Environments.*;
import static ucw.enums.ErrorMessages.PC_DUPLICATED_VIN_MSG;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class SmokeTest extends BaseTest {
    private static final Environments[] fullFlowEnvironments = {DEV, TEST};
    private PolicyCenter policyCenter;
    private BillingCenter billingCenter;
    private ClaimCenter claimCenter;
    private ContactManager contactManager;

    @BeforeClass
    public void goToCenters() {
        startTest("x-Centers smoke test");
        if (basePage.getCurrentEnvironment().equals(TEST)) {
            basePage.getConfig().setSsoAccount(basePage.getConfig().getAccountWithoutCode());
        }
        basePage.selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        billingCenter = basePage.loginBillingCenter();
        claimCenter = basePage.loginClaimCenter();
        contactManager = basePage.loginContactManager();
    }

    @DataProvider
    public static Object[][] getAllCountries() {
        return new Object[][] {
                new Object[]{Germany},
                new Object[]{Italy},
                new Object[]{Spain},
                new Object[]{France}
        };
    }

    @Test(description = "Centers smoke test", dataProvider = "getAllCountries")
    public void smokeTest(Countries country) {
        if (basePage.getCurrentEnvironment().equals(TRAINING) && country.equals(Germany)) {
            throw new SkipException("Skip smoke test in Germany");
        }
        basePage.selectCountry(country)
                .selectRandomLabel()
                .selectRandomCoverageType();
        dataFileName = "smokeTest_" + country;
        basePage.switchToBillingCenterTab();
        billingCenter.navigateToDesktopPage();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().openRateWorksheet();
        policyCenter.atPolicyPage().verifyRate();
        String dupVINErrorMsg = policyCenter.getExistingVINErrorMsg();
        String existingVIN = basePage.getDataObject().getPolicyCenter().getVin();
        String expectedErrorMsg = PC_DUPLICATED_VIN_MSG.replace("EXISTEDVIN", existingVIN);
        assertEquals(dupVINErrorMsg, expectedErrorMsg, "Error message for existing VIN does not match with expected");

        basePage.switchToClaimCenterTab();
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        claimCenter.navigateToDesktopPage();

        if (Arrays.asList(fullFlowEnvironments).contains(basePage.getCurrentEnvironment())) {
            basePage.switchToContactManagerTab();
            contactManager.createContact();
            assertTrue(contactManager.validateContactFlow(), "Contact flow in Contact Manager validation is failed");

            basePage.switchToPolicyCenterTab();
            assertTrue(policyCenter.atSearchPage().searchCMContacts(), "Not able to search Contact Manager contacts in Policy Center");
        }
        basePage.switchToBillingCenterTab();
        billingCenter.navigateToPoliciesPage();
        assertTrue(billingCenter.atPolicyPage().searchPolicy(basePage.getDataObject().getPolicyNumber()), "Policy number is not found in Billing Center");
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().searchPCAccount();
        String actualAccount = billingCenter.atAccountsPage().getAccountNumber();
        assertEquals(actualAccount, basePage.getDataObject().getAccountNumber(), "Policy Center account " + basePage.getDataObject().getAccountNumber() + " is not found in Billing Center");
        billingCenter.navigateToSearchPage();
        assertTrue(billingCenter.atSearchPage().searchPCContacts(), "Not able to search all Policy Center contacts in Billing Center");
        if (Arrays.asList(fullFlowEnvironments).contains(basePage.getCurrentEnvironment())) {
            assertTrue(billingCenter.atSearchPage().searchCMContacts(), "Not able to search all Contact Manager contacts in Billing Center");

            basePage.switchToClaimCenterTab();
            claimCenter.navigateToAddressBook();
            assertTrue(claimCenter.atAddressBookPage().searchCMContacts(), "Not able to search all Contact Manager contacts in Claim Center");
        }

        basePage.switchToPolicyCenterTab();
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().changePolicy();
        assertEquals(policyCenter.atPolicyPage().getTypeOfPolicyTransaction(), "Policy Change", "Policy type value doesn't match with expected");

        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancel();
        String actualCancelMessage = policyCenter.atPolicyPage().getCancelMsg();
        assertTrue(actualCancelMessage.contains("Canceled"),
                "Policy cancel message doesn't match with expected. Actual message is " + actualCancelMessage);
        assertEquals(policyCenter.atPolicyPage().getTransactionType(), "Cancellation", "Actual transaction type is not Cancellation");

        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().reinstate();
        String actualReinstatementMessage = policyCenter.atPolicyPage().getReinstateMessage();
        assertTrue(actualReinstatementMessage.contains("In Force"),
                "Reinstatement message isn't matched with expected. Actual message is " + actualReinstatementMessage);
        assertEquals(policyCenter.atPolicyPage().getTransactionType(), "Reinstatement", "The policy " + basePage.getDataObject().getPolicyNumber() + " is not reinstated successfully");
    }
}