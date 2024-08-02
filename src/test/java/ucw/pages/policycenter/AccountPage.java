package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import ucw.elements.*;

import static ucw.enums.AccountType.Company;
import static ucw.enums.AccountType.Person;

public class AccountPage extends PolicyCenter {

    private static final Logger logger = LogManager.getLogger(AccountPage.class);
    private static final TextBox prefixACTxt = TextBox.xpath("//input[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:GlobalPersonNameInputSet:Prefix-inputEl']");
    private static final TextBox firstNameCompanyTxt = TextBox.id("CreateAccount:CreateAccountScreen:CreateAccount_PSADV:companyrepresentative_firstname_psa-inputEl");
    private static final TextBox lastNameCompanyTxt = TextBox.id("CreateAccount:CreateAccountScreen:CreateAccount_PSADV:companyrepresentative_lasttname_psa-inputEl");
    private static final TextBox firstNamePersonTxt = TextBox.xpath("//input[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox lastNamePersonTxt = TextBox.xpath("//input[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final TextBox dobTxt = TextBox.xpath("//input[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:dob-inputEl']");
    private static final TextBox primaryEmailTxt = TextBox.xpath("//input[@id = 'CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:EmailAddress1-inputEl']");
    private static final Dropdown prodCodePolicyDdTxt = Dropdown.xpath("//input[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:ProducerSelectionInputSet:ProducerCode-inputEl']");
    private static final TextBox address1PolicyTxt = TextBox.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:PrimaryAddressInputSet_PSAInputSet:AddressInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox cityPolicyTxt = TextBox.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:PrimaryAddressInputSet_PSAInputSet:AddressInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl']");
    private static final TextBox postalCodePolicyTxt = TextBox.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:PrimaryAddressInputSet_PSAInputSet:AddressInputSet:globalAddressContainer:GlobalAddressInputSet:PostalCode-inputEl']");
    private static final Button updateButton = Button.id("CreateAccount:CreateAccountScreen:ForceDupCheckUpdate-btnInnerEl");
    private static final Label organizationLbl = Label.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:ProducerSelectionInputSet:Producer-inputEl']");
    private static final Title accFileSumTitle = Title.xpath("//*[@id='AccountFile_Summary:AccountFile_SummaryScreen:ttlBar']");
    private static final Button actionButton = Button.xpath("//*[@id='AccountFile:AccountFile_PSAMenuActions-btnInnerEl']");
    private static final Button newSubmissionButton = Button.xpath("//*[@id='AccountFile:AccountFile_PSAMenuActions:AccountFileMenuActions_Create:AccountFileMenuActions_PSANewSubmission-textEl']");
    private static final Button updateBtnTxt = Button.xpath("//*[@id='CreateAccount:CreateAccountScreen:ForceDupCheckUpdate-btnInnerEl']");
    private static final Dropdown cmpOrgTypeDdTxt = Dropdown.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CountryDependent_PSAInputSet:OrgType-inputEl']");
    private static final TextBox cmpAddress1Txt = TextBox.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:PrimaryAddressInputSet_PSAInputSet:AddressInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final Label pcDuplicateContactMsg = Label.xpath("//label[contains(@id, 'DuplicateContactsPopup') and text() = 'The contact potentially matches the following contacts.  Please select one that matches, if any.']");
    private static final Link returnToCreateElement = Link.xpath("//a[text() = 'Return to Create account']");
    private static final Link searchResultAccNumber = Link.id("NewAccount:NewAccountScreen:NewAccountSearchResultsLV:0:AccountNumber");
    private static final Button newAccPersonButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountButton:NewAccount_Person-textEl']");
    private static final Button newAccCompanyButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountButton:NewAccount_Company-textEl']");
    private static final TextBox newCityTxt = TextBox.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl']");
    private static final TextBox newAccountTitleTxt = TextBox.id("NewAccount:NewAccountScreen:ttlBar");
    private static final TextBox newFirstNameTxt = TextBox.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox newLastNameTxt = TextBox.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Dropdown newCountryDropdown = Dropdown.id("NewAccount:NewAccountScreen:NewAccountSearchDV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:Country-inputEl");
    private static final Button accountSearchButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Label firstNameError = Label.xpath("//div[text()='First name : Invalid value : the numbers and special characters are not accepted in this field']");
    private static final Label lastNameError = Label.xpath("//div[text()='Last name : Invalid value : the numbers and special characters are not accepted in this field']");
    private static final Label cityError = Label.xpath("//div[text()='City : Invalid value : the numbers and special characters are not accepted in this field']");
    private static final Label emptyEmailWarningMsg = Label.xpath("//div[text()='Be careful, the field is empty.Please put a valid email address to receive the results of the quality survey']");
    private static final Title accTitleTxt = Title.id("NewAccount:NewAccountScreen:ttlBar");
    private static final Button searchButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Button newAccCreationDdTxt = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountButton-btnInnerEl']");
    private static final Button personButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountButton:NewAccount_Person-textEl']");
    private static final Title createAccountFromPolicyTxt = Title.id("CreateAccount:CreateAccountScreen:ttlBar");
    private static final TextBox mobilePhone = TextBox.id("CreateAccount:CreateAccountScreen:CreateAccount_PSADV:CreateAccountContactInputSet:CellPhone:GlobalPhoneInputSet:NationalSubscriberNumber-inputEl");
    private static final Dropdown cmpCountryDdTxt = Dropdown.xpath("//*[@id='CreateAccount:CreateAccountScreen:CreateAccount_PSADV:PrimaryAddressInputSet_PSAInputSet:AddressInputSet:globalAddressContainer:GlobalAddressInputSet:Country-inputEl']");
    private static final TextBox newCompanyNameTxt = TextBox.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountSearchDV:GlobalContactNameInputSet:Name-inputEl']");
    private static final Dropdown producerCodeDropdown = Dropdown.xpath("//input[@id='NewSubmission:NewSubmissionScreen:SelectAccountAndProducerDV:ProducerSelectionInputSet:ProducerCode-inputEl']");

    public void createNewAccount() {
        boolean createNew = checkIfAccountIsCreated();
        if (createNew) {
            createAccount();
            assert isAccountCreated();
        }
        addStepInfoToReport("New " + accountType + " account is created");
    }

    public boolean isAccountCreated() {
        waitForElementVisible(accSummaryTxt);
        logger.info("Account is created successfully");
        return isElementDisplayed(accSummaryTxt);
    }

    private boolean checkIfAccountIsCreated() {
        logger.info("Create Policy Center account with account type " + accountType);
        boolean useNew = false;
        waitForElementVisible(newAccountTitleTxt);

        if (accountType.equals(Person)) {
            logger.info("Check if current person "
                    + dataObject.getContactFirstName()
                    + " "
                    + dataObject.getContactLastName()
                    + " exists");
            sendText(newFirstNameTxt, dataObject.getContactFirstName());
            sendText(newLastNameTxt, dataObject.getContactLastName());
            setDropDown(newCountryDropdown, dataObject.getCountry());
        } else {
            logger.info("Check if current company " + dataObject.getCompanyName() + " exist");
            sendText(newCompanyNameTxt, dataObject.getCompanyName());
        }
        addStepInfoToReport("Check if this contact exists");
        click(accountSearchButton);
        try {
            if (isElementDisplayed(searchResultAccNumber, 3)) {
                String accountNumber = getText(searchResultAccNumber);
                click(searchResultAccNumber);
                logger.info("Found the existing account, use existence account " + accountNumber);
                addStepInfoToReport("Found the existing account, use existence account " + accountNumber);
            }
        } catch (NoSuchElementException e) {
            useNew = true;
            if (accountType.equals(Company)) {
                String companyName = dataObject.getCompanyName();
                clearText(newFirstNameTxt);
                clearText(newLastNameTxt);
                clearText(newCompanyNameTxt);
                sendText(newCompanyNameTxt, companyName);
                logger.info("Set new company name as " + companyName);
            }
            click(newAccCreationDdTxt);

            if (accountType.equals(Person)) {
                click(newAccPersonButton);
            } else {
                click(newAccCompanyButton);
            }
        }
        return useNew;
    }

    private void createAccount() {
        switch (accountType) {
            case Person -> createPersonContact();
            case Company -> createCompanyContact();
        }
    }

    private void createPersonContact() {
        logger.info("Create Person contact in Policy Center");
        waitForElementVisible(createAccountFromPolicyTxt);
        setDropDownRandomValue(prefixACTxt);
        clearText(firstNamePersonTxt);
        sendText(firstNamePersonTxt, dataObject.getContactFirstName());
        clearText(lastNamePersonTxt);
        sendText(lastNamePersonTxt, dataObject.getContactLastName());
        String pcFullName = dataObject.getContactLastName() + " " + dataObject.getContactFirstName();
        dataObject.setFullName(pcFullName);
        setVirtualDate(dobTxt, dataObject.getPolicyCenter().getDateOfBirth());
        sendText(primaryEmailTxt, pcFullName.replace(" ", "").toLowerCase() + "@test.com");
        setDropDown(cmpCountryDdTxt, dataObject.getCountry());
        clickPageBody();
        sendText(address1PolicyTxt, faker.address().streetAddress());
        sendText(postalCodePolicyTxt, rand.nextInt(10000, 99999));
        sendText(cityPolicyTxt, faker.address().cityName());
        addStepInfoToReport("Filled out all mandatory fields to create person account");
        finishLastSteps();
    }

    private void finishLastSteps() {
        String producerCode = (getText(organizationLbl).equalsIgnoreCase("PCR")) ? "010435B " : dataObject.getProducerCode() + " ";
        setDropDown(prodCodePolicyDdTxt, producerCode);
        logger.info("Selected producer code " + producerCode);
        waitForElementVisible(updateButton);
        click(updateButton);
        logger.info("Policy Center account creation completed");
        addStepInfoToReport("Policy Center account creation completed");
    }

    private void createCompanyContact() {
        waitForElementVisible(updateBtnTxt);
        setDropDownRandomValue(cmpOrgTypeDdTxt);
        logger.info("Entered organization type " + getAttributeValue(cmpOrgTypeDdTxt, "value"));
        setDropDown(cmpCountryDdTxt, dataObject.getCountry());
        sendText(cmpAddress1Txt, faker.address().streetAddress());
        sendText(postalCodePolicyTxt, rand.nextInt(10000, 99999));
        sendText(cityPolicyTxt, faker.address().cityName());
        sendText(primaryEmailTxt,
                dataObject.getCompanyName().replace(",", "").replace(" ", "").toLowerCase() + "@email.com");
        sendText(firstNameCompanyTxt, dataObject.getContactFirstName());
        sendText(lastNameCompanyTxt, dataObject.getContactLastName());
        addStepInfoToReport("Filled out all mandatory fields to create Company account");
        finishLastSteps();
    }

    public void navigateToSubmissionPage() {
        waitForElementVisible(accFileSumTitle);
        click(actionButton);
        hoverAndClick(newSubmissionButton);
        addStepInfoToReport("Navigate to New Submission page");
    }

    public void selectGVO() {
        setDropDown(producerCodeDropdown, dataObject.getProducerCode() + " - " + dataObject.getPolicyCenter().getAdminLabel());
        Link productPath = Link.xpath("//div[starts-with(text(),'"
                + dataObject.getPolicyCenter().getProductName()
                + "')]/parent::td/preceding-sibling::td//a");

        Label exactProductName = Label.xpath("//div[starts-with(text(),'"
                + dataObject.getPolicyCenter().getProductName()
                + "')]/parent::td/div");
        String productNameValue = getText(exactProductName);
        dataObject.getPolicyCenter().setProductName(productNameValue);
        waitForElementVisible(productPath);
        click(productPath);
        logger.info("Selected product " + productNameValue);
        addStepInfoToReport("Selected GVO product");
    }

    public void selectEGVO() {
        setDropDown(producerCodeDropdown, dataObject.getProducerCode() + " - " + dataObject.getPolicyCenter().getAdminLabel());
        Link productPath = Link.xpath("//div[starts-with(text(),'"
                + dataObject.getPolicyCenter().getProdCodeTypeForEGVOStandalone()
                + "')]/parent::td/preceding-sibling::td//a");
        Label exactProductName = Label.xpath("//div[starts-with(text(),'"
                + dataObject.getPolicyCenter().getProdCodeTypeForEGVOStandalone()
                + "')]/parent::td/div");
        String productNameValue = getText(exactProductName);
        dataObject.getPolicyCenter().setProdCodeTypeForEGVOStandalone(productNameValue);
        click(productPath);
        logger.info("Selected product " + productNameValue);
    }

    public boolean validateCreationScreenFields() {
        validateAccountInformation();
        logger.info("Account creation screen validation starts");
        waitForElementVisible(createAccountFromPolicyTxt);
        setDropDownRandomValue(prefixACTxt);
        String fName = faker.name().firstName().replace("'", "");
        clearText(firstNamePersonTxt);
        sendText(firstNamePersonTxt, fName);
        logger.info("First name entered: " + fName);
        click(updateButton);
        waitForElementVisible(firstNameError);
        assert isElementDisplayed(firstNameError);
        addStepInfoToReport("First name error message display");
        clearText(firstNamePersonTxt);
        clickPageBody();

        String lName = faker.name().lastName().replace("'", "");
        clearText(lastNamePersonTxt);
        sendText(lastNamePersonTxt, lName);
        logger.info("Last name entered: " + lName);
        click(updateButton);
        waitForElementVisible(lastNameError);
        assert isElementDisplayed(lastNameError);
        addStepInfoToReport("Last name error message display");
        clearText(lastNamePersonTxt);
        clickPageBody();

        String cityName = faker.address().cityName();
        clearText(cityPolicyTxt);
        sendText(cityPolicyTxt, cityName);
        logger.info("City entered: " + cityName);
        click(updateButton);

        waitForElementVisible(cityError);
        assert isElementDisplayed(cityError);
        addStepInfoToReport("City error message display");
        clearText(cityPolicyTxt);
        clickPageBody();

        sendText(firstNamePersonTxt, faker.name().firstName().replace("'", ""));
        sendText(lastNamePersonTxt, faker.name().lastName().replace("'", ""));
        setVirtualDate(dobTxt, dataObject.getPolicyCenter().getDateOfBirth());
        sendText(address1PolicyTxt, faker.address().streetAddress());
        sendText(postalCodePolicyTxt, rand.nextInt(10000, 99999));
        setDropDown(cmpCountryDdTxt, dataObject.getCountry());
        sendText(cityPolicyTxt, faker.address().city());
        finishLastSteps();

        assert isElementDisplayed(emptyEmailWarningMsg);
        String emailWarningMsg = getText(emptyEmailWarningMsg);
        logger.info("Warning message displayed: " + emailWarningMsg);
        addStepInfoToReport("Warning message displayed: " + emailWarningMsg);

        click(updateButton);
        waitForElementVisible(accFileSumTitle);
        logger.info("Account creation screen validation completed");
        addStepInfoToReport("Account creation screen validation completed");
        return isElementDisplayed(accFileSumTitle);
    }

    public void validateAccountInformation() {
        logger.info("Account information screen validation starts");
        waitForElementVisible(newAccountTitleTxt);
        String fName = faker.name().firstName().replace("'", "");
        sendText(newFirstNameTxt, fName);
        logger.info("First name entered: " + fName);
        assert isElementDisplayed(firstNameError);
        addStepInfoToReport("First name error message display");
        fName = "PCFName";
        clearText(newFirstNameTxt);
        sendText(newFirstNameTxt, fName);
        logger.info("First name entered: " + fName);
        String lName = faker.name().lastName().replace("'", "");

        sendText(newLastNameTxt, lName);
        logger.info("Last name entered: " + lName);
        assert isElementDisplayed(lastNameError);
        addStepInfoToReport("Last name error message display");
        lName = "PCLName";
        clearText(newLastNameTxt);
        sendText(newLastNameTxt, lName);
        logger.info("Last name entered: " + lName);

        String cityName = faker.address().cityName();
        sendText(newCityTxt, cityName);
        logger.info("City entered: " + cityName);
        assert isElementDisplayed(cityError);
        addStepInfoToReport("City name error message display");
        clearText(newCityTxt);
        sendText(newCityTxt, faker.address().cityName());
        sendText(newLastNameTxt, dataObject.getContactLastName());
        click(accountSearchButton);
        click(newAccCreationDdTxt);
        click(newAccPersonButton);
        logger.info("Account information screen validation completed");
        addStepInfoToReport("Account information screen validation completed");
    }



    public String getPhoneMessage(String companyName) {
        logger.info("Phone localization validation of Policy Center starts");
        waitForElementVisible(accTitleTxt);
        sendText(newCompanyNameTxt, companyName);
        click(searchButton);
        click(newAccCreationDdTxt);
        click(personButton);
        waitForElementVisible(createAccountFromPolicyTxt);
        hover(mobilePhone);
        String mobilePhoneValue = getAttributeValue(mobilePhone, "data-qtip");
        addStepInfoToReport("Actual mobile phone is " + mobilePhoneValue);
        return mobilePhoneValue;
    }

    public String getDefaultCountry(String companyName) {
        logger.info("Policy Center address localization validation starts");
        waitForElementVisible(accTitleTxt);
        sendText(newCompanyNameTxt, companyName);
        click(searchButton);
        click(newAccCreationDdTxt);
        click(personButton);
        waitForElementVisible(createAccountFromPolicyTxt);
        String defaultCountry = getAttributeValue(cmpCountryDdTxt, "value");
        addStepInfoToReport("Actual default country is " + defaultCountry);
        return defaultCountry;
    }
}
