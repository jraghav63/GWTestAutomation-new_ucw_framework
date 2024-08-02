package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class BrandDisplayInSummary extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1859: At the policy screen display the brand exactly as at the brand typelist");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountry();
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.atInternalSystemPage().changeClock(PRESENT);
        portal = basePage.loginPortal();
    }

    @AfterClass
    public void revertSystemClock() {
        basePage.switchToPolicyCenterTab();
        policyCenter.atInternalSystemPage().changeClock(FUTURE);
    }

    @Test(description = "Check if the brand name displays exactly the brand from typelist in Portal")
    public void test_brandName_inPortal() {
        basePage.selectRandomCoverageType();
        String brand = portal.getDataObject().getPolicyCenter().getMake();
        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.atSubmissionPage().addNewSubmission();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
        portal.returnHome();
        portal.searchPolicyNumber();
        assertTrue(portal.isPolicyNumberFound(), "Policy number is not found in Portal");
        portal.clickPolicySearchResult();
        assertEquals(portal.atPoliciesPage().getBrandValue(), brand, "Brand name in summary screen is not matched with brand name in type list");

        basePage.switchToPolicyCenterTab();
        policyCenter.atSearchPage().searchPortalPolicy();
        policyCenter.atPolicyPage().waitForSummaryScreen();
        assertEquals(policyCenter.atPolicyPage().getBrandName(), brand, "Brand name in summary screen is not matched with brand name in type list");
    }
}
