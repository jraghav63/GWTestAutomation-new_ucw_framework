package ucw.tests.smoke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.Countries.*;
import static ucw.enums.ErrorMessages.PORTAL_DUPLICATED_VIN_MSG;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class PortalSmokeTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PortalSmokeTest.class);
    private PolicyCenter policyCenter;
    private Portal portal;

    @BeforeClass
    public void goToCenters() {
        basePage.selectManagementType(INSURED);
        startTest("Portal smoke test on all countries");
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.atInternalSystemPage().changeClock(PRESENT);
        portal = basePage.loginPortal();
    }

    @AfterClass
    public void revertSystemClock() {
        basePage.switchToPolicyCenterTab();
        policyCenter.atInternalSystemPage().changeClock(FUTURE);
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

    @Test(description = "Portal smoke test", dataProvider = "getAllCountries")
    public void smokeTestOnPortal(Countries country) {
        basePage.selectCountry(country)
                .selectRandomLabel()
                .selectRandomCoverageType();
        dataFileName = "portalSmokeTest_" + country;
        logger.info("Starting Portal smoke test on " + country);

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.clickMenu("Desktop");

        basePage.switchToPortalTab();
        portal.returnHome();
        portal.cancelSubmission();
        portal.navigateToSubmissionPage();
        portal.atSubmissionPage().addNewSubmission();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");
        portal.returnHome();
        portal.navigateToSubmissionPage();
        portal.atSubmissionPage().useExistingContact();
        portal.atSubmissionPage().goToVehicleDetails();
        String existingVIN = basePage.getDataObject().getPolicyCenter().getVin();
        portal.atSubmissionPage().inputVehicleMandatoryFields(existingVIN);
        portal.atSubmissionPage().submitVehicleInfo();
        String vinErrorMsg = portal.atSubmissionPage().getErrorMessage();
        String expectedErrorMsg = PORTAL_DUPLICATED_VIN_MSG.replace("EXISTEDVIN", existingVIN);
        assertEquals(vinErrorMsg, expectedErrorMsg, "Error message for existing VIN does not match with expected");
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();

        if (!portal.isWarrantyEndDateAFutureDate()) {
            portal.returnHome();
            portal.navigateToClaimPage();
            portal.atFileClaimPage().createClaim();
            assertTrue(portal.atFileClaimPage().isClaimCreated(), "Claim is not created successfully in Portal");
        }

        portal.returnHome();
        portal.searchPolicyNumber();
        assertTrue(portal.isPolicyNumberFound(), "Policy number is not found in Portal");
        portal.searchPCAccount();
        assertTrue(portal.isPCAccountFound(), "Policy Center account is not found in Portal");
        portal.returnHome();

        basePage.switchToPolicyCenterTab();
        policyCenter.atSearchPage().searchQuote();
        assertTrue(policyCenter.atSearchPage().isQuoteFound(), "Quote number is not found in Policy Center");
        policyCenter.atSearchPage().searchPortalPolicy();
        policyCenter.atPolicyPage().waitForSummaryScreen();
        assertTrue(policyCenter.atSearchPage().isPortalPolicyFound(), "Portal Policy is not found in Policy Center");
    }
}
