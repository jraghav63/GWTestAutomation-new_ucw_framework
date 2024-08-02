package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import java.io.File;

import static ucw.enums.Countries.Italy;
import static ucw.enums.Environments.DEV;
import static ucw.enums.ManagementType.DELEGATED;

public class AdministrationPage extends PolicyCenter {
    private static final Logger logger = LogManager.getLogger(AdministrationPage.class);
    private static final Form adminProdCodeDetails = Form.id("AdminProducerCodeDetail/ProducerCodeDetail_BasicCard-tbody");
    private static final CheckBox jvNoTxt = CheckBox.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:jointventure_PSA_false-inputEl']");
    private static final Label mgmtTypeSelectedTxt = Label.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:managementtype-inputEl']");
    private static final Button adminProdEditButton = Button.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:Edit-btnInnerEl']");
    private static final Button adminUserEditButton = Button.id("UserDetailPage:UserDetailScreen:UserDetailToolbarButtonSet:Edit-btnInnerEl");
    private static final Button adminUpdateButton = Button.xpath("//span[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:Update-btnInnerEl']");
    private static final Button userUpdateButton = Button.id("UserDetailPage:UserDetailScreen:UserDetailToolbarButtonSet:Update-btnInnerEl");
    private static final Dropdown adminMgmtDdTxt = Dropdown.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:managementtype-inputEl']");
    private static final TextBox collateralVehicleTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:collateralVehicle_PSA-inputEl']");
    private static final TextBox collateralTargetTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:collateralTarget_PSA-inputEl']");
    private static final TextBox expectedVOPVolumeTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:expectedvopvolume-inputEl']");
    private static final TextBox mandetSEPATxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:mandatesepa_PSA-inputEl']");
    private static final TextBox adminLossRationTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:lossratio_PSA-inputEl']");
    private static final Tab documentTab = Tab.xpath("//span[contains(text(),'Documents')]");
    private static final Label rrdiCodeValueLbl = Label.xpath("//div[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:Code-inputEl']");
    private static final TextBox rrdiCodeTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:Code-inputEl']");
    private static final Label addressSummaryLbl = Label.xpath("//div[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:AddressSummary-bodyEl']/div");
    private static final Label producerCodeLabel = Label.xpath("//div[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:label-bodyEl']/div");
    private static final TextBox dealerAddressTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox dealerAddress2Txt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:AddressLine2-inputEl']");
    private static final TextBox dealerCityTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:City-inputEl']");
    private static final TextBox provinceTxt = TextBox.xpath("//input[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:Province-inputEl']");
    private static final Button usersLeftPanelButton = Button.xpath("//*[@id='Admin:MenuLinks:Admin_UsersAndSecurity:UsersAndSecurity_AdminUserSearchPage']/div/span");
    private static final Title usersHeadingTxt = Title.id("AdminUserSearchPage:UserSearchScreen:ttlBar");
    private static final Button userSearchButton = Button.id("AdminUserSearchPage:UserSearchScreen:UserSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search");
    private static final TextBox userNameTxt = TextBox.xpath("//input[@id='AdminUserSearchPage:UserSearchScreen:UserSearchDV:Username-inputEl']");
    private static final Link userSearchResult = Link.xpath("(//div[@id='AdminUserSearchPage:UserSearchScreen:UserSearchResultsLV-body']//td)[2]/div/a");
    private static final Title userHeadingTxt = Title.id("UserDetailPage:UserDetailScreen:ttlBar");
    private static final Form userDetails = Form.id("UserDetailPage:UserDetailScreen:0-body");
    private static final TextBox userFirstNameTxt = TextBox.xpath("//input[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox userMiddleNameTxt = TextBox.xpath("//input[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:MiddleName-inputEl']");
    private static final TextBox userLastNameTxt = TextBox.xpath("//input[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Label userFNLbl = Label.xpath("//div[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final Label userMNLbl = Label.xpath("//div[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:MiddleName-inputEl']");
    private static final Label userLNLbl = Label.xpath("//div[@id='UserDetailPage:UserDetailScreen:UserDetailDV:UserDetailCommons:UserDetailInputSet:Name:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Button countryButton = Button.xpath("//input[@id = 'AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:AddressOwnerAddressInputSet:globalAddressContainer:GlobalAddressInputSet:Country-inputEl']");
    private static final Button uploadDocButton = Button.xpath("//input[@data-ref='fileInputEl']");
    private static final Link uploadedDocItem = Link.id("AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_DocumentsLV:0:filename");
    private static final Button removeDocButton = Button.id("AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_DocumentsLV_tb:RemoveCommissionPlanButton-btnInnerEl");
    private static final Dropdown labelDdTxt = Dropdown.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_Basic_PSADV:label-inputEl']");
    private static final TextBox prodCodeTextBoxTxt = TextBox.xpath("//*[@id='AdminProducerCodeSearch:AdminProducerCodeSearchScreen:ProducerCodeSearchDV:Code-inputEl']");
    private static final Title adminProdCodeHeadingTxt = Title.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ttlBar']");
    private static final Title prodCodeHeadingTxt = Title.xpath("//*[@id='AdminProducerCodeSearch:AdminProducerCodeSearchScreen:ttlBar']");
    private static final Button adminSearchButton = Button.xpath("//*[@id='AdminProducerCodeSearch:AdminProducerCodeSearchScreen:ProducerCodeSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final TextBox orgTxt = TextBox.xpath("//*[@id='AdminProducerCodeSearch:AdminProducerCodeSearchScreen:ProducerCodeSearchDV:Organization-inputEl']");
    private static final Label currency = Label.xpath("//*[@id='AdminProducerCodeDetail:ProducerCodeDetailScreen:ProducerCodeDetail_PSADV:Currency-bodyEl']");
    private static final Button productCodeLeftPanelButton = Button.xpath("//*[@id='Admin:MenuLinks:Admin_UsersAndSecurity:UsersAndSecurity_AdminProducerCodeSearch']/div/span");


    public void searchUser(String username) {
        logger.info("Search user " + username + " in Administrator panel");
        click(usersLeftPanelButton);
        waitForElementVisible(usersHeadingTxt);

        sendText(userNameTxt, username);
        click(userSearchButton);
        waitForElementVisible(userSearchResult);
        click(userSearchResult);
        waitForElementVisible(userHeadingTxt);
        waitForElementVisible(userDetails);
    }

    public void searchDealer() {
        waitForElementVisible(productCodeLeftPanelButton);
        click(productCodeLeftPanelButton);
        String prodCode = dataObject.getProducerCode().trim();
        if (isElementDisplayed(prodCodeTextBoxTxt, 10)) {
            sendText(prodCodeTextBoxTxt, prodCode);
            if (!getCurrentEnvironment().equals(DEV)) {
                clearText(orgTxt);
            }
            addStepInfoToReport("Search producer code " + dataObject.getProducerCode());
            click(adminSearchButton);
            Link prodCodeSelection = Link.xpath("//a[contains(text(),'" + prodCode + "')]");
            waitForElementVisible(prodCodeSelection);
            click(prodCodeSelection);
        }
        waitForElementVisible(adminProdCodeDetails);
        addStepInfoToReport("Opened producer code " + prodCode + "'s details");
    }

    public void editUserBasicsInfo() {
        logger.info("Edit user basic information");
        click(adminUserEditButton);
        waitForElementVisible(userUpdateButton);
        clearText(userFirstNameTxt);
        sendText(userFirstNameTxt, dataObject.getPolicyCenter().getUserFirstName());
        clearText(userMiddleNameTxt);
        sendText(userMiddleNameTxt, dataObject.getPolicyCenter().getUserMiddleName());
        clearText(userLastNameTxt);
        sendText(userLastNameTxt, dataObject.getPolicyCenter().getUserLastName());
        finishUpdateUser();
    }

    public void editDealerBasicsInfo() {
        logger.info("Edit dealer basic information");
        click(adminProdEditButton);
        waitForElementVisible(adminUpdateButton);
        clearText(rrdiCodeTxt);
        sendText(rrdiCodeTxt, dataObject.getPolicyCenter().getDealerNameUpdated());
        setDropDown(countryButton, dataObject.getCountry());
        if (dataObject.getCountry().equals(Italy)) {
            waitForElementVisible(provinceTxt);
            clearText(provinceTxt);
            sendText(provinceTxt, dataObject.getPolicyCenter().getDealerProvince());
        }
        clearText(dealerAddressTxt);
        sendText(dealerAddressTxt, dataObject.getPolicyCenter().getDealerAddress());
        clearText(dealerAddress2Txt);
        sendText(dealerAddress2Txt, dataObject.getPolicyCenter().getDealerAddress2());
        clearText(dealerCityTxt);
        sendText(dealerCityTxt, dataObject.getPolicyCenter().getDealerCity());
        finishUpdateDealer();
    }

    public void editDealerName(String name) {
        sendText(rrdiCodeTxt, name);
        finishUpdateDealer();
    }

    public boolean isDealerUpdated() {
        String rrdiCode = getText(rrdiCodeValueLbl);
        String address = getText(addressSummaryLbl);
        String label = getText(producerCodeLabel);
        String managementType = getText(mgmtTypeSelectedTxt);

        boolean result = rrdiCode.equals(dataObject.getPolicyCenter().getDealerNameUpdated());
        result &= address.contains(dataObject.getCountry().toString());
        result &= address.contains(dataObject.getPolicyCenter().getDealerAddress());
        result &= address.contains(dataObject.getPolicyCenter().getDealerAddress2());
        result &= address.contains(dataObject.getPolicyCenter().getDealerCity());
        if (dataObject.getCountry().equals(Italy)) {
            result &= address.contains(dataObject.getPolicyCenter().getDealerProvince());
        }
        result &= label.equals(dataObject.getPolicyCenter().getAdminLabel());
        result &= managementType.equals(dataObject.getPolicyCenter().getManagementType());
        if (result) {
            logger.info("All dealer info is updated correctly");
        } else {
            logger.info("Some dealer information is not updated correctly");
        }
        return result;
    }

    public boolean isUserUpdated() {
        String fn = getText(userFNLbl);
        String mn = getText(userMNLbl);
        String ln = getText(userLNLbl);

        boolean result = fn.equals(dataObject.getPolicyCenter().getUserFirstName());
        result &= mn.contains(dataObject.getPolicyCenter().getUserMiddleName());
        result &= ln.contains(dataObject.getPolicyCenter().getUserLastName());

        if (result) {
            logger.info("All user info is updated correctly");
        } else {
            logger.info("Some user information is not updated correctly");
        }
        return result;
    }

    public void setupDealer() {
        logger.info("Set up producer code " + dataObject.getProducerCode() + " in Administration panel");
        String targetCountry = dataObject.getCountry().toString();
        String targetLabel = dataObject.getPolicyCenter().getAdminLabel();
        String targetMgmtType = dataObject.getPolicyCenter().getManagementType();
        searchDealer();
        logger.info("Management type is " + targetMgmtType);
        if (targetMgmtType.equals(DELEGATED)) {
            clickEditDealer();
            setDropDown(countryButton, dataObject.getCountry());
            logger.info("Country is set as per the test data " + dataObject.getCountry());
            setDropDown(labelDdTxt, dataObject.getPolicyCenter().getAdminLabel());
            logger.info("Label is set as per the test data " + dataObject.getPolicyCenter().getAdminLabel());
            click(jvNoTxt);
            setDropDown(adminMgmtDdTxt, DELEGATED);
            clearText(adminLossRationTxt);
            clearText(collateralVehicleTxt);
            sendText(collateralVehicleTxt, dataObject.getPolicyCenter().getCollateralVehicle());
            clearText(collateralTargetTxt);
            sendText(collateralTargetTxt, dataObject.getPolicyCenter().getCollateralTarget());
            clearText(expectedVOPVolumeTxt);
            sendText(expectedVOPVolumeTxt, dataObject.getPolicyCenter().getExpectedVOP());
            clearText(mandetSEPATxt);
            sendText(mandetSEPATxt, dataObject.getPolicyCenter().getMandetSEPA());
            clearText(adminLossRationTxt);
            sendText(adminLossRationTxt, dataObject.getPolicyCenter().getLossRatio());
            addStepInfoToReport("Changed all necessary fields");
            finishUpdateDealer();
        } else {
            String addressSummary = getText(addressSummaryLbl);
            String currentLabel = getText(producerCodeLabel);
            String currentManagementType = getText(mgmtTypeSelectedTxt);
            if (!addressSummary.contains(targetCountry) ||
                    !currentLabel.equalsIgnoreCase(targetLabel) ||
                    !currentManagementType.equalsIgnoreCase(targetMgmtType)) {
                clickEditDealer();
                setDropDown(countryButton, targetCountry);
                logger.info("Country is set as " + targetCountry);
                setDropDown(labelDdTxt, targetLabel);
                logger.info("Label is set as " + targetLabel);
                click(jvNoTxt);
                clickPageBody();
                setDropDown(adminMgmtDdTxt, targetMgmtType);
                addStepInfoToReport("Changed all necessary fields");
                finishUpdateDealer();
            }
        }
    }

    public void clickEditDealer() {
        logger.info("Click edit dealer button");
        click(adminProdEditButton);
        waitForElementVisible(adminUpdateButton);
        addStepInfoToReport("Click Edit dealer button");
    }

    private void finishUpdateUser() {
        click(userUpdateButton);
        waitForElementVisible(adminUserEditButton);
    }
    private void finishUpdateDealer() {
        waitForElement(adminUpdateButton);
        click(adminUpdateButton);
        waitForElementVisible(adminProdEditButton);
        addStepInfoToReport("Updated producer code successfully");
    }

    public void uploadDocument(String uploadFilePath) {
        logger.info("Upload producer code document");
        click(productCodeLeftPanelButton);
        waitForElementVisible(prodCodeHeadingTxt);
        clearText(orgTxt);

        String producerCode = dataObject.getProducerCode().trim();
        logger.info("Producer code is " + producerCode);
        sendText(prodCodeTextBoxTxt, producerCode);
        click(adminSearchButton);

        Link prodCodeSelection = Link.xpath("//a[contains(text(),'" + producerCode + "')]");
        waitForElementVisible(prodCodeSelection);
        click(prodCodeSelection);
        waitForElementVisible(adminProdCodeHeadingTxt);
        click(adminProdEditButton);
        click(documentTab);

        File document = new File(uploadFilePath);
        sendTextOnly(uploadDocButton, document.getAbsolutePath());
        waitForElementVisible(uploadedDocItem);
        logger.info("File uploaded successfully");
        addStepInfoToReport("File uploaded successfully");
        click(adminUpdateButton);
        click(uploadedDocItem);
    }

    public void deleteUploadedDocument() {
        click(adminProdEditButton);
        click(Tab.xpath("//div[@class='x-grid-checkcolumn']"));
        click(removeDocButton);
        waitForElementDisappear(uploadedDocItem);
        addStepInfoToReport("Uploaded doc is removed");
        click(adminUpdateButton);
        waitForPageLoadComplete(500);
    }

    public String getDefaultCurrency(String producerCode) {
        logger.info("Currency localization validation of Policy Center starts");
        click(productCodeLeftPanelButton);
        waitForElementVisible(prodCodeHeadingTxt);
        clearText(orgTxt);
        sendText(prodCodeTextBoxTxt, producerCode);
        click(adminSearchButton);
        Link prodCodeSelection = Link.xpath("//a[contains(text(),'" + producerCode + "')]");
        click(prodCodeSelection);
        waitForElementVisible(adminProdCodeHeadingTxt);
        String currencyValue = getText(currency);
        addStepInfoToReport("Default currency of producer code " + producerCode + " is " + currencyValue);
        return currencyValue;
    }
}
