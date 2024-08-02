package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.*;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.Germany;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GWGarantieSpoticarTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-141: Germany-GW Garantie SPOTICAR Product Deployment");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Germany);
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

    @DataProvider
    public static Object[][] getCoverageTypes() {
        return new Object[][] {
                new Object[] {GWSpoticarPremium1_12M, "12"},
                new Object[] {GWSpoticarPremium1_24M, "24"},
                new Object[] {GWSpoticarPremium2_12M, "12"},
                new Object[] {GWSpoticarPremium2_24M, "24"},
                new Object[] {GWSpoticarAdvanced12M,  "12"},
                new Object[] {GWSpoticarAdvanced24M,  "24"}
        };
    }

    @Test(description = "GW Garantie policy creation and check duration in Portal", dataProvider = "getCoverageTypes")
    public void test_policyCreation(String coverageType, String duration) {
        basePage.selectCoverageType(coverageType);
        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        String actualDuration = portal.atSubmissionPage().getDuration();
        assertEquals(actualDuration, duration, "Duration is not matched as expected");
        boolean isWaitingPeriodShown = portal.atSubmissionPage().isWaitingPeriodDisplayed();
        assertFalse(isWaitingPeriodShown, "Waiting period is shown, which is not expected");
        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }
}
