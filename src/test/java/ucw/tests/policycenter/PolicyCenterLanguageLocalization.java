package ucw.tests.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.models.testdata.LocalizationObject;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;
import ucw.utils.TestDataGenerator;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Languages.ENGLISH;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-205
public class PolicyCenterLanguageLocalization extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PolicyCenterLanguageLocalization.class);
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("Policy Center language localization");
        logger.info("Test environment initializing");
        policyCenter = basePage.loginPolicyCenter();
    }

    @AfterMethod
    public void revertLanguageBackToEnglish() {
        policyCenter.setLanguage(ENGLISH);
    }

    @Test(description = "Language packs validation in Policy Center")
    public void test_languagePack() {
        String dataFile = "pc/language_localization.json";
        List<LocalizationObject> languages = TestDataGenerator.loadLocalizationData(dataFile);
        policyCenter.atSearchPage().searchHandler("Accounts");
        for (LocalizationObject language : languages) {
            policyCenter.setLanguage(language.getLanguage());
            assertTrue(policyCenter.atSearchPage().validateTextsOfLanguage(language));
        }
    }
}
