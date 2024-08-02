package ucw.tests.portal;

import org.testng.annotations.*;
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
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GVOFranceSpoticarTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("GVO France Spoticar in Portal");
        basePage.selectLabel(SPOTICAR)
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
                new Object[] {GVOPremium2_60,        "24", "3"},
                new Object[] {GVOPremium5_100_12,    "12", "3"},
                new Object[] {GVOPremium5_100_24,    "24", "3"},
                new Object[] {GVOPremium7_150_12,    "12", "3"},
                new Object[] {GVOPremium7_150_24,    "24", "3"},
                new Object[] {GVOAdvanced10_200_8,   "8",  "2"},
                new Object[] {GVOAdvanced10_200_12,  "12", "2"},
                new Object[] {GVOEssential15_200_6,  "6",  "1"},
                new Object[] {GVOEssential15_200_12, "12", "1"},
        };
    }

    @Test(description = "France Spoticar policy creation and check duration in Portal", dataProvider = "getCoverageTypes")
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
