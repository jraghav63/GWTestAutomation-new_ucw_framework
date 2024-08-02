package ucw.tests.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.models.soap.Envelope;
import ucw.models.soap.SBEntry;
import ucw.pages.SoapAPIBuilder;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;
import java.util.List;

import static org.testng.Assert.*;
import static ucw.enums.Environments.STAGING;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class StructuredQuoteTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(StructuredQuoteTest.class);
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-1115: CC//Structured Quote//Automation engine");
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Validate new parameter 'Claim type to allow automation' in script parameter " +
            "of the administration console section", priority = 1
    )
    public void test_claimTypeAutomation() {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickUtilities();
        claimCenter.atAdministrationPage().goToScriptParameter();
        boolean paramExist = claimCenter.atAdministrationPage().isParamExist("GTMotive_AutoProcess_PSA");
        assertTrue(paramExist, "Script parameter item GTMotive_AutoProcess_PSA does not exist");
    }

    @DataProvider
    public static Object[][] getAllClaimTypes() {
        return new String[][] {
                new String[]{"AutomationForClaimType_GVO_IMM"},
                new String[]{"AutomationForClaimType_GVO_DMM"},
                new String[]{"AutomationForClaimType_EGVO_CarMaintenance"},
                new String[]{"AutomationForClaimType_EGVO_Warranty"}
        };
    }

    @Test(description = "Validate that user will be able to enable/disable the automation for the claim Type " +
            "GVO IMM, GVO DMM, EGVO Car Maintenance, EGVO Warranty in new parameter 'Claim type to allow automation'",
            dataProvider = "getAllClaimTypes", priority = 2
    )
    public void test_editClaimTypes(String claimType) {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickUtilities();
        claimCenter.atAdministrationPage().goToScriptParameter();
        try {
            claimCenter.atAdministrationPage().openParam(claimType);
            claimCenter.atAdministrationPage().enableParam(false);
            String bitValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(bitValue, "No", "Bit value is not matched as expected.");

            claimCenter.atAdministrationPage().enableParam(true);
            bitValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(bitValue, "Yes", "Bit value is not matched as expected.");
        } finally {
            claimCenter.atAdministrationPage().backToScriptParameterSection();
        }
    }

    @DataProvider
    public static Object[][] getAllAutomationParamPerCountry() {
        return new String[][] {
                new String[]{"AutomaticPaymentProcess_France"},
                new String[]{"AutomaticPaymentProcess_Germany"},
                new String[]{"AutomaticPaymentProcess_Italy"},
                new String[]{"AutomaticPaymentProcess_Spain"}
        };
    }
    @Test(description = "Validate that user will be able to enable/disable the automation for each country in 'automation per country' parameter",
        dataProvider = "getAllAutomationParamPerCountry", priority = 3
    )
    public void test_automationPerCountry(String autoParamPerCountry) {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickUtilities();
        claimCenter.atAdministrationPage().goToScriptParameter();
        try {
            claimCenter.atAdministrationPage().openParam(autoParamPerCountry);
            claimCenter.atAdministrationPage().enableParam(false);
            String bitValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(bitValue, "No", "Bit value of " + autoParamPerCountry + " is not matched as expected.");

            claimCenter.atAdministrationPage().enableParam(true);
            bitValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(bitValue, "Yes", "Bit value of " + autoParamPerCountry + " is not matched as expected.");
        } finally {
            claimCenter.atAdministrationPage().backToScriptParameterSection();
        }
    }

    @Test(description = "Validate that user will be able to tune/edit the claim cost in 'Claim cost (threshold_cost_warranty)' parameter",
            priority = 4)
    public void test_thresholdCostWarranty() {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickUtilities();
        claimCenter.atAdministrationPage().goToScriptParameter();
        try {
            claimCenter.atAdministrationPage().openParam("threshold_cost_warranty");
            String originalValue = claimCenter.atAdministrationPage().getParamValue();
            claimCenter.atAdministrationPage().editParamValue("400");
            String updatedValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(updatedValue, "400", "Bit value of threshold_cost_warranty is not matched as expected.");

            claimCenter.atAdministrationPage().editParamValue(originalValue);
            updatedValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(updatedValue, originalValue, "Bit value of threshold_cost_warranty is not matched as expected.");
        } finally {
            claimCenter.atAdministrationPage().backToScriptParameterSection();
        }
    }

    @Test(description = "Validate the new section repairers rating in Administration section", priority = 5)
    public void test_repairerRatingSection() {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickBusinessSettings();
        claimCenter.atAdministrationPage().clickRepairerRatingSection();
        boolean listDisplay = claimCenter.atAdministrationPage().isRepairerListDisplay();
        assertTrue(listDisplay, "Repairer's Rating list does not display as expected");
    }

    @Test(description = "Validate that Adjuster will be able to view and edit the Repairer's rating details " +
            "as Repairer Rating 'Yes' or 'No' in Administration Console", priority = 6)
    public void test_editRepairerRating() {
        String repairer = basePage.getDataObject().getProducerCode();
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickBusinessSettings();
        try {
            claimCenter.atAdministrationPage().enableRating( false);
            String currentRatingStatus = claimCenter.atAdministrationPage().getCurrentRatingStatus(repairer);
            assertEquals(currentRatingStatus, "No", "Current repairer's rating status is not matched as expected");
        } finally {
            claimCenter.atAdministrationPage().enableRating(true);
            String currentRatingStatus = claimCenter.atAdministrationPage().getCurrentRatingStatus(repairer);
            assertEquals(currentRatingStatus, "Yes", "Current repairer's rating status is not matched as expected");
        }
    }


    @DataProvider
    public static Object[][] getAllThresholdItems() {
        return new String[][] {
                new String[]{"threshold1"},
                new String[]{"threshold2"},
                new String[]{"threshold3"},
                new String[]{"threshold4"}
        };
    }

    @Test(description = " Validating user should be able to update the values of the threshold 1,2,3 and 4 values " +
            "as ‘Automation threshold value’ in admin console", dataProvider = "getAllThresholdItems", priority = 7)
    public void test_editThresholdValues(String thresholdItem) {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().clickUtilities();
        claimCenter.atAdministrationPage().goToScriptParameter();
        try {
            claimCenter.atAdministrationPage().openParam(thresholdItem);
            String originalValue = claimCenter.atAdministrationPage().getParamValue();
            claimCenter.atAdministrationPage().editParamValue("100");
            String updatedValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(updatedValue, "100", "Value of " + thresholdItem + " is not matched as expected.");

            claimCenter.atAdministrationPage().editParamValue(originalValue);
            updatedValue = claimCenter.atAdministrationPage().getParamValue();
            assertEquals(updatedValue, originalValue, "Value of " + thresholdItem + " is not matched as expected.");
        } finally {
            claimCenter.atAdministrationPage().backToScriptParameterSection();
        }
    }

    @Test(description = "Validating that quote lines related to parts and operations coming from service box " +
            "should be mapped with the lines coming from GT motive and display accurate lines against each other in the Quote dashboard",
            priority = 8)
    public void test_partsAndOperationsLinesMapped() {
        if (!basePage.getCurrentEnvironment().equals(STAGING)) {
            throw new SkipException("This test only run on stag environment");
        }
        String redColor = "rgba(210, 105, 30, 1)";
        SoapAPIBuilder apiBuilder = basePage.initSoapApi();
        apiBuilder.setBodyFrom("src/test/resources/cc/vehicle_dmg_parts.xml");
        apiBuilder.sendRequest();
        apiBuilder.printResponseAsString();
        String returnedUrl = apiBuilder.getReturnedUrl();

        Portal portal = basePage.loginPortal();
        portal.navigateToUrl(returnedUrl);
        portal.atFileClaimPage().clickNextAfterSelectPolicy();
        portal.atFileClaimPage().fillLostDetails();
        portal.atFileClaimPage().clickAgreeOnSBPopup();
        portal.atFileClaimPage().uploadDocument();
        portal.atFileClaimPage().addPrimaryContactDetails();
        portal.atFileClaimPage().submit();
        assertTrue(portal.atFileClaimPage().isClaimCreated(), "Claim is not created successfully in Portal");

        basePage.switchToClaimCenterTab();
        claimCenter.atSearchPage().searchClaim();
        claimCenter.atClaimPage().goToServices();
        Envelope request = apiBuilder.getRequest();
        List<SBEntry> expectedParts = request.getBody().getSetServiceQuotePartsandOpt().getSerQuoteData().getParts();
        List<SBEntry> expectedOperations = request.getBody().getSetServiceQuotePartsandOpt().getSerQuoteData().getOperations();
        List<SBEntry> actualParts = claimCenter.atClaimPage().getParts();
        List<SBEntry> actualOperations = claimCenter.atClaimPage().getOperations();
        assertEquals(actualParts.size(), expectedParts.size(), "Number of added parts are not matched with expected");
        for (SBEntry actualPart : actualParts) {
            SBEntry expectedEntry = null;
            for (SBEntry expectedPart : expectedParts) {
                if (actualPart.getComment().equals(expectedPart.getComment())) {
                    expectedEntry = expectedPart;
                    break;
                }
            }
            assertNotNull(expectedEntry);
            logger.info("Compare all values of part ref " + expectedEntry.getRef());
            if (!actualPart.getDescription().isEmpty()) {
                assertEquals(actualPart.getDescriptionStyle(), redColor, "Description is not highlighted in red. Actual style value is " + actualPart.getDescriptionStyle());
            }
            assertEquals(actualPart.getRef(), expectedEntry.getRef(), "Part entry ref is not match as expected");
            assertEquals(actualPart.getDescription(), expectedEntry.getDescription(), "Part entry description is not match as expected");
            assertEquals(actualPart.getComment(), expectedEntry.getComment(), "Part entry Comment is not match as expected");
            assertEquals(actualPart.getQuantity(), expectedEntry.getQuantity(), "Part entry Quantity is not match as expected");
            assertEquals(actualPart.getUnitaryPrice().replace(" €", ""), expectedEntry.getUnitaryPrice(), "Part entry Unitary price is not match as expected");
            assertEquals(actualPart.getPriceTTC().replace(" €", "").replace(",", "."), expectedEntry.getPriceTTC(), "Part entry Price TTC is not match as expected");
        }
        assertEquals(actualOperations.size(), expectedOperations.size(), "Number of added operations are not matched with expected");

        for (SBEntry actualOperation : actualOperations) {
            SBEntry expectedEntry = null;
            for (SBEntry expectedOperation : expectedOperations) {
                if (actualOperation.getComment().equals(expectedOperation.getComment())) {
                    expectedEntry = expectedOperation;
                    break;
                }
            }
            assertNotNull(expectedEntry);
            logger.info("Compare all values of operation ref " + expectedEntry.getRef());
            if (!actualOperation.getDescription().isEmpty()) {
                assertEquals(actualOperation.getDescriptionStyle(), redColor, "Description is not highlighted in red");
            }
            assertEquals(actualOperation.getRef(), expectedEntry.getRef(), "Operation entry ref is not match as expected");
            assertEquals(actualOperation.getDescription(), expectedEntry.getDescription(), "Operation entry description is not match as expected");
            assertEquals(actualOperation.getComment(), expectedEntry.getComment(), "Operation entry Comment is not match as expected");
            assertEquals(actualOperation.getQuantity(), expectedEntry.getQuantity(), "Operation entry Quantity is not match as expected");
            assertEquals(actualOperation.getUnitaryPrice().replace(" €", ""), expectedEntry.getUnitaryPrice(), "Operation entry Unitary price is not match as expected");
            assertEquals(actualOperation.getPriceTTC().replace(" €", "").replace(",", "."), expectedEntry.getPriceTTC(), "Operation entry Price TTC is not match as expected");
        }
        float serviceBoxPrice = claimCenter.atClaimPage().getServiceBoxPrice();
        float gtMotivePrice = claimCenter.atClaimPage().getGTMotivePrice();
        String sbPriceColor = claimCenter.atClaimPage().getServiceBoxPriceColor();
        if (serviceBoxPrice > gtMotivePrice) {
            assertEquals(sbPriceColor, redColor, "Service box price is not highlighted in red. Actual service box style is " + sbPriceColor);
        } else {
            assertNotEquals(sbPriceColor, redColor, "Service box price is highlighted in red. Actual service box style is " + sbPriceColor);
        }
        assertTrue(claimCenter.atClaimPage().isGTMotiveWarrantyCheckDisplay(), "GT Motive Warranty Check label isn't shown");
        assertTrue(claimCenter.atClaimPage().isPreviousWarrantyClaimOnSameVINDisplay(), "Previous warranty claim on same VIN label isn't shown");
    }
}
