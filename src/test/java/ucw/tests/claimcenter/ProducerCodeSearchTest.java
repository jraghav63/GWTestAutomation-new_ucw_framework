package ucw.tests.claimcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Spain;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class ProducerCodeSearchTest extends BaseTest {
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-679: ADD INFORMATIONS ON BO CLAIMS CENTER ==>USERS tab");
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim producer code search validation")
    public void test_producerCodeSearch() {
        basePage.selectCountry(Spain)
                .selectProducerCode("010015C")
                .getDataObject().getClaimCenter().setCommercialName("AUTOMOVILES MOSANCAR, S.A.");
        claimCenter.navigateToAdministrationPage();
        assertTrue(claimCenter.atAdministrationPage().validateProducerCode(), "Producer code search validation in Claim Center is failed");
        claimCenter.navigateToAddressBook();
        assertTrue(claimCenter.atAddressBookPage().validateProducerCode(), "Producer code search validation in Address Book in Claim Center is failed");
    }
}
