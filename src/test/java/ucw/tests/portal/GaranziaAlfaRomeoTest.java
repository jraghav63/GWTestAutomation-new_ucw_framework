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
import static ucw.enums.Countries.Italy;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.ARC;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class GaranziaAlfaRomeoTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-719: UCW Italy - AR Certified - Virtual Product & Rate book");
        basePage.selectLabel(ARC)
                .selectManagementType(INSURED)
                .selectCountry(Italy);
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
                new Object[] {GaranziaARC4_80_24M,  4, 100,   80000,  "24"},
                new Object[] {GaranziaARC7_150_12M, 4, 80000, 150000, "12"},
                new Object[] {GaranziaARC7_150_12M, 7, 100,   150000, "12"},
                new Object[] {GaranziaARC7_150_24M, 4, 80000, 150000, "24"},
                new Object[] {GaranziaARC7_150_24M, 7, 100,   150000, "24"},
        };
    }

    @Test(description = "Italy Alfa Romeo Certified policy creation and check duration in Portal", dataProvider = "getCoverageTypes")
    public void test_policyCreation(String coverageType, int vehicleAge, long minMileage, long maxMileage, String duration) {
        basePage.selectCoverageType(coverageType)
                .selectRegistrationDate(vehicleAge)
                .selectMileageInRange(minMileage, maxMileage)
                .selectRandomCar();
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
