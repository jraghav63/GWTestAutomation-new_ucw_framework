package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import ucw.elements.*;

import java.text.DecimalFormat;
import java.util.List;

import static ucw.enums.AccountType.Person;

public class AccountsPage extends BillingCenter {
    private static final Logger logger = LogManager.getLogger(AccountsPage.class);
    private static final TextBox accountSearchTxt = TextBox.xpath("//*[@id='TabBar:AccountsTab:AccountNumberSearchItem-inputEl']");
    private static final TextBox searchAccountTxt = TextBox.xpath("//input[@id='Accounts:AccountSearchScreen:AccountSearchDV:AccountNumberCriterion-inputEl']");
    private static final Button accountSearchButton = Button.xpath("//a[@id='Accounts:AccountSearchScreen:AccountSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']/..");
    private static final Table accountSearchResultTable = Table.id("Accounts:AccountSearchScreen:AccountSearchResultsLV-body");
    private static final Link accountSearchResult = Link.id("Accounts:AccountSearchScreen:AccountSearchResultsLV:0:AccountNumber");
    private static final Label accountNumber = Label.xpath("//*[@id='AccountGroup:AccountInfoBar:AccountNumber-btnInnerEl']//span[2]");
    private static final Button accountActionButton = Button.xpath("//*[@id='AccountsGroup:AccountsMenuActions-btnEl']");
    private static final Button newAccountButton = Button.xpath("//*[@id='AccountsGroup:AccountsMenuActions:AccountsMenuActions_NewAccount-textEl']");
    private static final Label newAccountTitleTxt = Label.id("NewAccount:NewAccountScreen:ttlBar");
    private static final TextBox accNameTxt = TextBox.id("NewAccount:NewAccountScreen:NewAccountDV:AccountName-inputEl");
    private static final Dropdown typeTxt = Dropdown.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:AccountTypeInput-inputEl']");
    private static final Label accountNumberTxt = Label.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:AccountNumber-inputEl']");
    private static final Dropdown countryTxt = Dropdown.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:CountryId-inputEl']");
    private static final Dropdown billingPlanTxt = Dropdown.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:BillingPlan-inputEl']");
    private static final Dropdown delinquencyPlanTxt = Dropdown.xpath("//input[@id='NewAccount:NewAccountScreen:NewAccountDV:DelinquencyPlan-inputEl']");
    private static final Dropdown addContactButton = Dropdown.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:NewAccountContactsLV_tb:addNewContact-btnInnerEl']");
    private static final Dropdown addNewPersonButton = Dropdown.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:NewAccountContactsLV_tb:addNewContact:addNewPerson-textEl']");
    private static final Title addAccContactText = Title.id("NewAccountContactPopup:NewAccountContactScreen:ttlBar");
    private static final Dropdown prefixButton = Dropdown.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:NameInputSet:GlobalPersonNameInputSet:Prefix-inputEl']");
    private static final TextBox firstnameTxt = TextBox.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:NameInputSet:GlobalPersonNameInputSet:FirstName-inputEl");
    private static final TextBox lastnameTxt = TextBox.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:NameInputSet:GlobalPersonNameInputSet:LastName-inputEl");
    private static final TextBox addressTxt = TextBox.xpath("//input[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox cityTxt = TextBox.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:globalAddressContainer:GlobalAddressInputSet:City-inputEl");
    private static final TextBox postcodeTxt = TextBox.id("NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:globalAddressContainer:GlobalAddressInputSet:PostalCode-inputEl");
    private static final Button primaryPayerRadioBtn = Button.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:PrimaryPayer_true-boxLabelEl']");
    private static final Button updateButton = Button.id("NewAccountContactPopup:NewAccountContactScreen:UpdateButtonThatForcesCheckForDuplicates-btnInnerEl");
    private static final Button createAccountButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:Update-btnInnerEl']");
    private static final Title accountSummaryTitle = Title.id("AccountSummary:AccountSummaryScreen:ttlBar");
    private static final Link accTagsEmpTxt = Link.xpath("//li[contains(@data-recordindex,'0')]");
    private static final Link accTagsPersonTxt = Link.xpath("//li[contains(@data-recordindex,'1')]");
    private static final Button actionButton = Button.xpath("//*[@id='AccountGroup:AccountDetailMenuActions']");
    private static final Link chargesLink = Link.xpath("//*[contains(text(),'Charges')]");
    private static final Label newTransactionTxt = Label.xpath("//*[@id='AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction-textEl']");
    private static final Link generalTxt = Link.xpath("//*[@id='AccountGroup:AccountDetailMenuActions:AccountDetailMenuActions_NewTransaction:AccountDetailMenuActions_General-textEl']");
    private static final Label newTransWizTxt = Label.id("AccountNewTransactionWizard:NewTransactionAccountPoliciesScreen:ttlBar");
    private static final Button transWizNextButton = Button.xpath("//*[@id='AccountNewTransactionWizard:Next-btnInnerEl']");
    private static final Label transWizStep2Lbl = Label.xpath("//span[contains(text(),'New Transaction Wizard - Step 2 of 2')]");
    private static final Dropdown transWizCategoryTxt = Dropdown.xpath("//*[@id='AccountNewTransactionWizard:ChargeDetailsScreen:Type-inputEl']");
    private static final TextBox transWizAmountTxt = TextBox.xpath("///*[@id='AccountNewTransactionWizard:ChargeDetailsScreen:Amount-inputEl']");
    private static final Button transWizFinishButton = Button.xpath("//*[@id='AccountNewTransactionWizard:Finish-btnInnerEl']");
    private static final Label amountErrorMsgTxt = Label.xpath("//div[contains(text(),'Amount : You cannot create transaction higher than ')]");
    private static final Label accountChargeTypeTxt = Label.xpath("//div[contains(text(),'Collateral reserve adjustment')]");
    private static final CheckBox jvFalseChkBx = CheckBox.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:JointVentureId_false-boxLabelEl']");
    private static final Link jvAccountsImg = Link.xpath("//a[contains(text(),'Payment Plan for JV Accounts')]/../../../td[1]//img");
    private static final Button fixDueDateButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:FixDueDate_option1-boxLabelEl']");
    private static final TextBox dayOfMonthTxt = TextBox.id("NewAccount:NewAccountScreen:NewAccountDV:InvoicesFixedOnInputGroup:InvoiceDayOfMonth-inputEl");
    private static final Button paymentAddButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountPaymentPlansLV_tb:Add-btnInnerEl']");
    private static final Title addPaymentPlanTitle = Title.id("AccountAddPaymentPlanPopup:ttlBar");
    private static final Button upfrontPaymentAddButton = Button.xpath("//a[contains(text(),'Upfront Payment Plan')]/../../..//img");
    private static final Button addPaymentPlanButton = Button.xpath("//*[@id='AccountAddPaymentPlanPopup:AddSelectedPaymentPlans-btnInnerEl']");
    private static final Button addInvoicePlanButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountInvoiceStreamsLV_tb:addInvoiceStreamButton-btnInnerEl']");
    private static final Button invoicePlanOkButton = Button.xpath("//*[@id='AccountAddInvoiceStreamPopup:Update-btnInnerEl']");
    private static final TextBox companyNameTxt = TextBox.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:NameInputSet:GlobalContactNameInputSet:Name-inputEl']");
    private static final Button addNewCompanyButton = Button.xpath("//*[@id='NewAccount:NewAccountScreen:NewAccountDV:NewAccountContactsLV_tb:addNewContact:addNewCompany-textEl']");
    private static final Button accActButton = Button.id("AccountsGroup:AccountsMenuActions-btnInnerEl");
    private static final Button searchAccButton = Button.xpath("//div[@id='TabBar:AccountsTab:AccountNumberSearchItem_Button']");
    private static final Button newAccButton = Button.xpath("//span[@id='AccountsGroup:AccountsMenuActions:AccountsMenuActions_NewAccount-textEl']");
    private static final Label defaultCountryTxt = Label.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:globalAddressContainer:GlobalAddressInputSet:Country-inputEl']");
    private static final Label nationalNumber = Label.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:PhoneDetails_PSAInputSet:Mobile:GlobalPhoneInputSet:NationalSubscriberNumber-inputEl']");
    private static final Label fax = Label.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:PhoneDetails_PSAInputSet:Fax:GlobalPhoneInputSet:NationalSubscriberNumber-inputEl']");
    private static final Label homePhone = Label.xpath("//*[@id='NewAccountContactPopup:NewAccountContactScreen:AccountContactCV:AccountContactDetailDV:PhoneDetails_PSAInputSet:Home:GlobalPhoneInputSet:NationalSubscriberNumber-inputEl']");
    private static final Label numberOfPagesTxt = Label.xpath("//*[@id='AccountDetailCharges:AccountDetailChargesScreen:AccountDetailChargesListDetailPanel:ChargesLV']//*[contains(text(),'of ')]");
    private static final Button nextArrowInChargeButton = Button.xpath("//*[@id='AccountDetailCharges:AccountDetailChargesScreen:AccountDetailChargesListDetailPanel:ChargesLV']//*[contains(text(),'of ')]//../../a[3]");
    private static final Link accountSessionItem = Link.xpath("//*[@id='TabBar:AccountsTab:0:AccountSessionItemId-textEl']");
    private static final Label legendLabel = Label.xpath("//*[@id='AccountSummary:AccountSummaryScreen:SummaryChartPanelSet:0:legendLabel']");

    public void createAccount() {
        logger.info("Create Billing Center account with account type " + accountType);
        click(accountActionButton);
        click(newAccountButton);
        fillAccountInformation();
        addPaymentPlan();
        addInvoiceStream();
        addContact();
        click(createAccountButton);
        waitForElementVisible(accountSummaryTitle);
        logger.info("Billing Center account creation completed");
        addStepInfoToReport("Billing Center account creation completed");
    }

    private void addPaymentPlan() {
        click(paymentAddButton);
        logger.info("Add payment");
        waitForElementVisible(addPaymentPlanTitle);
        if (dataObject.getBillingCenter().getPaymentAllocation().equalsIgnoreCase("Monthly Payment Plan")) {
            click(jvAccountsImg);
            logger.info("Selected Monthly payment plan");
        } else {
            click(upfrontPaymentAddButton);
            logger.info("Selected default payment allocation plan");
        }
        waitForElementClickable(addPaymentPlanButton);
        click(addPaymentPlanButton);
    }

    private void addInvoiceStream() {
        click(addInvoicePlanButton);
        click(invoicePlanOkButton);
    }

    private void fillAccountInformation() {
        waitForElementVisible(newAccountTitleTxt);
        String accountNumber = getAttributeValue(accountNumberTxt, "value");
        dataObject.getBillingCenter().setAccountNoBilling(accountNumber);
        logger.info("Billing account number is " + accountNumber);
        sendText(accNameTxt, dataObject.getBillingCenter().getAccountName());
        click(jvFalseChkBx);
        waitForElementVisible(countryTxt);
        setDropDown(countryTxt, dataObject.getCountry());
        waitForPageLoadComplete(500);
        setDropDown(billingPlanTxt, "Billing Plan 1 - Dealers");
        select1stValueOfDropdown(delinquencyPlanTxt);
        waitForElementVisible(fixDueDateButton);
        click(fixDueDateButton);
        clearText(dayOfMonthTxt);
        sendText(dayOfMonthTxt, dataObject.getBillingCenter().getDayOfMonth());
        logger.info("Day of month provided is " + dataObject.getBillingCenter().getDayOfMonth());
        addStepInfoToReport("Filled all mandatory fields in account information section");
    }

    private void addContact() {
        click(addContactButton);
        if (accountType.equals(Person)) {
            fillPersonDetails();
        } else {
            fillCompanyDetails();
        }
        sendText(addressTxt, faker.address().streetAddress());
        sendText(postcodeTxt, rand.nextInt(10000, 99999));
        sendText(cityTxt, faker.address().city());
        click(primaryPayerRadioBtn);
        click(updateButton);
        waitForElementVisible(newAccountTitleTxt);
        addStepInfoToReport("Filled all mandatory fields in adding new contact form");
    }

    private void fillPersonDetails() {
        click(addNewPersonButton);
        waitForElementVisible(addAccContactText);
        setDropDownRandomValue(prefixButton);
        String firstName = faker.name().firstName().replace("'", "");
        sendText(firstnameTxt, firstName);
        dataObject.getBillingCenter().setFirstName(firstName);
        String lastName = faker.name().lastName().replace("'", "");
        sendText(lastnameTxt, lastName);
        dataObject.getBillingCenter().setLastName(lastName);
        String fullName = firstName + " " + lastName;
        dataObject.getBillingCenter().setFullName(fullName);
        logger.info("Billing Center contact full name is " + fullName);

        if (dataObject.getBillingCenter().getTags().equalsIgnoreCase("Employee")) {
            click(accTagsEmpTxt);
        } else {
            click(accTagsPersonTxt);
        }
        addStepInfoToReport("Fill Person details");
    }

    private void fillCompanyDetails() {
        click(addNewCompanyButton);
        waitForElementVisible(addAccContactText);
        String companyName = faker.company().name();
        sendText(companyNameTxt, companyName);
        logger.info("Set Billing Center company name as " + companyName);
        dataObject.getBillingCenter().setCompanyName(companyName);
        waitForElementVisible(tagsJV);
        click(tagsJV);
        addStepInfoToReport("Fill Company details");
    }

    public void searchPCAccount() {
        logger.info("Searching for Policy Center account " + dataObject.getAccountNumber() + " in Billing Center");
        sendText(searchAccountTxt, dataObject.getAccountNumber());
        click(accountSearchButton);
        waitForElementVisible(accountSearchResultTable);
        addStepInfoToReport("Searched for Policy Center account number " + dataObject.getAccountNumber());
        click(accountSearchResult);
    }

    public String getAccountNumber() {
        waitForTextToBe(accountNumber, dataObject.getAccountNumber());
        String accountValueInUI = getText(accountNumber);
        logger.info("Policy Center account from Billing Center is shown as " + accountValueInUI);
        return accountValueInUI;
    }

    public void createInsuredAcc() {
        logger.info("Non-List Bill Billing account creation starts");
        click(accountActionButton);
        click(newAccountButton);
        waitForElementVisible(newAccountTitleTxt);
        String accountNo = getAttributeValue(accountNumberTxt, "value");
        dataObject.getBillingCenter().setAccountNoBilling(accountNo);

        logger.info("Billing account number is " + accountNo);
        sendText(accNameTxt, dataObject.getBillingCenter().getAccountName());
        setDropDown(typeTxt, dataObject.getBillingCenter().getType());
        setDropDown(countryTxt, dataObject.getCountry());
        setDropDown(billingPlanTxt, dataObject.getBillingCenter().getBillingPlan());
        setDropDown(delinquencyPlanTxt, dataObject.getBillingCenter().getDelinquencyPlan());
        addContact();
        click(createAccountButton);
        waitForElementVisible(accountSummaryTitle);
        logger.info("Billing insured account creation completed");
        addStepInfoToReport("Billing insured account creation completed");
    }

    public boolean isAccountCreated() {
        logger.info("Check if account is created");
        waitForElementVisible(accountSummaryTitle);
        return isElementDisplayed(accountSummaryTitle);
    }

    public boolean validateGeneralTransactionAccount() {
        click(actionButton);
        hover(newTransactionTxt);
        click(generalTxt);
        waitForElementVisible(newTransWizTxt);
        click(transWizNextButton);
        waitForElementVisible(transWizStep2Lbl);
        setDropDown(transWizCategoryTxt,"Collateral reserve adjustment");
        String categoryVal = getAttributeValue(transWizCategoryTxt, "value");
        assert categoryVal.equalsIgnoreCase("Collateral reserve adjustment");
        logger.info(categoryVal + " value for account is displaying as expected in Category Dropdown on New Transaction Wizard Page");
        sendText(transWizAmountTxt, "450");
        click(transWizFinishButton);
        String errorMsg = getText(amountErrorMsgTxt);
        assert !errorMsg.isEmpty();
        logger.info("The error message " + errorMsg + " is displaying as expected");
        sendText(transWizAmountTxt, "300");
        click(transWizFinishButton);
        waitForElementVisible(accountSummaryTitle);
        click(chargesLink);
        String chargeType = getText(accountChargeTypeTxt);
        boolean result = chargeType.equalsIgnoreCase("Collateral reserve adjustment");
        if (result) {
            logger.info("The Charge type: " + chargeType + " is displaying as expected");
        }
        return result;
    }

    public String getDefaultCountry() {
        logger.info("Get default region in add new contact screen");
        click(accActButton);
        click(newAccButton);
        click(addContactButton);
        click(addNewPersonButton);
        String defaultCountry = getAttributeValue(defaultCountryTxt, "value");
        addStepInfoToReport("Get default region is " + defaultCountry);
        return defaultCountry;
    }

    public boolean validatePhoneLocalization() {
        logger.info("Validate default phone localization of Billing Center");
        boolean result;
        click(accActButton);
        click(newAccButton);
        click(addContactButton);
        click(addNewPersonButton);
        waitForElementVisible(addAccContactText);
        hover(nationalNumber);
        String mobHoverText = getAttributeValue(nationalNumber, "data-qtip");
        addStepInfoToReport("Actual hover mobile message is " + mobHoverText);
        String spainMobileMsg = "Current Region: Spain (34) <br>Example: 810 12 34 56 ext. 1234<br>Enter '+' and the country code for an international number";
        result = spainMobileMsg.equalsIgnoreCase(mobHoverText);
        if (result) {
            logger.info("[Current Region: Spain (34)] is the default message for Mobile field");
        } else {
            logger.info("Actual mobile text is " + mobHoverText);
        }
        hover(fax);
        String faxHoverText = getAttributeValue(fax, "data-qtip");
        addStepInfoToReport("Actual hover fax message is " + faxHoverText);
        result &= spainMobileMsg.equalsIgnoreCase(faxHoverText);
        if (result) {
            logger.info("[Current Region: Spain (34)] is the default message for Fax field");
        } else {
            logger.info("Actual Fax value is " + faxHoverText);
        }
        hover(homePhone);
        String homePhoneText = getAttributeValue(homePhone, "data-qtip");
        addStepInfoToReport("Actual hover home phone message is " + homePhoneText);
        result &= spainMobileMsg.equalsIgnoreCase(homePhoneText);
        if (result) {
            logger.info("[Current Region: Spain (34)] is the default message for HomePhone field");
        } else {
            logger.info("Actual home phone text is " + homePhoneText);
        }
        return result;
    }

    public void setChangeCost() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        clickMenu("Account");
        sendText(accountSearchTxt, dataObject.getBillingCenter().getAccountNoBilling());
        click(searchAccButton);
        click(chargesLink);
        double sum = 0, sum2 = 0, finalSum;
        String pageSize;
        int totalPagesInCharge=0;

        //handle this in try catch to handle if there is only single page
        try {
            pageSize = getText(numberOfPagesTxt);

            logger.info("Total number of pages are: " + pageSize);
            String[] pageNumber = pageSize.split(" ");
            logger.info("The Total number of pages are: " + pageNumber[1]);
            totalPagesInCharge = Integer.parseInt(pageNumber[1]);
        } catch (Exception e) {
            focusFirstInputField();
        }
        logger.info("Calculating sum of amount with respect to Policy change from page 1");
        double amtDouble = 0.0;
        Items changeList = Items.xpath("//*[@id='AccountDetailCharges:AccountDetailChargesScreen:AccountDetailChargesListDetailPanel:ChargesLV']//*[contains(text(),'Policy Change')]/../../td[12]/div");
        List<WebElement> page1PCChangeList = findElements(changeList);
        List<WebElement> page2PCChangeList;
        for (WebElement element : page1PCChangeList) {
            String textElem = getText(element);
            logger.info("UI Values are :" + textElem);
            int size = textElem.length() - 1;//where index can point to € symbol and can be removed
            logger.info("Actual size - 1 result is " + size);
            String amountWithoutEuro = textElem.substring(0, size);
            logger.info("Substring result is " + amountWithoutEuro);
            amountWithoutEuro.replace(',', '.');
            logger.info("Value after replacing coma character " + amountWithoutEuro);
            amountWithoutEuro = amountWithoutEuro.replaceAll("\\s", "").replace(",", ".");
            logger.info("Value after removing all white spaces " + amountWithoutEuro);//-21.36
            amtDouble = Double.parseDouble(amountWithoutEuro);
            logger.info("Value of Amount in doubles: " + amtDouble);
            sum = sum + amtDouble;//initially sum will be 0
        }
        logger.info("Finished calculating the sum of individual amounts from PC Change records from the table for page 1: " + sum);
        logger.info("Value of amount in doubles: " + amtDouble);

        double totalSum = Double.parseDouble(decimalFormat.format(sum));
        logger.info("The Sum of Policy Change in Page 1 after formatting into two decimal is " + totalSum);
        dataObject.getBillingCenter().setCostChange(totalSum);
        if (totalPagesInCharge > 1) {
            click(nextArrowInChargeButton);
            logger.info("Next arrow button has been triggered");
            //trying to find for Policy Change if any in the page 2, if not found handled in catch
            try {
                page2PCChangeList = findElements(changeList);
                logger.info("The size of the Amount column is: " + page2PCChangeList.size());
                for (WebElement webElement : page2PCChangeList) {
                    String textElem = webElement.getText();
                    int size = textElem.length() - 1;//where index can point to € symbol and can be removed
                    String amountWithoutEuro = textElem.substring(0, size);
                    amountWithoutEuro.replace(",", ".").replaceAll("\\s", "");
                    amtDouble = Double.parseDouble(amountWithoutEuro);
                    sum2 += amtDouble;
                }
                double totalSum1 = Double.parseDouble(decimalFormat.format(sum2));
                logger.info("The sum of Policy Cancellation in Charge screen is " + sum2);
                dataObject.getBillingCenter().setCostChange(totalSum1);
            } catch (Exception e) {
                focusFirstInputField();
            }
        }
        finalSum = sum + sum2;
        logger.info("Final sum for total number of pages is " + finalSum);
        double billingCostChange = Double.parseDouble(decimalFormat.format(finalSum));
        logger.info("The sum of Policy Cancellation in Charge screen is " + sum2);
        dataObject.getBillingCenter().setCostChange(billingCostChange);
    }

    public String getLegendLabel() {
        logger.info("Validate the currency symbol");
        clickMenu("Account");
        click(accountSessionItem);
        waitForElementVisible(accountSummaryTitle);
        String legendLabelValue = getText(legendLabel);
        addStepInfoToReport("Get current currency symbol is " + legendLabelValue);
        return legendLabelValue;
    }
}
