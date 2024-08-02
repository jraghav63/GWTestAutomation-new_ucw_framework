package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Button;
import ucw.elements.Tab;
import ucw.elements.Title;
import ucw.pages.BasePage;

public class BillingCenter extends BasePage {
    private static final Logger logger = LogManager.getLogger(BillingCenter.class);
    private static SearchPage searchPage;
    private static AccountsPage accountsPage;
    private static PolicyPage policyPage;
    private static AdministrationPage administrationPage;
    private static DesktopPage desktopPage;
    private static final Tab administrationTab = Tab.xpath("//*[@id='TabBar:AdministrationTab-btnInnerEl']");
    protected static final Tab policiesTab = Tab.id("TabBar:PoliciesTab-btnWrap");
    private static final Tab searchTab = Tab.id("TabBar:SearchTab-btnInnerEl");
    private static final Title userSearchTxt = Title.id("UserSearch:UserSearchScreen:ttlBar");
    private static final Button accountButton = Button.xpath("//span[@id='TabBar:AccountsTab-btnInnerEl']");
    private static final Title accountTitleTxt = Title.id("Accounts:AccountSearchScreen:ttlBar");
    private static final Tab desktopTab = Tab.xpath("//*[@id='TabBar:DesktopTab-btnInnerEl']");

    public SearchPage atSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public AccountsPage atAccountsPage() {
        if (accountsPage == null) {
            accountsPage = new AccountsPage();
        }
        return accountsPage;
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

    public DesktopPage atDesktopPage() {
        if (desktopPage == null) {
            desktopPage = new DesktopPage();
        }
        return desktopPage;
    }

    public void navigateToAccountsPage() {
        waitForElementVisible(accountButton);
        click(accountButton);
        waitForElementVisible(accountTitleTxt);
        logger.info("User is navigated to Accounts page");
        addStepInfoToReport("User is navigated to Accounts page");
    }

    public void navigateToSearchPage() {
        clickMenu("TabBar:SearchTab-btnWrap");
        logger.info("User is navigated to Search page");
        addStepInfoToReport("User is navigated to Search page");
    }

    public void navigateToAccountSearch() {
        click(searchTab);
    }

    public void navigateToPoliciesPage() {
        clickMenu(policiesTab);
        logger.info("User is navigated to Policy page");
        addStepInfoToReport("User is navigated to Policy page");
    }

    public void navigateToAdministrationPage() {
        click(administrationTab);
        waitForElementVisible(userSearchTxt);
        logger.info("User is navigated to Administration page");
        addStepInfoToReport("User is navigated to Administration page");
    }

    public void navigateToDesktopPage() {
        waitForElementVisible(desktopTab);
        click(desktopTab);
        logger.info("User is navigated to Desktop page");
        addStepInfoToReport("User is navigated to Desktop page");
    }
}
