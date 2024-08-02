package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1843
public class AccountCreationWithSpecialChars extends BaseTest {
    private PolicyCenter policyCenter;
    private static final String[] names = new String[]{
            "Schäfer", "Schäfler", "Jäschke", "Nägele", "Weiß", "Strauß", "Müller",
            "Göring", "Stöber", "Köhne", "Schönborn", "Stüber", "Jünemann", "Bülow",
            "Örtel", "Ösling", "Öbel"
    };
    private static final String[] dealerNames = new String[]{"Jäschken", "Weißv", "Müllerq", "Göringe",
            "Stöberq", "Köhnel", "Schönborng", "Stübert", "Jünemannk", "Bülowe", "Örtell", "Öslingh", "Öbelina"
    };
    private static final String[] chars = "abcdefghijklmnopqrstuvwxyz".split("");

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1843: Accept the special characters for all countries while creating account");
        basePage.selectProducerCode("MATION");
        policyCenter = basePage.loginPolicyCenter();
    }

    @DataProvider
    public static Object[][] getData() {
        return new Object[][] {
                new Object[] {France},
                new Object[] {Italy},
                new Object[] {Spain},
                new Object[] {Germany}
        };
    }

    @Test(description = "Able to create new account with special characters for all countries in Policy Center", dataProvider = "getData")
    public void test_accountCreation_policyCenter(Countries country) {
        policyCenter.selectCompanyName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)])
                .selectContactFirstName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)])
                .selectContactLastName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)])
                .selectCountry(country);

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @DataProvider
    public static Object[][] getCountries() {
        return new Countries[][] {
                new Countries[] {France},
                new Countries[] {Spain},
                new Countries[] {Italy},
                new Countries[] {Germany}
        };
    }

    @Test(description = "Able to use special characters to edit name and address of the dealers coming from Arcad", dataProvider = "getCountries")
    public void test_editDealerInfo(Countries country) {
        policyCenter.selectDealerNameUpdated(dealerNames[rand.nextInt(dealerNames.length)])
                .selectDealerProvince(names[rand.nextInt(names.length)])
                .selectDealerAddress(names[rand.nextInt(names.length)])
                .selectDealerAddress2(names[rand.nextInt(names.length)])
                .selectDealerCity(names[rand.nextInt(names.length)])
                .selectCountry(country);

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().searchDealer();
        try {
            policyCenter.atAdministrationPage().editDealerBasicsInfo();
            assertTrue(policyCenter.atAdministrationPage().isDealerUpdated(), "Unable to update dealer coming from Arcad with special characters");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //revert dealer name back to normal
            policyCenter.atAdministrationPage().clickEditDealer();
            policyCenter.atAdministrationPage().editDealerName(policyCenter.getDataObject().getProducerCode());
        }
    }

    @Test(description = "Able to use special characters to edit name of the users coming from LDAP")
    public void test_editUserInfo() {
        policyCenter.selectUserFirstName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)])
                .selectUserMiddleName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)])
                .selectUserLastName(names[rand.nextInt(names.length)] + chars[rand.nextInt(chars.length)]);

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().searchUser(basePage.getConfig().getPortalAccount().split("/")[0]);
        policyCenter.atAdministrationPage().editUserBasicsInfo();
        assertTrue(policyCenter.atAdministrationPage().isUserUpdated(), "Unable to update user coming from LDAP with special characters");
    }
}
