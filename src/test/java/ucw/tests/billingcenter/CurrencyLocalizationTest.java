package ucw.tests.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-121
public class CurrencyLocalizationTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(CurrencyLocalizationTest.class);
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToBillingCenter() {
        startTest("Billing Center currency localization");
        billingCenter = basePage.loginBillingCenter();
    }

    @Test(description = "Billing Center currency localization")
    public void test_currencyLocalization() {
        billingCenter.navigateToDesktopPage();
        assertTrue(billingCenter.atDesktopPage().validateCurrency(), "Currency localization validation in Billing Center is failed");
        billingCenter.navigateToAccountsPage();
        String legendLabel = billingCenter.atAccountsPage().getLegendLabel();
        logger.info("Legend label is " + legendLabel);
        assertTrue(legendLabel.contains("€"), "Currency symbol validation in Billing Center is failed");
    }
}
