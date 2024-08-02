package ucw.tests.claimcenter;

import org.testng.annotations.*;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Spain;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1285
public class ClaimExportDataTest extends BaseTest {
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-1285: Export data in Claims");
        claimCenter = basePage.loginClaimCenter();
        basePage.selectCountry(Spain)
                .selectProducerCode("010015C");
    }

    @Test(description = "Claim export data")
    public void test_printData() {
        claimCenter.navigateToAdministrationPage();
        claimCenter.atAdministrationPage().printReport();
        assertTrue(claimCenter.atAdministrationPage().isDataExported("Print.csv"), "User data is not exported correctly");
    }
}
