package ucw.tests.contactmanager;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.contactmanager.ContactManager;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Person;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class CMAddressTest extends BaseTest {
    private ContactManager contactManager;

    @BeforeClass
    public void goToContactManager() {
        startTest("Address validation in Contact Manager");
        contactManager = basePage.loginContactManager();
    }

    @Test
    public void test_addressValidation() {
        contactManager.selectFirstName("CMFName")
                .selectTags("Policy Person")
                .setAccountTypeAs(Person);
        contactManager.createContact();
        assertTrue(contactManager.validateAddressTab(), "Address tab validation in Contact Manager is failed");
    }
}
