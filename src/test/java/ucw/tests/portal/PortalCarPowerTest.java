package ucw.tests.portal;

import org.testng.annotations.*;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class PortalCarPowerTest extends BaseTest {
	private Portal portal;
	private PolicyCenter policyCenter;
	private static final Map<Countries, Integer> maxCarPowers = new HashMap<>();
	static {
		maxCarPowers.put(France, 999);
		maxCarPowers.put(Spain, 380);
		maxCarPowers.put(Italy, 249);
		maxCarPowers.put(Germany, 300);
	}

	private static final Map<Countries, String> errorCarPowerMsg = new HashMap<>();
	static {
		errorCarPowerMsg.put(France, "The power of this vehicle exceeds the authorized limit (510hp). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.");
		errorCarPowerMsg.put(Spain, "The power of this vehicle exceeds the authorized limit (380hp). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.");
		errorCarPowerMsg.put(Italy, "The power of this vehicle exceeds the authorized limit (249 KW). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.");
		errorCarPowerMsg.put(Germany, "The power of this vehicle exceeds the authorized limit (300 KW). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.");
	}

	@BeforeClass
	public void goToCenters() {
		startTest("UCWAR-1975: Car power validation in Portal");
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

	@Test(priority = 1, description = "Decline car power if beyond maximum value in Portal")
	public void test_declineCarPower() {
		basePage.selectRandomCoverageType();
		int carPower = maxCarPowers.get(basePage.getCurrentCountry());
		String errorMsg = errorCarPowerMsg.get(basePage.getCurrentCountry());

		portal.navigateToSubmissionPage();
		portal.cancelSubmission();
		portal.atSubmissionPage().createNewContact(Personal);
		portal.atSubmissionPage().submitContactInfo();
		portal.atSubmissionPage().goToVehicleDetails();
		portal.atSubmissionPage().inputVehicleMandatoryFields();
		portal.atSubmissionPage().inputCarPower(carPower + 1);
		portal.atSubmissionPage().submitVehicleInfo();
		String actualMessage = portal.atSubmissionPage().getCarPowerErrorMsg();
		assertEquals(actualMessage, errorMsg,
				"Actual error message of car power validation is not matching with expected"
		);
	}

	@Test(priority = 2, description = "Accept max car power country in Portal")
	public void test_acceptMaximumCarPower() {
		basePage.selectRandomCoverageType()
				.selectCarPower(maxCarPowers.get(basePage.getCurrentCountry()));

		portal.navigateToSubmissionPage();
		portal.cancelSubmission();
		portal.atSubmissionPage().addNewSubmission();
		assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Portal submission with maximum car power is failed");
	}
}