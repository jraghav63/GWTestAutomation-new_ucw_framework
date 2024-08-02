package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.*;
import static ucw.enums.Countries.France;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class RegDateEditableTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;
    private static final Map<Countries, String> licensePlates = new HashMap<>();
    static {
        licensePlates.put(Italy, "BK905EV");
        licensePlates.put(Spain, "050001L");
        licensePlates.put(France, "CK239EJ");
    }

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-2990: CIN ==> Make the \"First registration date\" field editable in Portal");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED);
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

    @Test(description = "Validate the First registration field is editable when the field retrieved from CIN")
    public void test_editableRegDate() {
        basePage.selectRandomCountryExcept(Germany)
                .selectRandomCoverageType();
        String licensePlate = licensePlates.get(basePage.getCurrentCountry());
        basePage.selectLicensePlate(licensePlate);

        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().searchLicensePlate();
        String expectedRegDate = basePage.getDataObject().getPortal().getRegDate().replace("-", "/");
        portal.atSubmissionPage().inputRegDate(expectedRegDate);
        String changedRegistrationDate = portal.atSubmissionPage().getEnteredRegistrationDate();
        assertEquals(changedRegistrationDate, expectedRegDate, "Registration Date field is not editable");

        portal.atSubmissionPage().saveDraft();
        portal.atSubmissionPage().continueQuote();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().searchLicensePlate();
        portal.atSubmissionPage().inputRegDate(expectedRegDate);
        changedRegistrationDate = portal.atSubmissionPage().getEnteredRegistrationDate();
        assertEquals(changedRegistrationDate, expectedRegDate, "Registration Date field is not editable");
    }
}
