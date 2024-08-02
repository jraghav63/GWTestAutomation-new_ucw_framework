package ucw.pages.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import java.io.File;

import static ucw.enums.Environments.DEV;

public class FileClaimPage extends Portal {
    private static final Logger logger = LogManager.getLogger(FileClaimPage.class);
    private static final Title newClaimTtlTxt = Title.xpath("//h1[contains(text(),'New Claim')]");
    private static final TextBox providePolicyNumberTxt = TextBox.xpath("//*[@id='page-inner']//input[@name='PolicyNumber']");
    private static final TextBox vehicleMileageTxt = TextBox.xpath("//input[contains(@type,'number')]");
    private static final Dropdown causeCategoryDdTxt = Dropdown.xpath("(//div//select)[2]");
    private static final Dropdown causeDdTxt = Dropdown.xpath("(//div//select)[5]");
    private static final Dropdown defaultCategoryDdTxt = Dropdown.xpath("(//div//select)[3]");
    private static final Dropdown defaultSubgroupDdTxt = Dropdown.xpath("(//div//select)[6]");
    private static final Button pSubmitButton = Button.xpath("//*[@id='page-inner']//button[text()='Submit Claim']");
    private static final Dropdown documentTypeDdTxt = Dropdown.xpath("//select[@options='documentTypes']");
    private static final Button uploadDocument = Button.xpath("//input[@type='file']");
    private static final Button portalClaimNextButton = Button.xpath("//*[@id='page-inner']//button[text()='Next']");
    private static final Form uploadDocViewer = Form.tagName("gw-file-upload-viewer");
    private static final Label portalClaimMessageTxt = Label.xpath("//div[contains(text(),'Claim was successfully submitted')]");
    private static final Label portalClaimNoTxt = Label.xpath("//div[contains(text(),'Claim reference number')]");
    private static final Button pNextButton = Button.xpath("//*[@id='page-inner']//span[text()='Next']");
    private static final Label creatingDraftClaimLbl = Label.xpath("//*[contains(text(),'Creating personal auto draft claim')]");
    private static final Form claimSummaryScreen = Form.xpath("//section[contains(@class,'gw-claim-form-summary')]");
    private static final Button agreeButton = Button.xpath("//button[contains(text(),'Agree')]");
    private static final Dropdown prefixDropdown = Dropdown.xpath("//span[text()='Prefix']/../following-sibling::div//select");
    private static final TextBox emailField = TextBox.xpath("//span[text()='Email']/../following-sibling::div//input");
    private static final Button newContactNextButton = Button.xpath("//button[@ng-click='next(contactDetailsForm)']");
    private static final Button submitClaimButton = Button.xpath("//button[contains(@class,'gw-btn-primary')]");
    private static final Form claimDetailsForm = Form.xpath("//form[@name='claimDetailsForm']");
    private static final String[] docTypes = {"Diagnostic trouble code List", "Quote", "Work order"}; // Mandatory document types

    public void createClaim() {
        logger.info("Portal Claim creation starts");
        waitForElementVisible(newClaimTtlTxt);
        selectPolicy();
        fillLostDetails();
        uploadDocument();
        finishSubmittingClaim();
    }

    private void inputPolicyNumber() {
        String portalPolicy = dataObject.getPolicyNumber();
        clearText(providePolicyNumberTxt);
        sendText(providePolicyNumberTxt, portalPolicy);
        waitForPageLoadComplete();
        logger.info("Provided Policy Number: " + portalPolicy);
        addStepInfoToReport("Provide Policy Number: " + portalPolicy);
    }

    public void clickNextAfterSelectPolicy() {
        click(pNextButton);
        waitForElementVisible(creatingDraftClaimLbl);
    }

    public void selectPolicy() {
        inputPolicyNumber();
        clickNextAfterSelectPolicy();
    }

    public void fillLostDetails() {
        waitForElementVisible(claimDetailsForm);
        selectRandom(causeCategoryDdTxt);
        selectRandom(causeDdTxt);
        selectRandom(defaultCategoryDdTxt);
        selectRandom(defaultSubgroupDdTxt);
        sendText(vehicleMileageTxt, dataObject.getPortal().getVehicleMileage());
        addStepInfoToReport("Filled out all loss details");
        click(portalClaimNextButton);
    }

    public void uploadDocument() {
        waitForElementVisible(uploadDocViewer);
        String absolutePath;
        File docFile;
        String uploadedDoc = "//p[text()='{type}']/../../..";
        Link byDocumentItem;
        for (String docType : docTypes) {
            docFile = new File("src/test/resources/docs/" + docType + ".txt");
            absolutePath = docFile.getAbsolutePath();
            logger.info("Upload doc " + absolutePath + " for document type " + docType);
            selectByText(documentTypeDdTxt, docType);
            waitForPageLoadComplete(1000);
            sendTextOnly(uploadDocument, absolutePath);
            byDocumentItem = Link.xpath(uploadedDoc.replace("{type}", docType));
            waitForElementVisible(byDocumentItem);
            logger.info("Uploaded document successfully for document type " + docType);
        }
        addStepInfoToReport("Uploaded all documents");
        click(portalClaimNextButton);
    }

    public void finishSubmittingClaim() {
        click(portalClaimNextButton);
        if (!getCurrentEnvironment().equals(DEV)) {
            addPrimaryContactDetails();
        }
        click(pSubmitButton);
        waitForElementVisible(claimSummaryScreen);
        addStepInfoToReport("New claim is created in Portal");
    }

    public boolean isClaimCreated() {
        waitForTextNotNull(portalClaimMessageTxt);
        waitForTextNotNull(portalClaimNoTxt);
        String claimMessageLine = getText(portalClaimNoTxt);
        logger.info(claimMessageLine);
        addStepInfoToReport(claimMessageLine);
        dataObject.getClaimCenter().setClaimNo(claimMessageLine.substring(23));
        return isElementDisplayed(portalClaimNoTxt);
    }

    public void clickAgreeOnSBPopup() {
        logger.info("Include service box popup appear, click Agree");
        addStepInfoToReport("Include service box popup appear, click Agree");
        switchToActiveElement();
        click(agreeButton);
    }

    public void addPrimaryContactDetails() {
        logger.info("Include service box popup appear, click Agree");
        selectRandom(prefixDropdown);
        sendText(emailField, "stellantis_tester@email.com");
        addStepInfoToReport("Added email to primary contact");
        click(newContactNextButton);
    }

    public void submit() {
        waitForElementVisible(claimSummaryScreen);
        click(submitClaimButton);
        addStepInfoToReport("New claim is created in Portal");
    }
}
