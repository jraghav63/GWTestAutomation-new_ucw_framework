package ucw.tests.portal;

import org.openqa.selenium.By;
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
import static ucw.enums.Countries.Spain;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GVOSpainSpoticarTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("GVO Spain Spoticar validation in Portal");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Spain);
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
                new Object[] {GVOPremium2_60,    new String[] {"12", "24", "36"}},
                new Object[] {GVOPremium2_100,   new String[] {"12", "24"}},
                new Object[] {GVOPremium5_150,   new String[] {"12", "24"}},
                new Object[] {GVOPremium7_150,   new String[] {"12", "24"}},
                new Object[] {GVOAdvanced10_150, new String[] {"12", "24"}}
        };
    }

    @Test(description = "Spain Spoticar policy creation and check duration in Portal", dataProvider = "getCoverageTypes")
    public void test_durations(String coverageType, String[] expectedDurations) {
        basePage.selectCoverageType(coverageType);
        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        By actualDurations = portal.atSubmissionPage().getDurationList();
        assertTrue(basePage.dropDownValuesExist(actualDurations, expectedDurations), "Duration values are not matched as expected");
        boolean isWaitingPeriodShown = portal.atSubmissionPage().isWaitingPeriodDisplayed();
        assertFalse(isWaitingPeriodShown, "Waiting period is shown, which is not expected");
        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }

    @DataProvider
    public static Object[][] getDuration() {
        return new Object[][] {
                new Object[] {GVOPremium7_200,   "12"},
                new Object[] {GVOAdvanced10_200, "12"}
        };
    }

    @Test(description = "Spain Spoticar policy creation and check duration in Portal", dataProvider = "getDuration")
    public void test_duration(String coverageType, String expectedDuration) {
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
        assertEquals(actualDuration, expectedDuration, "Duration value are not matched as expected");
        boolean isWaitingPeriodShown = portal.atSubmissionPage().isWaitingPeriodDisplayed();
        assertFalse(isWaitingPeriodShown, "Waiting period is shown, which is not expected");
        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }
}
