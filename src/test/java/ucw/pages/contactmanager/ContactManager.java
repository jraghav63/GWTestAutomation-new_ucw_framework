package ucw.pages.contactmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import ucw.elements.*;
import ucw.pages.BasePage;

import static ucw.enums.AccountType.Company;
import static ucw.enums.AccountType.Person;

public class ContactManager extends BasePage {
    private static final Logger logger = LogManager.getLogger(ContactManager.class);
    private static final Title searchTitleTxt = Title.id("ABContactSearch:ABContactSearchScreen:ttlBar");
    private static final Button actionButton = Button.xpath("//span[@id='ABContacts:ContactsMenuActions-btnInnerEl']");
    private static final Button newPersonButton = Button.xpath("//span[@id='ABContacts:ContactsMenuActions:newperson_PSA-textEl']");
    private static final Title personTitleTxt = Title.id("NewContact:ABContactDetailScreen:ttlBar");
    private static final Dropdown prefixDdTxt = Dropdown.xpath("//input[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:Prefix-inputEl']");
    private static final TextBox firstNameTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox lastNameTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Link policyPersonTxt = Link.xpath("//li[contains(@data-recordindex,'0')]");
    private static final Link tagsEmployeeTxt = Link.xpath("//li[contains(@data-recordindex,'0')]");
    private static final TextBox addressTxt = TextBox.id("NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl");
    private static final TextBox postalCodeTxt = TextBox.id("NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:PostalCode-inputEl");
    private static final TextBox cityTxt = TextBox.id("NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl");
    private static final Button updateButton = Button.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV_tb:Update-btnInnerEl']");
    private static final Title contactDetailTitleTxt = Title.xpath("//*[@id='ContactDetail:ABContactDetailScreen:ttlBar']");
    private static final TextBox searchCompanyNameTxt = TextBox.xpath("//*[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:GlobalContactNameInputSet:Name-inputEl']");
    private static final Link cmUpToSearchLink = Link.xpath("//*[@id='ContactDetail:ContactDetail_UpLink']");
    private static final Button newCompanyButton = Button.xpath("//*[@id='ABContacts:ContactsMenuActions:newcompany_PSA-textEl']");
    private static final TextBox companyNameTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:GlobalContactNameInputSet:Name-inputEl']");
    private static final Button tagsButton = Button.xpath("//li[contains(text(),'JV')]");
    private static final TextBox compAddressTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox compPostCodeTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:PostalCode-inputEl']");
    private static final TextBox compCityTxt = TextBox.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl']");
    private static final Button primaryContMenuIconButton = Button.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryContact:PrimaryContactMenuIcon']/img");
    private static final Label contactInfoMenu = Label.id("NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryContact:PrimaryContactMenuIcon-fieldMenu-innerCt");
    private static final Link personPrimContTxt = Link.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV:PrimaryContact:NewPersonOnlyPickerMenuItemSet:NewPersonOnlyPickerMenuItemSet_NewPersonMenuItem-itemEl']");
    private static final Dropdown contactInfoPrefixTxt = Dropdown.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:Prefix-inputEl']");
    private static final TextBox contactInfoFNameTxt = TextBox.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox contactInfoLNameTxt = TextBox.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Label contactInfoTagsPolPersonTxt = Label.xpath("//li[contains(text(),'Policy Person')]");
    private static final Label contactInfoTagsEmployeeTxt = Label.xpath("//li[contains(text(),'Employee')]");
    private static final TextBox contactInfoAddressTxt = TextBox.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox contactInfoPostCodeTxt = TextBox.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:PostalCode-inputEl']");
    private static final TextBox contactInfoPostCityTxt = TextBox.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:AddressOwnerInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl']");
    private static final Button contactInfoOkButton = Button.xpath("//*[@id='NewContactPopup:ABContactDetailScreen:ContactBasicsDV_tb:Update-btnInnerEl']");
    private static final Button newCompanyUpdateButton = Button.xpath("//*[@id='NewContact:ABContactDetailScreen:ContactBasicsDV_tb:Update-btnInnerEl']");
    private static final Button cmContactsTabButton = Button.xpath("//*[@id='TabBar:ContactsTab-btnInnerEl']");
    private static final Tab addressTab = Tab.xpath("//span[@id='ContactDetail:ABContactDetailScreen:AddressesCardTab-btnInnerEl']");
    private static final Dropdown cmSearchContTypeDdTxt = Dropdown.xpath("//input[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:ContactSubtype-inputEl']");
    private static final Button cmSearchContSearchButton = Button.xpath("//*[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Link cmSearchResultTxt = Link.xpath("//*[@id='ABContactSearch:ABContactSearchScreen:ContactSearchResultsLV:0:DisplayName']");
    private static final TextBox cmSearchContFirstNameTxtBx = TextBox.xpath("//input[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox cmSearchContLstNameTxtBx = TextBox.xpath("//input[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Button cmResetButton = Button.xpath("//*[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Reset']");
    private static final TextBox clientCodeTextbox = TextBox.xpath("//input[@id='ABContactSearch:ABContactSearchScreen:ContactSearchDV:producercode_psa-inputEl']");
    private static final CheckBox resultCheckbox = CheckBox.xpath("(//div[@id='ABContactSearch:ABContactSearchScreen:ContactSearchResultsLV-body']//td)[2]//img");
    private static final Button deleteButton = Button.id("ABContactSearch:ABContactSearchScreen:ContactSearchResultsLV_tb:ABContactSearchScreen_DeleteButton-btnInnerEl");

    public ContactManager selectFirstName(String name) {
        dataObject.getContactManager().setFirstName(name);
        return this;
    }

    public ContactManager selectTags(String tag) {
        dataObject.getContactManager().setTags(tag);
        return this;
    }

    public boolean validateContactFlow() {
        logger.info("Validate the contact flow to Contact Manager from Policy Center");

        boolean result = true;
        if (accountType.equals(Person)) {
            String pcFirstName = dataObject.getContactFirstName();
            String pcLastName = dataObject.getContactLastName();
            String bcFirstName = dataObject.getBillingCenter().getFirstName();
            String bcLastName = dataObject.getBillingCenter().getLastName();
            click(cmUpToSearchLink);
            result &= searchPersonContact(pcFirstName, pcLastName);
            result &= searchPersonContact(bcFirstName, bcLastName);
        } else {
            String pcCompanyName = dataObject.getCompanyName();
            String bcCompanyName = dataObject.getBillingCenter().getCompanyName();
            result &= searchCompanyContact(pcCompanyName);
            result &= searchCompanyContact(bcCompanyName);
        }
        return result;
    }

    public void createContact() {
        if (accountType.equals(Person)) {
            createPersonContact();
        } else {
            createCompanyContact();
        }
        addStepInfoToReport("New " + accountType + " account is created in Contact Manager");
    }

    public boolean validateAddressTab() {
        click(addressTab);
        String addressTabText = getText(addressTab);
        return addressTabText.equalsIgnoreCase("Addresses");
    }

    private void createCompanyContact() {
        logger.info("Create company contact in Contact Manager");
        click(actionButton);
        click(newCompanyButton);
        waitForElementVisible(personTitleTxt);
        String companyName = faker.company().name();
        logger.info("New Contact Manager company name is " + companyName);
        dataObject.getContactManager().setCompanyName(companyName);
        sendText(companyNameTxt, companyName);
        click(tagsButton);

        sendText(compAddressTxt, faker.address().streetAddress());
        sendText(compPostCodeTxt, rand.nextInt(10000, 99999));
        sendText(compCityTxt, faker.address().city());
        click(primaryContMenuIconButton);
        waitForElementVisible(contactInfoMenu);
        click(personPrimContTxt);
        addContactInfoForCompany();
        click(newCompanyUpdateButton);
        waitForElementVisible(contactDetailTitleTxt);
        click(cmContactsTabButton);
    }

    private void addContactInfoForCompany() {
        logger.info("Add contact information for company contact in Contact Manager");
        setDropDownRandomValue(contactInfoPrefixTxt);
        String firstName = faker.name().firstName().replace("'", "");
        sendText(contactInfoFNameTxt, firstName);
        dataObject.getContactManager().setFirstName(firstName);
        String lastName = faker.name().lastName().replace("'", "");
        sendText(contactInfoLNameTxt, lastName);
        dataObject.getContactManager().setLastName(lastName);
        if (dataObject.getContactManager().getTags().equalsIgnoreCase("Policy Person")) {
            click(contactInfoTagsPolPersonTxt);
        } else if (dataObject.getContactManager().getTags().equalsIgnoreCase("Employee")) {
            click(contactInfoTagsEmployeeTxt);
        }
        clickPageBody();
        sendText(contactInfoAddressTxt, faker.address().streetAddress());
        sendText(contactInfoPostCodeTxt, rand.nextInt(10000, 99999));
        sendText(contactInfoPostCityTxt, faker.address().city());
        click(contactInfoOkButton);
        waitForElement(personTitleTxt);
    }

    private void createPersonContact() {
        logger.info("Person contact creation starts");
        click(actionButton);
        click(newPersonButton);
        waitForElementVisible(personTitleTxt);
        setDropDownRandomValue(prefixDdTxt);
        String firstName = faker.name().firstName().replace("'", "");
        sendText(firstNameTxt, firstName);
        dataObject.getContactManager().setFirstName(firstName);
        String lastName = faker.name().lastName().replace("'", "");
        sendText(lastNameTxt, lastName);
        dataObject.getContactManager().setLastName(lastName);
        if (dataObject.getContactManager().getTags().equalsIgnoreCase("Policy Person")) {
            click(policyPersonTxt);
        } else if (dataObject.getContactManager().getTags().equalsIgnoreCase("Employee")) {
            click(tagsEmployeeTxt);
        }
        clickPageBody();
        sendText(addressTxt, faker.address().streetAddress());
        sendText(postalCodeTxt, rand.nextInt(10000, 99999));
        sendText(cityTxt, faker.address().city());
        jsClick(updateButton);
        logger.info("Update Action is invoked");
        waitForElementVisible(contactDetailTitleTxt);
        logger.info("Contact Details page has been displayed");

        String fullName = lastName + " " + firstName;
        logger.info("Full name of Contact Manager contact is " + fullName);
        dataObject.getContactManager().setFullName(fullName);

        String fullNameInUI = getText(contactDetailTitleTxt);
        logger.info("Full name from UI is " + fullNameInUI);
        String fullNameFile = dataObject.getContactManager().getFullName();
        logger.info("Full name from input data file is " + fullNameFile);
        assert fullNameInUI.equalsIgnoreCase(fullNameFile);
        logger.info("CM contact creation completed");
    }

    private boolean searchCompanyContact(String pcCompanyName) {
        logger.info("Searching company contact " + pcCompanyName + " in Contact Manager");
        waitForElementVisible(searchTitleTxt);
        setDropDown(cmSearchContTypeDdTxt, String.valueOf(Company));
        clearText(searchCompanyNameTxt);
        sendText(searchCompanyNameTxt, pcCompanyName);
        click(cmSearchContSearchButton);
        waitForElementVisible(cmSearchResultTxt);

        String companyNameInUI = getText(cmSearchResultTxt);
        boolean result = companyNameInUI.equalsIgnoreCase(pcCompanyName);
        if (result) {
            logger.info("Company contact " + companyNameInUI + " is found in Contact Manager");
            addStepInfoToReport("Company contact " + companyNameInUI + " is found in Contact Manager");
        } else {
            logger.error("Company contact " + companyNameInUI + " is not found in Contact Manager");
            addStepInfoToReport("Company contact " + companyNameInUI + " is not found in Contact Manager");
        }
        resetSearch();
        return result;
    }

    private boolean searchPersonContact(String fName, String lName) {
        logger.info("Searching person contact first name " + fName + ", last name " + lName + " in Contact Manager");
        String fullName = lName + " " + fName;
        waitForElementVisible(searchTitleTxt);
        setDropDown(cmSearchContTypeDdTxt, dataObject.getContactManager().getPersonType());
        waitForElementVisible(cmSearchContFirstNameTxtBx);
        clearText(cmSearchContFirstNameTxtBx);
        sendText(cmSearchContFirstNameTxtBx, fName);
        clearText(cmSearchContLstNameTxtBx);
        sendText(cmSearchContLstNameTxtBx, lName);
        click(cmSearchContSearchButton);
        boolean result;
        try {
            waitForElementVisible(cmSearchResultTxt);
            String fullNameInUI = getText(cmSearchResultTxt);
            result = fullNameInUI.equalsIgnoreCase(fullName);

            if (result) {
                logger.info("The person contact: " + fullNameInUI + " is found in Contact Manager");
                addStepInfoToReport("The person contact: " + fullNameInUI + " is found in Contact Manager");
            } else {
                logger.error("The person contact: " + fullNameInUI + " is not found in Contact Manager");
                addStepInfoToReport("The person contact: " + fullNameInUI + " is not found in Contact Manager");
            }
        } catch (TimeoutException e) {
            logger.info("Timeout, person contact: " + fullName + " is not found in Contact Manager");
            result = false;
        }

        resetSearch();
        return result;
    }

    private void resetSearch() {
        logger.info("Reset search result");
        click(cmResetButton);
        waitForPageLoadComplete();
        waitForAttributeToBe(cmSearchContTypeDdTxt, "value", "Company");
    }
}
