package ucw.tests.billingcenter;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.models.testdata.LocalizationObject;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;
import ucw.utils.TestDataGenerator;
import java.util.List;

import static ucw.enums.Languages.ENGLISH;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-123
public class LanguageLocalizationTest extends BaseTest {
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToBillingCenter() {
        startTest("Billing Center language localize validation");
        billingCenter = basePage.loginBillingCenter();
    }

    @AfterClass
    public void revertLanguageBackToEnglish() {
        billingCenter.setLanguage(ENGLISH);
    }

    @Test(description = "Language packs validation")
    public void test_languagePack() {
        String dataFile = "bc/language_localization.json";
        List<LocalizationObject> languages = TestDataGenerator.loadLocalizationData(dataFile);
        billingCenter.navigateToAccountSearch();
        billingCenter.atSearchPage().validateLanguagePack(languages);
    }
}
