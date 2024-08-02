package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import ucw.pages.BasePage;

public class PolicyCenter extends BasePage {
    private static final Logger logger = LogManager.getLogger(PolicyCenter.class);
    public static AccountPage accountPage;
    public static PolicyPage policyPage;
    protected static SearchPage searchPage;
    private static AdministrationPage administrationPage;
    private static InternalSystemPage systemPage;
    private static final Link newAccountTxt = Link.xpath("//*[@id='TabBar:AccountTab:AccountTab_NewAccount-textEl']");
    protected static final Title accSummaryTxt = Title.id("AccountFile_Summary:AccountFile_SummaryScreen:ttlBar");
    protected static final TextBox searchPolTxt = TextBox.xpath("//input[@id='TabBar:PolicyTab:PolicyTab_PolicyRetrievalItem-inputEl']");
    protected static final Button searchPolButton = Button.xpath("//div[@id='TabBar:PolicyTab:PolicyTab_PolicyRetrievalItem_Button']");
    protected static final Tab policyTab = Tab.id("TabBar:PolicyTab");
    private static final Button accountMenuButton = Button.id("TabBar:AccountTab-btnInnerEl");
    protected static final Tab desktopTab = Tab.id("TabBar:DesktopTab-btnWrap");

    public PolicyCenter selectCompanyName(String value) {
        dataObject.setCompanyName(value);
        return this;
    }

    public PolicyCenter selectContactFirstName(String value) {
        dataObject.setContactFirstName(value);
        return this;
    }

    public PolicyCenter selectContactLastName(String value) {
        dataObject.setContactLastName(value);
        return this;
    }

    public PolicyCenter selectDealerNameUpdated(String value) {
        dataObject.getPolicyCenter().setDealerNameUpdated(value);
        return this;
    }

    public PolicyCenter selectDealerProvince(String value) {
        dataObject.getPolicyCenter().setDealerProvince(value);
        return this;
    }

    public PolicyCenter selectDealerAddress(String value) {
        dataObject.getPolicyCenter().setDealerAddress(value);
        return this;
    }

    public PolicyCenter selectDealerAddress2(String value) {
        dataObject.getPolicyCenter().setDealerAddress2(value);
        return this;
    }

    public PolicyCenter selectDealerCity(String value) {
        dataObject.getPolicyCenter().setDealerCity(value);
        return this;
    }

    public PolicyCenter selectUserFirstName(String value) {
        dataObject.getPolicyCenter().setUserFirstName(value);
        return this;
    }

    public PolicyCenter selectUserMiddleName(String value) {
        dataObject.getPolicyCenter().setUserMiddleName(value);
        return this;
    }

    public PolicyCenter selectUserLastName(String value) {
        dataObject.getPolicyCenter().setUserLastName(value);
        return this;
    }

    public AccountPage atAccountPage() {
        if (accountPage == null) {
            accountPage = new AccountPage();
        }
        return accountPage;
    }

    public PolicyPage atPolicyPage() {
        if (policyPage == null) {
            policyPage = new PolicyPage();
        }
        return policyPage;
    }

    public AdministrationPage atAdministrationPage() {
        if (administrationPage == null) {
            administrationPage = new AdministrationPage();
        }
        return administrationPage;
    }

    public SearchPage atSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public InternalSystemPage atInternalSystemPage() {
        if (systemPage == null) {
            systemPage = new InternalSystemPage();
        }
        return systemPage;
    }

    public void navigateToAccountPage() {
        clickMenu("Account");
        waitForElementVisible(newAccountTxt);
        click(newAccountTxt);
        logger.info("User is navigated to Account page");
        addStepInfoToReport("User is navigated to Account page");
    }

    public void navigateToAdministrationPage() {
        click(adminTabButton);
        logger.info("User is navigated to Administrator page");
        addStepInfoToReport("User is navigated to Administrator page");
    }

    public void searchPolicy() {
        clickMenu("Policy");
        sendTextOnly(searchPolTxt, dataObject.getPolicyNumber());
        click(searchPolButton);
        atPolicyPage().waitForSummaryPage();
    }

    public void goBackToAccSummary() {
        click(accountMenuButton);
        waitForElementVisible(accSummaryTxt);
        addStepInfoToReport("Go to account summary page in Policy Center");
    }

    public String getExistingVINErrorMsg() {
        goBackToAccSummary();
        accountPage.navigateToSubmissionPage();
        accountPage.selectGVO();
        policyPage.navigateToPUWVehicleScreen();
        policyPage.addVehicleBody();
        String existingVIN = dataObject.getPolicyCenter().getVin();
        policyPage.inputVin(existingVIN);
        return policyPage.getErrorMessageAtVehicleScreen();
    }
}
