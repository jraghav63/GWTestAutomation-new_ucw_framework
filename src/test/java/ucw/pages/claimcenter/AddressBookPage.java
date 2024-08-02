package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Button;
import ucw.elements.Dropdown;
import ucw.elements.Label;
import ucw.elements.TextBox;

import static ucw.enums.AccountType.Company;
import static ucw.enums.AccountType.Person;

public class AddressBookPage extends ClaimCenter {
    private static final Logger logger = LogManager.getLogger(AddressBookPage.class);
    private static final Dropdown typeDropDownTxt = Dropdown.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:ContactSubtype-inputEl']");
    private static final Label firstNameLabel = Label.id("AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:NameInputSet:GlobalPersonNameInputSet:FirstName-labelEl");
    private static final TextBox fNameSearchTxt = TextBox.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:NameInputSet:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox lNameSearchTxt = TextBox.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:NameInputSet:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final TextBox companyNameSearchTxt = TextBox.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:NameInputSet:GlobalContactNameInputSet:Name-inputEl']");
    private static final TextBox clientCodeTxt = TextBox.xpath("//input[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:producercode_psa-inputEl']");
    private static final Dropdown addressBookCountry = Dropdown.id("AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:CCAddressBookSearchLocationInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:Country-inputEl");
    private static final Button searchButton = Button.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Label nameInUITxt = Label.xpath("//*[@id='AddressBookSearch:AddressBookSearchScreen:AddressBookSearchLV:0:DisplayName']");

    public boolean validateProducerCode() {
        logger.info("Validate producer code in Address Book");
        sendText(clientCodeTxt, dataObject.getProducerCode());
        setDropDown(addressBookCountry, dataObject.getCountry());
        click(searchButton);
        waitForElementVisible(nameInUITxt);
        String nameUI = getText(nameInUITxt);
        String expectedName = dataObject.getClaimCenter().getCommercialName();
        logger.info("The actual Commercial name " + nameUI + " is displaying in Address Book");
        logger.info("The expected Commercial name is " + expectedName);
        addStepInfoToReport("The actual Commercial name " + nameUI + " is displaying in Address Book");
        boolean result = nameUI.equalsIgnoreCase(expectedName);
        if (result) {
            logger.info("Producer Code in Address Book is validated successfully");
        } else {
            logger.error("Producer Code in Address Book validation is failed");
        }
        return result;
    }

    public boolean searchCMContacts() {
        String nameUI;
        logger.info("Searching for CM company contact in Claim Center");
        boolean result = false;
        waitForElementVisible(typeDropDownTxt);
        if (accountType.equals(Person)) {
            logger.info("Search with Person type");
            setDropDown(typeDropDownTxt, "Person");
            waitForElementVisible(firstNameLabel);
            sendText(fNameSearchTxt, dataObject.getContactManager().getFirstName());
            sendText(lNameSearchTxt, dataObject.getContactManager().getLastName());
            addStepInfoToReport("Search CM Person contact in Claim Center");
            click(searchButton);
            waitForElementVisible(nameInUITxt);
            nameUI = getText(nameInUITxt);
            result = nameUI.equalsIgnoreCase(dataObject.getContactManager().getFullName());
            if (result) {
                logger.info("The CM Person contact " + nameUI + " is found in Claim Center");
                addStepInfoToReport("The CM Person contact " + nameUI + " is found in Claim Center");
            } else {
                logger.error("The CM Person contact " + nameUI + " is not found in Claim Center");
                addStepInfoToReport("The CM Person contact " + nameUI + " is not found in Claim Center");
            }
        } else if (accountType.equals(Company)) {
            logger.info("Search with Company type");
            sendText(companyNameSearchTxt, dataObject.getContactManager().getCompanyName());
            addStepInfoToReport("Search CM Company contact in Claim Center");
            click(searchButton);
            waitForElementVisible(nameInUITxt);
            nameUI = getText(nameInUITxt);
            result = nameUI.equalsIgnoreCase(dataObject.getContactManager().getCompanyName());
            if (result) {
                logger.info("The CM Company contact " + nameUI + " is found in Claim Center");
                addStepInfoToReport("The CM Company contact " + nameUI + " is found in Claim Center");
            } else {
                logger.error("The CM Company contact " + nameUI + " is not found in Claim Center");
                addStepInfoToReport("The CM Company contact " + nameUI + " is not found in Claim Center");
            }
        }
        return result;
    }
}
