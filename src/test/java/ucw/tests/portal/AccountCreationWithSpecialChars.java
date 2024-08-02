package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.ContactType;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Commercial;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1843
public class AccountCreationWithSpecialChars extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;
    private static final String[] names = new String[] {
            "Schäfer", "Schäfler", "Jäschke", "Nägele", "Weiß", "Strauß", "Müller",
            "Göring", "Stöber", "Köhne", "Schönborn", "Stüber", "Jünemann", "Bülow",
            "Örtel", "Ösling", "Öbel"
    };
    private static final String[] chars = "abcdefghijklmnopqrstuvwxyz".split("");

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1843: Accept the special characters for all countries while creating account");
        policyCenter = basePage.loginPolicyCenter();
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED);
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

    @DataProvider
    public static Object[][] getData() {
        return new Object[][] {
                new Object[] {France,  Commercial},
                new Object[] {France,  Personal  },
                new Object[] {Italy,   Commercial},
                new Object[] {Italy,   Personal  },
                new Object[] {Spain,   Commercial},
                new Object[] {Spain,   Personal  },
                new Object[] {Germany, Commercial},
                new Object[] {Germany, Personal  }
        };
    }

    @Test(description = "Able to create new account with special characters for all countries in Portal", dataProvider = "getData")
    public void test_accountCreation_portal(Countries country, ContactType contactType) {
        portal.getDataObject().getPortal().setCompany(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)]);
        portal.getDataObject().getPortal().setFirstName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)]);
        portal.getDataObject().getPortal().setLastName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)]);
        portal.selectCountry(country);

        basePage.switchToPortalTab();
        portal.navigateToSubmissionPage();
        portal.atSubmissionPage().createNewContact(contactType);
        portal.returnHome();
    }
}
