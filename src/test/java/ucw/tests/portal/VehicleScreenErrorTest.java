package ucw.tests.portal;

import org.testng.annotations.*;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1884 and UCW-1928
public class VehicleScreenErrorTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPortal() {
        startTest("Portal vehicle screen error validation");
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

    @BeforeMethod
    public void goToSubmission() {
        portal.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();
    }

    @AfterMethod
    public void cancelVehicle() {
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
    }

    @Test(description = "As a Portal User when user provides the First Registration date which is not matching " +
            "the Age eligibility criteria as defined in VP then I want to see the modified error message " +
            "to display as 'First Registration Date : Your vehicle is not eligible' to be consistent with " +
            "error message defined in PC so that Portal can be consistent with PC. " +
            "I want also to see the field encircle in red as in PC. ")
    public void test_invalidRegistrationDate() {
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().inputRegDate("01/01/2000");
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getErrorMessage();
        assertEquals(actualMessage, "First Registration Date : Your vehicle is not eligible.",
                "Actual error message for invalid registration date is not matched with expected message"
        );
    }

    @Test(description = "As a Portal User when user provides the Mileage which is not matching the " +
            "mileage eligibility criteria as defined in VP then I want to see the modified error message " +
            "to display as 'Mileage : Your vehicle is not eligible' to be consistent with error message " +
            "defined in PC, so that Portal can be consistent with PC. And encircle the concerned field in " +
            "red as in PC." +
            "If the mileage is superior to the maximum value, I want to see display the error message " +
            "'Maximum value is 200000 KM'. " +
            "(The maximum value is dependant by the country). And encircle the concerned field in red as in PC.")
    public void test_invalidMileage() {
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().inputMileage(200001);
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getErrorMessage();
        assertEquals(actualMessage, "Mileage: Your vehicle is not eligible.",
                "Actual error message for invalid mileage is not matched with expected message"
        );
    }

    @Test(description = "As a Portal user when I provide value in Maker Warranty KM > 10 digits " +
            "or enter a non integer value then I want to see the updated error message as " +
            "'Maker warranty - kilometers : must be an integer with no more than 10 digits' " +
            "so that it will be consistent with error message defined in PC and encircle the " +
            "concerned field in red as in PC.")
    public void test_invalidMakerWarrantyKM() {
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().inputWarrantyKilometer("10000000000");
        String actualMessage = portal.atSubmissionPage().getWarrantyKMErrorMsg();
        assertEquals(actualMessage, "Maker warranty - kilometers : must be an integer with no more than 10 digits",
                "Actual error message for invalid marker warranty km is not matched with expected message"
        );
    }

    @DataProvider
    public static Object[][] getInvalidVINs() {
        return new Object[][] {
                new Object[] {"VIN3938173713"},
                new Object[] {"VIN228319831983183"}
        };
    }

    @Test(description = "As a Portal user, if VIN is invalid (not matching 17 alphanumeric characters)" +
            " then I want to see an error message as 'VIN: The value in this field is invalid', " +
            "and encircle the concerned field in red as in PC.", dataProvider = "getInvalidVINs")
    public void test_invalidVIN(String vin) {
        portal.atSubmissionPage().inputVehicleMandatoryFields(vin);
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getVinErrorInlineMsg();
        assertEquals(actualMessage, "VIN: The value in this field is invalid",
                "Actual error message for invalid VIN is not matched with expected message"
        );
    }

    @Test(description = "As a Portal user, if VIN is a duplicate VIN (Matching another GVO Policy VIN) " +
            "then I want to see an error message as 'VIN is already associated with another Policy' " +
            "display one time and encircle the field in RED.")
    public void test_duplicateVIN() {
        String dupVIN = "VIN16970841239074";
        portal.atSubmissionPage().inputVehicleMandatoryFields(dupVIN);
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getErrorMessage();
        assertEquals(actualMessage, "A warranty contract already exists for this vehicle (VIN: "
                        + dupVIN + "). If you wish to subscribe a new one, please contact " +
                        "info@garantiestellantis.fr with the VIN number and the License plate " +
                        "of the vehicle to request the cancellation of the existing contract.",
                "Actual error message for duplicated VIN is not matched with expected message"
        );
    }

    @Test(description = "As a Portal user, for all countries if Car Power is exceeding the defined limit " +
            "then I want to see an error message")
    public void test_invalidCarPower() {
        int carPower = 0;
        String errorMessage = "";
        switch (basePage.getCurrentCountry()) {
            case France, Spain -> {
                carPower = 1000;
                errorMessage = "The power of this vehicle exceeds the authorized limit (510hp). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.";
            }
            case Italy -> {
                carPower = 250;
                errorMessage = "The power of this vehicle exceeds the authorized limit (249 KW). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.";
            }
            case Germany -> {
                carPower = 301;
                errorMessage = "The power of this vehicle exceeds the authorized limit (300 KW). If this vehicle belongs to a brand of the Stellantis group, please contact the back office team.";
            }
        }
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().inputCarPower(carPower);
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getCarPowerErrorMsg();
        assertEquals(actualMessage, errorMessage,
                "Actual error message for invalid car power is not matched with expected message"
        );
    }


    @DataProvider
    public static Object[][] getInvalidLicensePlate() {
        return new String[][] {
                new String[] {"27187339817"},
                new String[] {"27.618"},
        };
    }

    @Test(description = "As a Portal user during subscription, if user provides value in Licence plate > 10 digits" +
            " or enter a non integer value, then I want to see an error message: " +
            "'Licence plate can not be more than 10 characters' to be consistent with error message defined in PC, " +
            "and encircle the field in red.", dataProvider = "getInvalidLicensePlate")
    public void test_invalidLicensePlate(String licensePlate) {
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().inputLicensePlate(licensePlate);
        portal.atSubmissionPage().submitVehicleInfo();
        String actualMessage = portal.atSubmissionPage().getLicensePlateInlineErrorMsg();
        assertEquals(actualMessage, "Licence plate cannot be more than 10 characters",
                "Actual error message for invalid license plate is not matched with expected message"
        );
    }
}
