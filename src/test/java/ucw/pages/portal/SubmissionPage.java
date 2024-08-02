package ucw.pages.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ucw.elements.*;
import ucw.enums.ContactType;
import ucw.enums.Makes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ucw.enums.ContactType.Commercial;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.Germany;
import static ucw.utils.DateHelper.setVirtualDatePortal;

public class SubmissionPage extends Portal {
    private static final Logger logger = LogManager.getLogger(SubmissionPage.class);
    private static final Button newCustomerButton = Button.xpath("//*[@id='possible-account-matches']//button[contains(text(),'Continue as a New Customer')]");
    private static final Dropdown prefixPersonalDdTxt = Dropdown.xpath("//span[contains(text(),'Prefix')]/following::select[@class='ng-scope ng-isolate-scope gw-pl-select'][1]");
    private static final Dropdown orgTypeDDTxt = Dropdown.xpath("//span[text()='Organization Type']/../following-sibling::div//select");
    private static final TextBox emailTxt = TextBox.xpath("//span[text()='Email']/../following-sibling::div/div/input");
    private static final TextBox emailAddressTxt = TextBox.xpath("//span[text()='Email Address']/../following-sibling::div/div/input");
    private static final Button nextPolicyDetailsButton = Button.xpath("//button[@type='button' and @on-click='goToNext()']");
    private static final TextBox companyTxt = TextBox.xpath("//*[@id='existing-account-search']//div[2]//div[@class='gw-controls']//input");
    private static final Label acNameTxt = Label.xpath("//*[@id='page-inner']//gw-transaction-wizard-title//div/a");
    private static final Label quoteNoTxt = Label.xpath("//*[@id='page-inner']//gw-transaction-wizard-title//div[contains(text(),'Quote')]");
    private static final TextBox pVinTxt = TextBox.xpath("//span[text()='VIN']/../following-sibling::div//input");
    private static final Button vinSearchButton = Button.xpath("//span[text()='VIN']/../following-sibling::div//input/following-sibling::span");
    private static final Label vinSearchWarningMsg = Label.xpath("//div[@class='gw-alert__content' and contains(text(),'Due to search without or with partial result')]");
    private static final TextBox pLicenseTxt = TextBox.xpath("//span[text()='License plate']/../following-sibling::div//input");
    private static final Image licenseSearchButton = Image.xpath("//span[text()='License plate']/../following-sibling::div//input/following-sibling::span");
    private static final Label clickedLicenseMsg = Label.xpath("//div[@ng-show='clickedLicenseSearch']");
    private static final Dropdown pVehicleTypeDdTxt = Dropdown.xpath("//span[text()='Vehicle type']/../following-sibling::div//select");
    private static final TextBox pFirstRegDateTxt = TextBox.xpath("//span[text()='First registration date']/../following-sibling::div//input");
    private static final TextBox pModelTxt = TextBox.xpath("//*[@id='Model']");
    private static final Dropdown pModelSelector = Dropdown.xpath("//span[text()='Model']/../../div//select");
    private static final Dropdown pMakeDdTxt = Dropdown.xpath("//*[@id='Make']");
    private static final Dropdown pEnergyTypeDdTxt = Dropdown.xpath("//span[text()='Type of energy']/../following-sibling::div//select");
    private static final TextBox pCarPowerTxt = TextBox.xpath("//span[contains(text(),'Car power') or contains(text(),'Car Power')]/../following-sibling::div//input");
    private static final Label vinInlineErrorMsg = Label.xpath("//div[@model='vehicle.vin']/div/div/div[@class='gw-inline-messages']/div[@aria-hidden='false']/span");
    private static final Label lpInlineErrorMsg = Label.xpath("//div[@model='vehicle.license']/div/div/div[@class='gw-inline-messages']/div[@aria-hidden='false']/span");
    private static final Label carPowerErrorMsg = Label.xpath("//div[contains(@model,'vehicle.carPower')]/following-sibling::div//div");
    private static final Label warrantyKMErrorMsg = Label.xpath("//gw-pl-input-ctrl[@model='vehicle.makerWarrantyKilometers']/following-sibling::div/div[@class='gw-alert__content']/span");
    private static final TextBox pMileageTxt = TextBox.xpath("//span[text()='Mileage']/../following-sibling::div//input");
    private static final Button pWarrantyEndDateTxt = Button.xpath("//span[text()='Maker warranty - end date']/../following-sibling::div//input");
    private static final TextBox pWarrantyKMTxt = TextBox.xpath("//span[text()='Maker warranty - kilometers']/../following-sibling::div//input");
    private static final Dropdown pVehicleSpecificDdTxt = Dropdown.xpath("//span[text()='Vehicle specificities']/../following-sibling::div//select");
    private static final Button pNextButton = Button.xpath("//*[@id='page-inner']//span[text()='Next']");
    private static final Title quoteScrnHdngTxt = Title.xpath("//*[@id='page-inner']//gw-transaction-wizard-title//div[contains(text(),'Quote')]");
    private static final Label coverageTypeTxt = Label.xpath("//*[@id='page-inner']/div[2]//div[2]/div/div[1]/p[1]");
    private static final Button pSelectButton = Button.xpath("//*[@id='page-inner']//button/span[text()='Select']");
    private static final Title policyInfoHeadingTxt = Title.xpath("//*[@id='page-inner']//div[text()='Policy Information']");
    private static final Button pBuyNowButton = Button.xpath("//*[@id='page-inner']//span[text()='Buy Now']");
    private static final Button popupYesButton = Button.xpath("/html/body//button[contains(text(),'Yes')]");
    private static final Label paymentSuccessTxt = Label.xpath("//*[@class='gw-page gw-box gw-quote-confirmation ng-binding ng-scope']");
    private static final Dropdown producerCode1Txt = Dropdown.xpath("(//span[text()='Producer Code']/../following-sibling::div//select)[1]");
    private static final Image productCodeLoader = Image.xpath("//select[@id='ProductCode']/..");
    private static final Dropdown producerCode2Txt = Dropdown.xpath("//span[text()='Producer Code']/../following-sibling::div//select");
    private static final Dropdown countryDdTxt = Dropdown.xpath("//*[@id='existing-account-search']/section/ng-form//div/div[2]/div/div/div/select");
    private static final TextBox pFNameTxt = TextBox.xpath("//span[text()='First Name']/../following-sibling::div/div/input");
    private static final TextBox pLNameTxt = TextBox.xpath("//span[text()='Last Name']/../following-sibling::div/div/input");
    private static final Button searchButton = Button.xpath("//*[@id='existing-account-search']//section/ng-form//button[2]");
    private static final Button productNxtButton = Button.xpath("//*[@id='page-inner']//div//div[2]//div[3]/button[3]");
    private static final Title submissionTtlTxt = Title.xpath("//*[@id='existing-account-search']//h1[contains(text(), 'Search for Existing Beneficiary')]");
    private static final TextBox addressTxt = TextBox.xpath("//span[text()='Address']/../following-sibling::div/div/input");
    private static final TextBox zipcodeTxt = TextBox.xpath("//span[text()='ZIP Code']/../following-sibling::div/div/input");
    private static final TextBox cityTxt = TextBox.xpath("//span[text()='City']/../following-sibling::div/div/input");
    private static final Button nextButton = Button.xpath("//*[@id='page-inner']//div[2]//div[2]//section[1]/ng-form/div[4]/button[2]");
    private static final Items subscriptionPlans = Items.xpath("//p[@class='MultipleOfferingView-hashed__gw_quote_offering_name__sbobT ng-binding']");
    private static final Label vehicleErrorMsg = Label.xpath("//gw-wizard-single-error//span");
    private static final Items useAsLinks = Items.xpath("//td[@title='Use this Account']/a");
    private static final TextBox localDateChooser = TextBox.xpath("//input[@id='localDateChooser']");
    private static final Label marketingFeeLabel = Label.xpath("//gw-qnb-quote-view-cell//div/p[contains(text(),'Marketing Fee')]");
    private static final Button vehiclesTab = Button.xpath("(//div[@class='gw-wizard-sidebar']//ul/li)[2]");
    private static final Form offerForm = Form.tagName("gw-qnb-common-offering-selection");
    private static final Button saveButton = Button.xpath("//button[@on-click='save(form)']");
    private static final Form policyDetailForm = Form.xpath("//div[contains(@class,'gw-policy-detail')]");
    private static final Label savedSuccessMsg = Label.xpath("//*[text()='Your draft has been successfully saved.']");
    private static final Button continueQuoteButton = Button.xpath("//button[contains(@ng-if,'showContinueSubmissionButton')]");
    private static final Button saveChangesButton = Button.cssSelector("button[type='submit']");
    private static final Form accountInfoForm = Form.cssSelector("div[contact='accountInfoCopyView']");
    private static final Button closeButton = Button.xpath("//button[contains(text(),'Close')]");

    public void useExistingContact() {
        searchForExistingBeneficiary(contactType);
        selectBeneficiary(0);
        quoteForExistingContact();
    }

    public void createNewContact(ContactType type) {
        searchForExistingBeneficiary(type);
        addNewBeneficiaryDetails(type);
        assert isAccountCreated();
    }

    public boolean isAccountCreated() {
        waitForElementVisible(portAccInUITxt);
        boolean result = isElementDisplayed(portAccInUITxt);
        if (result) {
            logger.info("Portal account is created successfully");
        }
        return result;
    }

    public void addNewSubmission() {
        switch(contactType) {
            case Personal -> createPersonalPolicy();
            case Commercial -> createCommercialPolicy();
        }
    }

    public void createPersonalPolicy() {
        logger.info("Portal Personal policy submission starts");
        createNewContact(Personal);
        submitContactInfo();
        String quoteNum = getQuoteNo();
        dataObject.getPortal().setPersonalQuoteNo(quoteNum);
        goToVehicleDetails();
        addVehicleDetails();
        assert isPolicyNoDisplayed();
        addStepInfoToReport("Created Personal policy");
    }

    public void createCommercialPolicy() {
        logger.info("Portal Commercial policy submission starts");
        createNewContact(Commercial);
        submitContactInfo();
        String acNameUI = getText(acNameTxt);
        assert acNameUI.equalsIgnoreCase(dataObject.getPortal().getCompany());
        String quoteNum = getQuoteNo();
        dataObject.getPortal().setCommQuoteNo(quoteNum);
        goToVehicleDetails();
        addVehicleDetails();
        assert isPolicyNoDisplayed();
        addStepInfoToReport("Created Commercial policy");
    }

    public void goToVehicleDetails() {
        waitForElementVisible(localDateChooser);
        waitForElementVisible(nextPolicyDetailsButton);
        waitForElementVisible(nextPolicyDetailsButton);
        click(nextPolicyDetailsButton);
        waitForElementVisible(pVinTxt);
        addStepInfoToReport("Go to vehicle detail page");
    }

    public void inputVehicleMandatoryFields() {
        inputNewVIN();
        inputMandatoryFields();
    }

    public void inputVehicleMandatoryFields(String vin) {
        inputVIN(vin);
        inputMandatoryFields();
    }

    public String getQuoteNo() {
        waitForElementVisible(quoteNoTxt);
        String quoteNoLine = getText(quoteNoTxt);
        String quoteNum = quoteNoLine.replaceAll("\\D", "");
        logger.info("Quote no is " + quoteNum);
        addStepInfoToReport("Quote no is " + quoteNum);
        return quoteNum;
    }

    public boolean isPolicyNoDisplayed() {
        String policyMessageUI = getText(paymentSuccessTxt);
        logger.info("The Payment successfully message is: " + policyMessageUI);
        String policyNumberValue = policyMessageUI.replaceAll("\\D","");
        logger.info("The Portal policy number generated is " + policyNumberValue);
        dataObject.setPolicyNumber(policyNumberValue);
        addStepInfoToReport("The Portal policy number generated is " + policyNumberValue);
        return !policyNumberValue.isEmpty();
    }

    public void searchForExistingBeneficiary(ContactType contactType) {
        waitForElementVisible(submissionTtlTxt);
        if (contactType.equals(Personal)) {
            portalMenuClick("person");
            waitForElementVisible(pFNameTxt);
            String firstName = dataObject.getPortal().getFirstName();
            String lastName = dataObject.getPortal().getLastName();
            sendText(pFNameTxt, firstName);
            sendText(pLNameTxt, lastName);
            logger.info("Search Personal name in Portal: " + lastName + " " + firstName);
        } else {
            String companyName = dataObject.getPortal().getCompany();
            sendText(companyTxt, companyName);
            logger.info("Search Company name in Portal: " + companyName);
        }
        selectByText(countryDdTxt, dataObject.getCountry());
        addStepInfoToReport("Search for existing " + contactType + " beneficiary contact in Portal");
        click(searchButton);
    }

    private void addNewBeneficiaryDetails(ContactType contactType) {
        selectPortalProducerCode(dataObject.getProducerCode() + " " + dataObject.getPolicyCenter().getAdminLabel());
        click(newCustomerButton);
        if (contactType.equals(Personal)) {
            selectRandom(prefixPersonalDdTxt);
            sendText(addressTxt, faker.address().streetAddress());
            sendText(zipcodeTxt, rand.nextInt(10000, 99999));
            sendText(cityTxt, faker.address().city());
            sendText(emailTxt,
                    dataObject.getPortal().getLastName()
                            + dataObject.getPortal().getFirstName()
                            + "@email.com");
            selectByText(producerCode1Txt, dataObject.getPortal().getProducerCode());
            waitForElementVisible(nextButton);
            click(nextButton);
            waitForElementVisible(portAccInUITxt);
            waitForTextNotToBe(portAccInUITxt, "");
            String portalAccount = getText(portAccInUITxt);
            logger.info("Person Portal account no is: " + portalAccount);
            dataObject.getPortal().setPersonPTAccount(portalAccount);
            addStepInfoToReport("Person Portal account no is: " + portalAccount);
        } else {
            sendText(addressTxt, faker.address().streetAddress());
            sendText(emailAddressTxt,
                    dataObject.getPortal().getCompany()
                            .replace(" ", "")
                            .replace(",", "")
                    + "@email.com");
            sendText(zipcodeTxt, rand.nextInt(10000, 99999));
            sendText(cityTxt, faker.address().city());
            selectRandom(orgTypeDDTxt);
            selectByText(producerCode1Txt, dataObject.getPortal().getProducerCode());
            click(nextButton);
            waitForElementVisible(portAccInUITxt);
            waitForTextNotToBe(portAccInUITxt, "");
            String portalAccount = getText(portAccInUITxt);
            logger.info("Commercial Portal account no is: " + portalAccount);
            dataObject.getPortal().setCommPTAccount(portalAccount);
            addStepInfoToReport("Commercial Portal account no is: " + portalAccount);
        }
    }

    public void submitContactInfo() {
        logger.info("Quote portal policy");
        waitForAttributeContains(productCodeLoader, "class", "gw-loader-done");
        waitForPageLoadComplete(1000);
        click(productNxtButton);
        addStepInfoToReport("Quote Portal policy");
    }

    public void quoteForExistingContact() {
        logger.info("Quote portal policy for existing beneficiary");
        String producerCode = dataObject.getProducerCode() + " - " + dataObject.getPolicyCenter().getAdminLabel();
        waitForElementVisible(producerCode1Txt);
        selectByText(producerCode1Txt, producerCode);
        waitForAttributeContains(productCodeLoader, "class", "gw-loader-done");
        waitForPageLoadComplete(1000);
        click(productNxtButton);
        waitForElementVisible(acNameTxt);
        addStepInfoToReport("Selected producer code " + producerCode + " for existing contact");
    }

    public void inputNewVIN() {
        String vinNo = faker.vehicle().vin();
        logger.info("New random generated VIN is " + vinNo);
        clearText(pVinTxt);
        sendText(pVinTxt, vinNo);
        dataObject.getPolicyCenter().setVin(vinNo);
        addStepInfoToReport("Input new VIN " + vinNo);
    }

    public void inputVIN(String vinNo) {
        logger.info("Input VIN " + vinNo);
        sendText(pVinTxt, vinNo);
        dataObject.getPolicyCenter().setVin(vinNo);
        addStepInfoToReport("Input VIN " + vinNo);
    }

    public void inputLicensePlate() {
        clearText(pLicenseTxt);
        sendText(pLicenseTxt, dataObject.getPortal().getLicensePlate());
    }

    public void inputMake() {
        selectByText(pMakeDdTxt, dataObject.getPortal().getMake());
    }

    public void inputModel() {
        if (Arrays.asList(makesHasModelSelector).toString().contains(dataObject.getPortal().getMake())) {
            selectByText(pModelSelector, dataObject.getPortal().getModel());
        } else {
            clearText(pModelTxt);
            sendText(pModelTxt, dataObject.getPortal().getModel());
        }
    }

    public void inputMandatoryFields() {
        inputLicensePlate();
        selectRandom(pVehicleTypeDdTxt);
        setVirtualDatePortal(pFirstRegDateTxt, dataObject.getPortal().getRegDate());
        warrantyEndDate = getTextFromJSExecutor(pWarrantyEndDateTxt);
        inputMake();
        inputModel();
        selectByText(pEnergyTypeDdTxt, dataObject.getPortal().getEnergyType());
        inputCarPower(dataObject.getPortal().getCarPower());
        inputMileage(dataObject.getPortal().getMileage());
        inputWarrantyKilometer(dataObject.getPortal().getWarrantyKM());
        selectRandom(pVehicleSpecificDdTxt);
        addStepInfoToReport("Input all mandatory fields");
    }

    public void searchLicensePlate() {
        if (!dataObject.getCountry().equals(Germany)) {
            scrollUp();
            click(licenseSearchButton);
            waitForElementVisible(clickedLicenseMsg);
            click(licenseSearchButton);
            waitForElementVisible(vinSearchButton);
            addStepInfoToReport("Clicked on License Plate search icon");
        }
    }

    public void searchVIN() {
        scrollUp();
        click(vinSearchButton);
        waitForElementVisible(vinSearchWarningMsg);
        waitForPageLoadComplete();
        addStepInfoToReport("Clicked on VIN search icon");
    }

    public void submitVehicleInfo() {
        logger.info("Submit all entered vehicle detail information");
        searchLicensePlate();
        searchVIN();
        click(pNextButton);
    }

    public void buy() {
        selectPlan();
        proceedBuy();
    }

    public void addVehicleDetails() {
        inputNewVIN();
        inputMandatoryFields();
        submitVehicleInfo();
        selectPlan();
        proceedBuy();
    }

    public void selectPlan() {
        boolean found = false;
        String expectedType = dataObject.getPortal().getCoverageType();
        waitForElementVisible(offerForm);
        waitForElementVisible(quoteScrnHdngTxt);
        waitUntilSizeThan0(subscriptionPlans);
        List<WebElement> plans = findElements(subscriptionPlans);
        logger.info("Number of found plans " + plans.size());
        addStepInfoToReport("Found " + plans.size() + " subscription plans");
        for (int i = 0; i < plans.size(); i++) {
            String eachType = getText(plans.get(i)).trim();
            if (expectedType.contains("GVO Alfa Romeo Certified") || expectedType.contains("GVO Alfa Romeo Certfied")) {
                expectedType = expectedType.replace("Certified", "").replace("Certfied", "");
                eachType = eachType.replace("Certified", "").replace("Certfied", "");
            }
            if (eachType.equals(expectedType)) {
                found = true;
                logger.info("Found the expected plan for " + expectedType);
                int planOrder = i + 1;
                Button selectBtn = Button.xpath("(//p[@class='MultipleOfferingView-hashed__gw_quote_offering_name__sbobT ng-binding'])[" + planOrder + "]/following-sibling::div/button/span");
                click(selectBtn);
                waitForElement(policyInfoHeadingTxt);
                break;
            }
        }
        if (!found) {
            logger.error("Can't found expected plan for " + expectedType);
        }
    }

    public void proceedBuy() {
        click(pBuyNowButton);
        switchToActiveElement();
        click(popupYesButton);
        waitForElement(paymentSuccessTxt);
        addStepInfoToReport("Proceeded to buy the selected plan");
    }

    public boolean isPolicySubmitted() {
        String policyMessageUI = getText(paymentSuccessTxt);
        String policySubMsg = "Payment Successful";
        return policyMessageUI.contains(policySubMsg);
    }

    public String getErrorMessage() {
        waitForElementVisible(vehicleErrorMsg);
        waitForTextNotToBe(vehicleErrorMsg, "");
        return getText(vehicleErrorMsg);
    }

    public void inputWarrantyKilometer(String value) {
        clearText(pWarrantyKMTxt);
        sendText(pWarrantyKMTxt, value);
    }

    public void inputCarPower(int value) {
        clearText(pCarPowerTxt);
        sendText(pCarPowerTxt, value);
    }

    public String getCarPowerErrorMsg() {
        waitForElementVisible(carPowerErrorMsg);
        waitForTextNotToBe(carPowerErrorMsg, "");
        return getText(carPowerErrorMsg).trim();
    }

    public String getWarrantyKMErrorMsg() {
        waitForElementVisible(warrantyKMErrorMsg);
        waitForTextNotToBe(warrantyKMErrorMsg, "");
        return getText(warrantyKMErrorMsg).trim();
    }

    public void selectBeneficiary(int i) {
        List<WebElement> useLinks;
        int retries = 5;
        do {
            useLinks = findElements(useAsLinks);
            retries--;
        } while (useLinks.isEmpty() || retries > 0);
        click(useLinks.get(i));
        logger.info("Selected existing beneficiary contact at index " + i);
        waitForElementVisible(runningLoader);
        addStepInfoToReport("Selected existing beneficiary contact");
    }

    public String getVinErrorInlineMsg() {
        waitForTextNotToBe(vinInlineErrorMsg, "");
        return getText(vinInlineErrorMsg);
    }

    public String getLicensePlateInlineErrorMsg() {
        waitForTextNotToBe(lpInlineErrorMsg, "");
        return getText(lpInlineErrorMsg);
    }

    public void cancel() {
        try {
            if (isElementDisplayed(proceedButton, 5)) {
                switchToActiveElement();
                click(proceedButton);
            }
        } catch (NoSuchElementException ignore) {}
    }

    public void closePortalErrorPopup() {
        try {
            if (isElementDisplayed(closeButton, 1)) {
                switchToActiveElement();
                click(closeButton);
            }
        } catch (NoSuchElementException ignore) {}
    }

    public void inputMileage(long mileage) {
        clearText(pMileageTxt);
        sendText(pMileageTxt, mileage);
    }

    public void inputRegDate(String date) {
        clearText(pFirstRegDateTxt);
        setVirtualDatePortal(pFirstRegDateTxt, date);
    }

    public void inputLicensePlate(String licensePlate) {
        clearText(pLicenseTxt);
        sendText(pLicenseTxt, licensePlate);
    }

    public String getDuration() {
        waitForElementVisible(offerForm);
        logger.info("Get duration value at Quote screen");
        String value = null;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Get duration value at Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                Label durationLbl = Label.xpath("(//li//label[text()='Duration (Months)']/following-sibling::div/span)[" + planOrder + "]");
                waitForTextNotNull(durationLbl);
                value = getText(durationLbl);
                logger.info("Actual duration is " + value);
                break;
            }
        }
        return value;
    }

    public Items getDurationList() {
        waitForElementVisible(offerForm);
        logger.info("Get all duration values from dropdown at Quote screen");
        Items durationDD = null;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Get all duration values from dropdown at Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                durationDD = Items.xpath("(//li//label[text()='Duration (Months)']/following-sibling::gw-pc-coverage-term/select)[" + planOrder + "]");
                waitForElementVisible(durationDD);
                break;
            }
        }
        return durationDD;
    }

    public boolean isWaitingPeriodDisplayed() {
        waitForElementVisible(offerForm);
        logger.info("Check if waiting period is shown or not Quote screen");
        boolean result = true;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Check if waiting period is shown or not Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                Label waitingPeriodLbl = Label.xpath("(//li//label[text()='Waiting Period (Months)']/following-sibling::gw-pc-coverage-term/span)[" + planOrder + "]");
                try {
                    result = isElementDisplayed(waitingPeriodLbl, 5);
                } catch (NoSuchElementException e) {
                    result = false;
                }
                break;
            }
        }
        return result;
    }

    public String getWaitingPeriod() {
        waitForElementVisible(offerForm);
        logger.info("Get waiting period value at Quote screen");
        String value = null;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Get waiting period value at Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                Label durationLbl = Label.xpath("(//li//label[contains(text(),'Waiting Period')]/following-sibling::div/span)[" + planOrder + "]");
                value = getText(durationLbl);
                break;
            }
        }
        return value;
    }

    public boolean isMarketingFeeDisplayed() {
        logger.info("Check if Marketing fee is shown or not Quote screen");
        waitForElement(marketingFeeLabel);
        addStepInfoToReport("Check if Marketing fee is shown or not Quote screen");
        return isElementDisplayed(marketingFeeLabel, 5);
    }

    public boolean checkMakeOnlyContains(Makes make) {
        List<String> availableMakes = getAllOptions(pMakeDdTxt);
        availableMakes.remove(0); //Remove the Choose Make option
        logger.info("Number of available makes is " + availableMakes.size());
        boolean result = availableMakes.size() == 1 && availableMakes.get(0).equals(make.toString());
        return result;
    }

    public boolean checkAllOffersContain(String partialText) {
        waitForElementVisible(offerForm);
        boolean result = true;
        List<WebElement> offers = findElements(subscriptionPlans);
        for (WebElement offer: offers) {
            logger.info("There is offer: " + offer.getText());
            result &= offer.getText().contains(partialText);
        }
        addStepInfoToReport("Check if every offer contains this text: " + partialText);
        return result;
    }

    public String getSparePartsLabel() {
        waitForElementVisible(offerForm);
        String value = null;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Get spare parts reimbursement label at Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                Label sparePartsLabel = Label.xpath("(//label[contains(text(),'Spare parts reimbursement % at the date of subscription') " +
                        "or contains(text(),'Maximale Erstattung der garantiebedingten')])[" + planOrder + "]");
                waitForTextNotNull(sparePartsLabel);
                value = getText(sparePartsLabel);
                break;
            }
        }
        return value;
    }

    public int getReimbursementPercentValue() {
        waitForElementVisible(offerForm);
        int value = 0;
        List<WebElement> plans = findElements(subscriptionPlans);
        addStepInfoToReport("Get spare parts reimbursement value at Quote screen");
        for (int i = 0; i < plans.size(); i++) {
            if (getText(plans.get(i)).trim().contains(dataObject.getPortal().getCoverageType())) {
                int planOrder = i + 1;
                Label durationLbl = Label.xpath("(//label[contains(text(),'Spare parts reimbursement % at the date of subscription')])[" + planOrder + "]/following-sibling::div/span");
                value = Integer.parseInt(getText(durationLbl));
                break;
            }
        }
        return value;
    }

    public void goBackToVehiclesScreen() {
        click(vehiclesTab);
        waitForElementVisible(pVinTxt);
        addStepInfoToReport("Went back to vehicle detail page");
    }

    public String getEnteredRegistrationDate() {
        clickPageBody();
        return getTextFromJSExecutor(pFirstRegDateTxt);
    }

    public void saveDraft() {
        click(saveButton);
        switchToActiveElement();
        click(proceedButton);
        waitForElementVisible(policyDetailForm);
        waitForElementVisible(savedSuccessMsg);
    }

    public void continueQuote() {
        click(continueQuoteButton);
        waitForElementVisible(accountInfoForm);
        click(saveChangesButton);
        switchToActiveElement();
        click(okButton);
    }

    public boolean checkModelsNotContains(String[] models) {
        List<String> currentModelValues = getAllOptions(pModelSelector);
        clickPageBody();
        return Collections.disjoint(currentModelValues, Arrays.asList(models));
    }
}
