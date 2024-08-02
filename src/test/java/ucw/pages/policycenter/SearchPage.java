package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import ucw.models.testdata.LocalizationObject;

import static ucw.enums.AccountType.Company;
import static ucw.enums.ContactType.Personal;

public class SearchPage extends PolicyCenter {

    private static final Logger logger = LogManager.getLogger(SearchPage.class);
    private static final Title contactSearchTitle = Title.id("ContactSearch:ContactSearchScreen:ttlBar");
    private static final Dropdown contactTypeDropdown = Dropdown.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDetail_PSADV:ContactType-inputEl']");
    private static final TextBox companyNameTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDetail_PSADV:BasicContactInfoInputSet:GlobalContactNameInputSet:Name-inputEl']");
    private static final TextBox firstNameTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDetail_PSADV:BasicContactInfoInputSet:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox lastNameTxt = TextBox.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDetail_PSADV:BasicContactInfoInputSet:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Button searchButton = Button.xpath("//*[@id='ContactSearch:ContactSearchScreen:ContactSearchDetail_PSADV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Link searchResults = Link.xpath("//div[@id='ContactSearch:ContactSearchScreen:ContactSearchResultsLV-body']//td[2]//a");
    private static final Label policyNumber = Label.xpath("//*[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_PolicyDV:PolicyNumber-inputEl']");
    private static final TextBox accountSearchBox = TextBox.xpath("//input[@id='TabBar:AccountTab:AccountTab_AccountNumberSearchItem-inputEl']");
    private static final Button accountSearchButton = Button.xpath("//div[@id='TabBar:AccountTab:AccountTab_AccountNumberSearchItem_Button']");
    private static final TextBox searchAccTitleTxt = TextBox.id("AccountSearch:AccountSearch_PSAScreen:ttlBar");
    private static final TextBox accFirstNameTxt = TextBox.id("AccountSearch:AccountSearch_PSAScreen:AccountSearchDV:GlobalPersonNameInputSet:FirstName-labelEl");
    private static final TextBox accLastNameTxt = TextBox.id("AccountSearch:AccountSearch_PSAScreen:AccountSearchDV:GlobalPersonNameInputSet:LastName-labelEl");
    private static final TextBox accCountryTxt = TextBox.id("AccountSearch:AccountSearch_PSAScreen:AccountSearchDV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:Country-labelEl");
    private static final TextBox accCityTxt = TextBox.id("AccountSearch:AccountSearch_PSAScreen:AccountSearchDV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:City-labelEl");
    private static final TextBox policyQuoteTxt = TextBox.id("SubmissionWizard:SubmissionWizard_QuoteScreen:ttlBar");
    private static final TextBox subSearchTxt = TextBox.xpath("//*[@id='TabBar:PolicyTab:PolicyTab_SubmissionNumberSearchItem-inputEl']");
    private static final Button subSearchButton = Button.xpath("//*[@id='TabBar:PolicyTab:PolicyTab_SubmissionNumberSearchItem_Button']");

    public void searchQuote() {
        if (contactType.equals(Personal)) {
            searchPersonalQuote();
        } else {
            searchCommQuoteInPC();
        }
    }

    public boolean isQuoteFound() {
        String quoteValueInUI = searchPage.getQuoteInUI();
        String expectedQuoteValue = (contactType.equals(Personal)) ? dataObject.getPortal().getPersonalQuoteNo() : dataObject.getPortal().getCommQuoteNo();
        boolean result = expectedQuoteValue.equalsIgnoreCase(quoteValueInUI);
        if (result) {
            logger.info("The account quote number " + quoteValueInUI + " is found in Policy Center");
        }
        return result;
    }

    public boolean isPortalPolicyFound() {
        String policyNoInUI = searchPage.getPortalPolicyInUI();
        boolean result = dataObject.getPolicyNumber().equalsIgnoreCase(policyNoInUI);
        if (result) {
            logger.info("Portal policy #" + dataObject.getPolicyNumber() + " is found in Policy Center");
        }
        return result;
    }

    private String getCMCompanyName() {
        logger.info("Get CM company name " + dataObject.getContactManager().getCompanyName() + " from search panel");
        searchHandler("Contacts");
        waitForElementVisible(contactSearchTitle);
        setDropDown(contactTypeDropdown, "Company");
        sendText(companyNameTxt, dataObject.getContactManager().getCompanyName());
        click(searchButton);
        waitForElementVisible(searchResults);
        return getText(searchResults);
    }

    private String getCMPersonName() {
        logger.info("Get CM person name "
                + dataObject.getContactManager().getFullName()
                + " from search panel");
        searchHandler("Contacts");
        waitForElementVisible(contactSearchTitle);
        setDropDown(contactTypeDropdown, "Person");
        waitForElementVisible(firstNameTxt);
        sendText(firstNameTxt, dataObject.getContactManager().getFirstName());
        sendText(lastNameTxt, dataObject.getContactManager().getLastName());
        click(searchButton);
        waitForElementVisible(searchResults);
        return getText(searchResults);
    }

    public void searchHandler(String searchItem) {
        logger.info("Search handler " + searchItem);
        boolean isSearchShowOnMenu = !getAttributeValue(Tab.xpath("//a[@id='TabBar:SearchTab']"), "style").contains("display: none;");
        if (isSearchShowOnMenu) {
            clickMenu("TabBar:SearchTab-btnWrap");
            click(Link.xpath(
                    "//a[@id='TabBar:SearchTab:Search_PolicySearch-itemEl']/parent::div//parent::div//span[text()='"
                            + searchItem + "']"));
        } else {
            click(Button.id(":tabs-menu-trigger-btnIconEl"));
            hover(Button.xpath("//*[@eventid='TabBar:SearchTab']/a/span[text()='Search']"));
            click(Link.xpath("//div[@id='TabBar:SearchTab:Search_PolicySearch']/parent::div//span[text()='"
                    + searchItem + "']"));
        }
    }

    public void searchPersonalQuote() {
        logger.info("Searching for Person quote number " + dataObject.getPortal().getPersonalQuoteNo() + " in Policy Center");
        clickMenu("TabBar:PolicyTab-btnWrap");
        sendTextOnly(subSearchTxt, dataObject.getPortal().getPersonalQuoteNo());
        click(subSearchButton);
        waitForElementVisible(policyQuoteTxt);
    }

    public void searchCommQuoteInPC() {
        logger.info("Search for Commercial quote number in Portal Policy");
        clickMenu("TabBar:PolicyTab-btnWrap");
        sendTextOnly(subSearchTxt, dataObject.getPortal().getCommQuoteNo());
        click(subSearchButton);
        waitForElementVisible(policyQuoteTxt);

        Label polInUI = Label.xpath("//*[@id='SubmissionWizard:JobWizardInfoBar:PolicyNumber-btnInnerEl']//span[2]");
        String portPolicyInUI = getText(polInUI);
        logger.info("Commercial Policy number is : " + portPolicyInUI);
        dataObject.getPolicyCenter().setCommPolicyNo(portPolicyInUI);
        logger.info("Policy no in Portal is: " + dataObject.getPolicyCenter().getCommPolicyNo());
    }

    public String getQuoteInUI() {
        Label quoteInUI = Label.xpath("//*[@id='SubmissionWizard:SubmissionWizard_QuoteScreen:Quote_Summary_PSADV:JobNumber-inputEl']");
        return getText(quoteInUI);
    }

    public void searchPortalPolicy() {
        logger.info("Searching for Portal Policy number: " + dataObject.getPolicyNumber());
        clickMenu("TabBar:PolicyTab-btnWrap");
        waitForElementVisible(searchPolTxt);
        sendTextOnly(searchPolTxt, dataObject.getPolicyNumber());
        click(searchPolButton);
    }

    public String getPortalPolicyInUI() {
        return getText(policyNumber);
    }

    public void searchAccount() {
        logger.info("Searching for account " + dataObject.getAccountNumber() + " in Policy Center");
        clickMenu("TabBar:AccountTab-btnWrap");
        sendTextOnly(accountSearchBox, dataObject.getAccountNumber());
        click(accountSearchButton);
        if (accountPage == null) {
            accountPage = new AccountPage();
        }
        waitForElementVisible(accSummaryTxt);
        logger.info("Account summary is displayed");
    }

    public boolean validateTextsOfLanguage(LocalizationObject language) {
        boolean result = true;
        int noOfMenuItems = language.getMainMenu().split(",").length;
        String[] menuItems = language.getMainMenu().split(",");
        for (int i = 0; i < noOfMenuItems; i++) {
            String itemValue = findElements(menuItemPath).get(i).getText();
            logger.info("Actual item value is " + itemValue);
            logger.info("Expected item value is " + menuItems[i]);
            result &= itemValue.contains(menuItems[i]);
        }
        waitForElementVisible(searchAccTitleTxt);
        String[] checkWords = language.getSearchAccountsPage().split(",");

        result &= getText(searchAccTitleTxt).equals(checkWords[0]);
        result &= getText(accFirstNameTxt).equals(checkWords[1]);
        result &= getText(accLastNameTxt).equals(checkWords[2]);
        result &= getText(accCountryTxt).equals(checkWords[3]);
        result &= getText(accCityTxt).equals(checkWords[4]);
        logger.info(language.getLanguage() + " is available in language menu");
        return result;
    }

    public boolean searchCMContacts() {
        logger.info("Searching for CM " + accountType + " contact in Policy Center");
        boolean result;
        if (accountType.equals(Company)) {
            String actualCompanyName = searchPage.getCMCompanyName();
            result = actualCompanyName.equals(dataObject.getContactManager().getCompanyName());
        } else {
            String actualPersonName = searchPage.getCMPersonName();
            result = actualPersonName.equals(dataObject.getContactManager().getFirstName() + " " + dataObject.getContactManager().getLastName());
        }
        return result;
    }
}
