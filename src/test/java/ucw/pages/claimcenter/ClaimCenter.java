package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import ucw.pages.BasePage;

public class ClaimCenter extends BasePage {
    private static final Logger logger = LogManager.getLogger(ClaimCenter.class);
    private static SearchPage searchPage;
    private static InternalSystemPage internalPage;
    private static AdministrationPage administrationPage;
    private static AddressBookPage addressBookPage;
    private static ClaimPage claimPage;
    private static final Link newClaimDdBtn = Link.xpath("//span[@id='TabBar:ClaimTab:ClaimTab_FNOLWizard-textEl']");
    private static final Button desktopButton = Button.xpath("//*[@id='TabBar:DesktopTab']");
    protected static final Tab addressBookTab = Tab.xpath("//*[@id='TabBar:AddressBookTab-btnInnerEl']");
    protected static final Title addressBookPageTxt = Title.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:ttlBar']");
    private static final Title defaultRootGroupTitle = Title.id("GroupDetailPage:GroupDetailScreen:ttlBar");
    private static final Table defaultGroupTable = Table.id("GroupDetailPage:GroupDetailScreen:GroupDetail_BasicCardTab:panelId-table");
    private static final Table activitiesTable = Table.id("DesktopActivities-table");

    public AdministrationPage atAdministrationPage() {
        if (administrationPage == null) {
            administrationPage = new AdministrationPage();
        }
        return administrationPage;
    }

    public AddressBookPage atAddressBookPage() {
        if (addressBookPage == null) {
            addressBookPage = new AddressBookPage();
        }
        return addressBookPage;
    }

    public InternalSystemPage atInternalSystemPage() {
        if (internalPage == null) {
            internalPage = new InternalSystemPage();
        }
        return internalPage;
    }

    public ClaimPage atClaimPage() {
        if (claimPage == null) {
            claimPage = new ClaimPage();
        }
        return claimPage;
    }

    public SearchPage atSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public void navigateToDesktopPage() {
        click(desktopButton);
        waitForElementVisible(activitiesTable);
        logger.info("Navigate to Desktop page");
        addStepInfoToReport("User is navigated to Desktop page");
    }

    public void navigateToAdministrationPage() {
        logger.info("Navigate to Administrator screen");
        click(adminTabButton);
        waitForElementVisible(defaultRootGroupTitle);
        waitForElementVisible(defaultGroupTable);
        addStepInfoToReport("User is navigated to Administrator screen");
    }

    public void navigateToAddressBook() {
        logger.info("Navigate to Address Book");
        click(addressBookTab);
        waitForElementVisible(addressBookPageTxt);
        addStepInfoToReport("User is navigated to Address Book");
    }

    public void navigateToInternalPage() {
        logger.info("Navigate to system internal page of Claim Center");
        pressAltShiftT();
        addStepInfoToReport("User is navigated to system internal page of Claim Center");
    }

    public void navigateToNewClaimPage() {
        logger.info("Navigate to New Claim page");
        clickMenu("TabBar:ClaimTab");
        click(newClaimDdBtn);
        addStepInfoToReport("User is navigated to New Claim page");
    }
}
