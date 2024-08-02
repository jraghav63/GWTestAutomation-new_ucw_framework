package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.enums.Makes;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.*;
import static ucw.enums.Makes.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class CertifiedSubscriptionTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;
    private static final String[] oldARCModels = {"Brera", "GT", "147", "159", "8C"};

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-2571: Modify subscription process for Certified Dealers on Portal");
        basePage.selectManagementType(INSURED);
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
    public static Object[][] getMakes() {
        return new Object[][] {
                new Object[] {ARC, Spain,  ALFAROMEO},
                new Object[] {DSC, Spain,  DS       },
                new Object[] {ARC, France, ALFAROMEO},
                new Object[] {DSC, France, DS       },
                new Object[] {ARC, Italy,  ALFAROMEO},
                new Object[] {DSC, Italy,  DS       },
                new Object[] {LC,  Italy,  LANCIA   }
        };
    }

    @Test(description = "Validating that when Producer code Label = Alfa Romeo/DS/Lancia Certified then Make Typelist will have only related certified option",
            dataProvider = "getMakes")
    public void test_typelist(String label, Countries country, Makes make) {
        basePage.selectLabel(label)
                .selectCountry(country)
                .switchToPolicyCenterTab();

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();

        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        boolean result = portal.atSubmissionPage().checkMakeOnlyContains(make);
        assertTrue(result, "Makes typelist contains more options than only " + make);

        if (label.equals(ARC)) { // UCWAR-3127: Remove old AlfaRomeo vehicle models for AlfaRomeoCertified dealers
            portal.atSubmissionPage().inputMake();
            boolean acceptedModels = portal.atSubmissionPage().checkModelsNotContains(oldARCModels);
            assertTrue(acceptedModels, "Model selector may contains one of followings: Brera, GT, 147, 159, 8C");
        }
    }

    @DataProvider
    public static Object[][] getTypes() {
        return new Object[][] {
                new Object[] {Spain,  ARC, "Alfa Romeo"},
                new Object[] {Spain,  DSC, "GVO DSC"},
                new Object[] {France, ARC, "Alfa Romeo"},
                new Object[] {France, DSC, "GVO DSC"},
                new Object[] {Italy,  ARC, "Alfa Romeo"},
                new Object[] {Italy,  DSC, "Garanzia DS Certified"},
                new Object[] {Italy,  LC,  "Lancia Certified"}
        };
    }

    @Test(description = "Validating that when Producer code Label = Alfa Romeo/DS/Lancia Certified then only related offerings should be available",
            dataProvider = "getTypes")
    public void test_certifiedOfferings(Countries country, String label, String partialText) {
        basePage.selectLabel(label)
                .selectCountry(country)
                .selectRandomCoverageType();

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();

        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        boolean result = portal.atSubmissionPage().checkAllOffersContain(partialText);
        assertTrue(result, "Coverage type dropdown contains more options not related to " + label);
    }
}
