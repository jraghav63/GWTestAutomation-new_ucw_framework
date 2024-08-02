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
import static ucw.enums.Labels.DSC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GVOSpainDSTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("GVO Spain DS Certified in Portal");
        basePage.selectLabel(DSC)
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
    public static Object[][] getDuration() {
        return new Object[][] {
                new Object[]{GVODSC4_80, 4, 100, 80000},
        };
    }

    @Test(description = "Spain DS Certified policy creation and check duration in Portal", dataProvider = "getDuration")
    public void test_duration(String coverageType, int vehicleAge, long minMileage, long maxMileage) {
        basePage.selectCoverageType(coverageType)
                .selectRegistrationDate(vehicleAge)
                .selectMileageInRange(minMileage, maxMileage);
        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        String actualDuration = portal.atSubmissionPage().getDuration();
        assertEquals(actualDuration, "24", "Duration is not matched as expected");
        boolean isWaitingPeriodShown = portal.atSubmissionPage().isWaitingPeriodDisplayed();
        assertFalse(isWaitingPeriodShown, "Waiting period is shown, which is not expected");
        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }

    @DataProvider
    public static Object[][] getDurations() {
        return new Object[][] {
                new Object[]{GVODSC7_150, 4, 80000, 150000},
                new Object[]{GVODSC7_150, 7, 100, 150000}
        };
    }

    @Test(description = "Spain DS Certified policy creation and check duration in Portal", dataProvider = "getDurations")
    public void test_durations(String coverageType, int vehicleAge, long minMileage, long maxMileage) {
        basePage.selectCoverageType(coverageType)
                .selectRegistrationDate(vehicleAge)
                .selectMileageInRange(minMileage, maxMileage);
        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        String[] expectedDurations = new String[] {"12", "24"};
        By actualDurations = portal.atSubmissionPage().getDurationList();
        assertTrue(basePage.dropDownValuesExist(actualDurations, expectedDurations), "Duration values are not matched as expected");
        boolean isWaitingPeriodShown = portal.atSubmissionPage().isWaitingPeriodDisplayed();
        assertFalse(isWaitingPeriodShown, "Waiting period is shown, which is not expected");
        portal.atSubmissionPage().buy();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
    }
}
