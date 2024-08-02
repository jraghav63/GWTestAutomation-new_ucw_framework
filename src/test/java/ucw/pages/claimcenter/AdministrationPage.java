package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import ucw.elements.*;

import java.io.File;

public class AdministrationPage extends ClaimCenter {
    private static final Logger logger = LogManager.getLogger(AdministrationPage.class);
    private static final Button userSecurityButton = Button.xpath("//span[text()='Users & Security']");
    private static final Label prodTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='Producer Code']");
    private static final Label brandTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='Brand']");
    private static final Label commTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='Commercial Name']");
    private static final Label rolesTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='Roles']");
    private static final Label userStatusTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='User Status']");
    private static final Label groupNameTitle = Label.xpath("//div[@class='x-box-inner']//span[text()='Group Name']");
    private static final Link userDisplayName = Link.xpath("//a[@id='AdminUserSearchPage:UserSearchScreen:AdminUserSearchResultsLV:0:DisplayName']");
    private static final Button userSearchButton = Button.xpath("//a[@id='AdminUserSearchPage:UserSearchScreen:UserSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final TextBox prodCodeTxt = TextBox.xpath("//input[@id='AdminUserSearchPage:UserSearchScreen:UserSearchDV:RRDICode_PSA-inputEl']");
    private static final Dropdown adminCountrySearch = Dropdown.id("AdminUserSearchPage:UserSearchScreen:UserSearchDV:CCAddressBookSearchLocationInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:Country-inputEl");
    private static final Label commNameTxt = Label.xpath("(//div[@style='text-align:left;text-align:left !important'])[5]");
    private static final Title userDetailsTitleTxt = Title.id("UserDetailPage:UserDetailScreen:ttlBar");
    private static final Button printButton = Button.xpath("//span[@id='AdminUserSearchPage:UserSearchScreen:AdminUserSearchResultsLV_tb:AdminUserSearchResult_PrintButton-btnInnerEl']");
    private static final CheckBox exportChoice = CheckBox.xpath("//input[@id='PrintOptionPopup:PrintOptionPopupScreen:PrintOptionDV:ExportChoice_Choice-inputEl']");
    private static final Button exportOkButton = Button.xpath("//span[@id='PrintOptionPopup:PrintOptionPopupScreen:Ok-btnInnerEl']");
    private static final Title importDataLbl = Title.id("ImportWizard:ImportWizard_UploadScreen:ttlBar");
    private static final Button utilitiesButton = Button.xpath("//span[text()='Utilities']");
    private static final Button businessSettingsButton = Button.xpath("//span[text()='Business Settings']");
    private static final Button scriptParametersButton = Button.id("Admin:MenuLinks:Admin_Utilities:Utilities_ScriptParametersPage");
    private static final Label scriptParamLbl = Label.id("ScriptParametersPage:ScriptParametersScreen:0");
    private static final Form scriptParamList = Form.id("ScriptParametersPage:ScriptParametersScreen:ScriptParametersLV-body");
    private static final Button scriptParamNextButton = Button.xpath("//a[@data-qtip='Next Page']");
    private static final Form paramDetailBody = Form.id("ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV-tbody");
    private static final Label paramDetailName = Label.id("ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV:Name-inputEl");
    private static final Button repairerRatingButton = Button.id("Admin:MenuLinks:Admin_BusinessSettings:BusinessSettings_RepairersRating_PSA");
    private static final Title repairerRatingLbl = Title.id("RepairersRating_PSA:RepairersRatingScreen:ttlBar");
    private static final Table repairerList = Table.id("RepairersRating_PSA-table");
    private static final Button updateRepairerRatingButton = Button.id("RepairersRating_PSA:RepairersRatingScreen:RepairersRating_PSALV_tb:Update-btnInnerEl");
    private static final Button editRepairerRatingButton = Button.id("RepairersRating_PSA:RepairersRatingScreen:RepairersRating_PSALV_tb:Edit-btnInnerEl");
    private static final Title activityRulesLabel = Title.id("ActivityRules:ttlBar");
    private static final Link upToScriptParameterLink = Link.id("ScriptParameterDetail:ScriptParameterDetail_UpLink");
    private static final Button editParamItemButton = Button.id("ScriptParameterDetail:ScriptParameterDetailScreen:Edit-btnWrap");
    private static final Button updateParamItemButton = Button.id("ScriptParameterDetail:ScriptParameterDetailScreen:Update-btnInnerEl");
    private static final CheckBox noCheckbox = CheckBox.xpath("//input[@id='ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV:BitValue_false-inputEl']");
    private static final CheckBox yesCheckbox = CheckBox.xpath("//input[@id='ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV:BitValue_true-inputEl']");
    private static final Label paramValue = Label.xpath("//div[@id='ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV:BitValue-inputEl']");
    private static final TextBox valueTextField = TextBox.xpath("//input[@id='ScriptParameterDetail:ScriptParameterDetailScreen:ScriptParameterDetailDV:IntegerValue-inputEl']");

    public boolean validateProducerCode() {
        logger.info("Validate Producer code " + dataObject.getProducerCode() + " in User Search");
        click(userSecurityButton);
        sendText(prodCodeTxt, dataObject.getProducerCode());
        setDropDown(adminCountrySearch, dataObject.getCountry());
        click(userSearchButton);
        waitForElementVisible(prodTitle);
        logger.info("Producer Code title is present");
        boolean result = isElementDisplayed(brandTitle);
        logger.info("Brand title is present");
        result &= isElementDisplayed(commTitle);
        logger.info("Commercial name title is present");
        result &= isElementDisplayed(rolesTitle);
        logger.info("Roles title is present");
        result &= isElementDisplayed(userStatusTitle);
        logger.info("User status title is present");
        result &= isElementDisplayed(groupNameTitle);
        logger.info("Group name title is present");
        String commNameInUI = getText(commNameTxt);
        logger.info("Commercial name of producer code is " + commNameInUI);
        dataObject.getClaimCenter().setCommercialName(commNameInUI);
        logger.info("Clicking on user");
        addStepInfoToReport("Validate Producer code " + dataObject.getProducerCode() + " in User Search");
        click(userDisplayName);
        result &= isElementDisplayed(userDetailsTitleTxt);
        addStepInfoToReport("Check if user details display");
        logger.info("Producer code in user search is validated successfully");
        return result;
    }

    public void printReport() {
        logger.info("Validating Printing report in User Search");
        click(userSecurityButton);
        sendText(prodCodeTxt, dataObject.getProducerCode());
        setDropDown(adminCountrySearch, dataObject.getCountry());
        addStepInfoToReport("Validate Printing report in User search");
        click(userSearchButton);
        click(printButton);
        Label printPDF = Label.xpath("//span[text()='Print (as PDF)']");
        assert isElementDisplayed(printPDF);
        logger.info("Print (as PDF) option is present");
        Label exportCSV = Label.xpath("//span[text()='Export (as CSV)']");
        assert isElementDisplayed(exportCSV);
        logger.info("Export (as CSV) option is present");
        Label customCSV = Label.xpath("//span[text()='Custom Export (as CSV)']");
        assert isElementDisplayed(customCSV);
        logger.info("Custom Export (as CSV) option is present");
        addStepInfoToReport("All export option are displayed");
        click(exportChoice);
        logger.info("Export (as CSV) option is selected");
        click(exportOkButton);
    }

    public boolean isDataExported(String fileName) {
        String userHome = System.getProperty("user.home");
        File file = new File(userHome + File.separator + "Downloads" + File.separator + fileName);
        waitForFileDownloadComplete(file);
        boolean result = file.exists();
        if (result) {
            logger.info("File " + fileName + " is exported successfully");
            file.delete();
        }
        return result;
    }

    public void clickUtilities() {
        logger.info("Click Utilities menu in Claim Administration page");
        click(utilitiesButton);
        waitForElementVisible(importDataLbl);
        waitForTextNotNull(importDataLbl);
        addStepInfoToReport("Clicked Utilities menu in Claim Administration page");
    }

    public void goToScriptParameter() {
        logger.info("Open the Script Parameter from Utilities menu");
        click(scriptParametersButton);
        waitForElementVisible(scriptParamList);
        waitForTextNotNull(scriptParamLbl);
        addStepInfoToReport("Opened the Script Parameter from Utilities menu");
    }

    public boolean isParamExist(String paramName) {
        logger.info("Check if the script parameter " + paramName + " exist");
        boolean result = false;
        Label paramItem = Label.xpath("//a[text()='" + paramName + "']");
        int page = 1;
        do {
            try {
                if (isElementDisplayed(paramItem, 3)){
                    addStepInfoToReport("Found the parameter " + paramName + " at page " + page);
                    result = true;
                    break;
                }
            } catch (NoSuchElementException e) {
                addStepInfoToReport("Still not found the parameter " + paramName + " at page " + page);
                page++;
                click(scriptParamNextButton);
                waitForPageLoadComplete(500);
            }
        } while (!getAttributeValue(scriptParamNextButton, "class").contains("x-btn-disabled"));
        return result;
    }

    public void openParam(String paramName) {
        Label paramItem = Label.xpath("//a[text()='" + paramName + "']");
        int page = 1;
        do {
            try {
                if (isElementDisplayed(paramItem, 3)) {
                    addStepInfoToReport("Found the parameter " + paramName + " at page " + page);
                    break;
                }
            } catch (NoSuchElementException e) {
                addStepInfoToReport("Still not found the parameter " + paramName + " at page " + page);
                page++;
                click(scriptParamNextButton);
                waitForPageLoadComplete(500);
            }
        } while (!getAttributeValue(scriptParamNextButton, "class").contains("x-btn-disabled"));
        click(paramItem);
        waitForElementVisible(paramDetailBody);
        waitForTextToBe(paramDetailName, paramName);
    }

    public void enableParam(boolean enabled) {
        waitForElementVisible(editParamItemButton);
        click(editParamItemButton);
        waitForElementVisible(updateParamItemButton);
        waitForPageLoadComplete();
        if (enabled) {
            click(yesCheckbox);
        } else {
            click(noCheckbox);
        }
        click(updateParamItemButton);
        waitForElementVisible(editParamItemButton);
    }

    public void editParamValue(String newValue) {
        logger.info("Edit current value to " + newValue);
        waitForElementVisible(editParamItemButton);
        click(editParamItemButton);
        waitForElementVisible(updateParamItemButton);
        waitForPageLoadComplete();
        clearText(valueTextField);
        sendText(valueTextField, newValue);
        click(updateParamItemButton);
        waitForElementVisible(editParamItemButton);
        addStepInfoToReport("Updated the current parameter with new value " + newValue);
    }

    public String getParamValue() {
        waitForTextNotNull(paramValue);
        return getText(paramValue);
    }

    public void clickBusinessSettings() {
        logger.info("Click Business Settings menu in Claim Administration page");
        click(businessSettingsButton);
        waitForTextNotNull(activityRulesLabel);
        addStepInfoToReport("Clicked Business Settings menu in Claim Administration page");
    }

    public void clickRepairerRatingSection() {
        logger.info("Open the Repairer's Rating from Business Settings menu");
        click(repairerRatingButton);
        waitForElementVisible(repairerRatingLbl);
        waitForTextNotNull(repairerRatingLbl);
        addStepInfoToReport("Opened the Repairer's Rating from Business Settings menu");
    }

    public void backToScriptParameterSection() {
        logger.info("Open the Script Parameter from Utilities menu");
        click(upToScriptParameterLink);
        waitForElementVisible(scriptParamList);
        waitForTextNotNull(scriptParamLbl);
        addStepInfoToReport("Go back to Script Parameter page 1");
    }

    public boolean isRepairerListDisplay() {
        waitForElementVisible(repairerList);
        return isElementDisplayed(repairerList);
    }

    public void enableRating(boolean setRating) {
        click(editRepairerRatingButton);
        waitForElementVisible(updateRepairerRatingButton);
        CheckBox checkbox;
        if (setRating) {
            checkbox = CheckBox.xpath("((//div[text()='AUTO']/../../../..//td)[3]/div//input)[1]");
        } else {
            checkbox = CheckBox.xpath("((//div[text()='AUTO']/../../../..//td)[3]/div//input)[2]");
        }
        click(checkbox);
        click(updateRepairerRatingButton);
        waitForElementVisible(editRepairerRatingButton);
    }

    public String getCurrentRatingStatus(String repairer) {
        Label status = Label.xpath("(//div[text()='" + repairer + "']/../../../..//td)[3]/div");
        waitForTextNotNull(status);
        return getText(status);
    }
}
