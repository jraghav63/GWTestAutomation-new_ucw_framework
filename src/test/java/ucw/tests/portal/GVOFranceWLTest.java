package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.France;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.WHITELABEL;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GVOFranceWLTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1959: White Label Product - IMM France");
        basePage.selectLabel(WHITELABEL)
                .selectManagementType(INSURED)
                .selectCountry(France);
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
                new Object[] {GVOCarePremium2_60     , "24", "3"},
                new Object[] {GVOCarePremium7_150_12M, "12", "3"},
                new Object[] {GVOCarePremium7_150_24M, "24", "3"},
                new Object[] {GVOCarePlus10_200      , "8",  "2"},
                new Object[] {GVOCare15_200          , "6",  "1"}
        };
    }

    @Test(description = "France White Label policy creation in Portal", dataProvider = "getCoverageTypes")
    public void test_durations(String coverageType, String expectedDuration, String waitingPeriod) {
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
        assertEquals(actualDuration, expectedDuration, "Duration values are not matched as expected");

        String actualWaitingPeriod = portal.atSubmissionPage().getWaitingPeriod();
        assertEquals(actualWaitingPeriod, waitingPeriod, "Waiting Period is not matched as expected");

        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }
}
