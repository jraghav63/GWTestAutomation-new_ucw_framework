package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import ucw.models.testdata.LocalizationObject;

import java.util.List;

import static ucw.enums.AccountType.Company;
import static ucw.enums.AccountType.Person;


public class SearchPage extends BillingCenter {
    private static final Logger logger = LogManager.getLogger(SearchPage.class);
    private static final Title searchContTitleTxt = Title.xpath("//*[@id='ContactSearch:ContactSearchScreen:ttlBar']");
    private static final Link contInSearchMenuTxt = Link.xpath("//*[@id='TabBar:SearchTab:SearchGroup_ContactSearch-textEl']");
    private static final Dropdown bcTypeTxt = Dropdown.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:ContactType-inputEl']");
    private static final TextBox bcFirstNameTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:NameInputSet:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox bcLastNameTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:NameInputSet:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Dropdown bcCountryTxt = Dropdown.xpath("//input[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:globalAddressContainer:GlobalAddressInputSet:Country-inputEl']");
    private static final Button searchButton = Button.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Label contactInUITxt = Label.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchResultsLV-body']//td[2]//div");
    private static final TextBox companyNameSearchTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:NameInputSet:GlobalContactNameInputSet:Name-inputEl']");
    private static final Button resetButton = Button.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Reset']");
    private static final Label accFirstNameTxt = Label.id("AccountSearch:AccountSearchScreen:AccountSearchDV:ContactCriteriaInputSet:GlobalPersonNameInputSet:FirstName-labelEl");
    private static final Label accLstNameTxt = Label.id("AccountSearch:AccountSearchScreen:AccountSearchDV:ContactCriteriaInputSet:GlobalPersonNameInputSet:LastName-labelEl");
    private static final Label accCountryTxt = Label.id("AccountSearch:AccountSearchScreen:AccountSearchDV:ContactCriteriaInputSet:globalAddressContainer:GlobalAddressInputSet:Country-labelEl");
    private static final Label accSearchTxt = Label.id("AccountSearch:AccountSearchScreen:AccountSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search");
    private static final Title searchAccTitleTxt = Title.id("AccountSearch:AccountSearchScreen:ttlBar");

    public boolean searchPCContacts() {
        logger.info("Searching for PC " + accountType + " contact in Billing Center");
        boolean result = false;
        click(contInSearchMenuTxt);
        waitForElementVisible(searchContTitleTxt);

        setDropDown(bcTypeTxt, String.valueOf(accountType));
        String pcContactInUI = null;
        if (accountType.equals(Person)) {
            logger.info("Search contact");
            waitForElementVisible(bcFirstNameTxt);
            sendText(bcFirstNameTxt, dataObject.getContactFirstName());
            sendText(bcLastNameTxt, dataObject.getContactLastName());
            setDropDown(bcCountryTxt, dataObject.getCountry());
            click(searchButton);
            pcContactInUI = getText(contactInUITxt);
            result = pcContactInUI.equals(dataObject.getFullName());
        } else if (accountType.equals(Company)) {
            sendText(companyNameSearchTxt, dataObject.getCompanyName());
            click(searchButton);
            pcContactInUI = getText(contactInUITxt);
            result = pcContactInUI.equals(dataObject.getCompanyName());
        }
        if (result) {
            logger.info("Policy Center " + accountType + " contact " + pcContactInUI + " is found in Billing Center");
            addStepInfoToReport("Policy Center " + accountType + " contact " + pcContactInUI + " is found in Billing Center");
        } else {
            logger.info("Policy Center " + accountType + " contact " + pcContactInUI + " is not found in Billing Center");
            addStepInfoToReport("Policy Center " + accountType + " contact " + pcContactInUI + " is not found in Billing Center");
        }
        click(resetButton);
        return result;
    }

    public boolean searchCMContacts() {
        logger.info("Searching for CM " + accountType + " contact in Billing Center");
        boolean result = false;
        String pcContactInUI = null;
        waitForElementVisible(bcTypeTxt);
        if (accountType.equals(Person)) {
            setDropDown(bcTypeTxt, String.valueOf(accountType));
            sendText(bcFirstNameTxt, dataObject.getContactManager().getFirstName());
            sendText(bcLastNameTxt, dataObject.getContactManager().getLastName());
            click(searchButton);
            pcContactInUI = getText(contactInUITxt);
            result = pcContactInUI.equals(dataObject.getContactManager().getFullName());
        } else if (accountType.equals(Company)) {
            setDropDown(bcTypeTxt, String.valueOf(accountType));
            sendText(companyNameSearchTxt, dataObject.getContactManager().getCompanyName());
            click(searchButton);
            pcContactInUI = getText(contactInUITxt);
            result = pcContactInUI.equals(dataObject.getContactManager().getCompanyName());
        }
        logger.info("PC contact in UI is " + pcContactInUI);
        logger.info("PC contact from input data file is " + dataObject.getContactManager().getCompanyName());
        clickPageBody();
        if (result) {
            logger.info("Contact Manager " + accountType + " contact " + pcContactInUI + " is found in Billing Center");
            addStepInfoToReport("Contact Manager " + accountType + " contact " + pcContactInUI + " is found in Billing Center");
        } else {
            logger.info("Contact Manager " + accountType + " contact " + pcContactInUI + " is not found in Billing Center");
            addStepInfoToReport("Contact Manager " + accountType + " contact " + pcContactInUI + " is not found in Billing Center");
        }
        click(resetButton);
        return result;
    }

    public void validateLanguagePack(List<LocalizationObject> languages) {
        logger.info("Billing Center language localization validation starts");
        for (LocalizationObject language : languages) {
            setLanguage(language.getLanguage());

            // main menu check
            int noOfMenuItems = language.getMainMenu().split(",").length;
            String[] menuItems = language.getMainMenu().split(",");
            for (int i = 0; i < noOfMenuItems; i++) {
                assert findElements(menuItemPath).get(i).getText().contains(menuItems[i]);
            }
            // account page check
            waitForElementVisible(searchAccTitleTxt);
            String[] checkWords = language.getSearchAccountsPage().split(",");
            assert getText(searchAccTitleTxt).contains(checkWords[0]);
            assert getText(accFirstNameTxt).contains(checkWords[1]);
            assert getText(accLstNameTxt).contains(checkWords[2]);
            assert getText(accCountryTxt).contains(checkWords[3]);
            assert getText(accSearchTxt).contains(checkWords[4]);
            logger.info(language + " language is available in language menu");
        }
    }
}
