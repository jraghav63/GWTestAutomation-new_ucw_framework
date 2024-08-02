package ucw.pages.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;
import ucw.exceptions.NonExistProducerCodeException;
import ucw.exceptions.PortalLanguageSettingException;
import ucw.pages.BasePage;
import ucw.utils.DateHelper;
import ucw.utils.TestNGResultHandling;

import java.io.File;

import static ucw.enums.ContactType.Personal;
import static ucw.enums.Languages.ENGLISH;
import static ucw.pages.portal.PortalLoginPage.userName;

public class Portal extends BasePage {
    private static final Logger logger = LogManager.getLogger(Portal.class);
    private static SubmissionPage submissionPage;
    private static FileClaimPage fileClaimPage;
    private static PoliciesPage policiesPage;
    private static final Button preferencesButton = Button.xpath("//a[@ui-sref='preferences']");
    private static final TextBox usernameField = TextBox.xpath("//input[@type='text']");
    private static final Button newSubmissionButton = Button.xpath("//*[@id='page-inner']/div[2]//span[contains(text(),' New Submission ')]");
    private static final TextBox searchTxt = TextBox.xpath("//*[@id='SearchParam']");
    private static final Button paSearchButton = Button.xpath("//span[@class='gw-icon fa fa-search']");
    private static final Button homeButton = Button.xpath("//*[@id='page-inner']//div[2]//div[2]/div[2]/gw-portal-nav/div/ul/li[1]/a");
    private static final Link policyInUI = Link.xpath("//h1[contains(text(),'Policies')]/following::a[contains(@href,'policies')]");
    private static final Label accountInUITxt = Label.xpath("//h1[contains(text(),'Accounts')]//following::a[contains(@href,'accounts')][2]");
    private static final Button fileClaimButton = Button.xpath("//*[@id='page-inner']/div[2]//span[contains(text(),' File a Claim ')]");
    private static final Title dashboardHeading = Title.xpath("//ng-transclude[text()='Dashboard']");
    private static final Label assignedCodes = Label.xpath("//gw-portal-nav//li[@class='gw-horizontal-menu-item gw-pull-right']/a");
    protected static final Label portAccInUITxt = Label.xpath("//*[@id='page-inner']//div[2]//div[2]//section//section[1]/ng-form/div[3]/div[2]/div/p");
    private static final Form policySummary = Form.xpath("//div[@psa-policy-quote-summary]");
    protected static final Image runningLoader = Image.xpath("//section[@class='gw-container-form']/ancestor::div[contains(@class,'gw-loader-done')]");
    protected static final Button proceedButton = Button.xpath("//button[contains(text(),'Proceed')]");
    protected static final Button okButton = Button.xpath("//button[contains(text(),'OK')]");
    protected static String warrantyEndDate;

    public SubmissionPage atSubmissionPage() {
        if (submissionPage == null) {
            submissionPage = new SubmissionPage();
        }
        return submissionPage;
    }

    public FileClaimPage atFileClaimPage() {
        if (fileClaimPage == null) {
            fileClaimPage = new FileClaimPage();
        }
        return fileClaimPage;
    }

    public PoliciesPage atPoliciesPage() {
        if (policiesPage == null) {
            policiesPage = new PoliciesPage();
        }
        waitForElementVisible(policySummary);
        return policiesPage;
    }

    public void navigateToSubmissionPage() {
        click(newSubmissionButton);
        logger.info("User is navigated to Submission page");
        addStepInfoToReport("User is navigated to Submission page");
    }

    private void navigateToPoliciesPage() {
        if (policiesPage == null) {
            policiesPage = new PoliciesPage();
        }

        logger.info("User is navigated to Policies page");
        addStepInfoToReport("User is navigated to Policies page");
    }

    public void navigateToClaimPage() {
        click(fileClaimButton);
        logger.info("User is navigated to Claim page");
        addStepInfoToReport("User is navigated to Claim page");
    }

    public void returnHome() {
        logger.info("Return to Home page");
        click(homeButton);
        waitForElementVisible(dashboardHeading);
        addStepInfoToReport("Returned to Portal Home page");
    }

    public void searchPolicyNumber() {
        String expectedNum = dataObject.getPolicyNumber();
        logger.info("Searching policy number " + expectedNum + " in Portal");
        clearText(searchTxt);
        sendText(searchTxt, expectedNum);
        click(paSearchButton);
        addStepInfoToReport("Search policy number " + expectedNum + " in Portal");
    }

    public boolean isPolicyNumberFound() {
        String expectedNum = dataObject.getPolicyNumber();
        String policyNumberResult = getText(policyInUI);
        String actualNum = policyNumberResult.replaceAll("\\D", "");
        return expectedNum.equalsIgnoreCase(actualNum);
    }

    public void clickPolicySearchResult() {
        click(policyInUI);
        navigateToPoliciesPage();
    }

    public void searchPCAccount() {
        String expectedAccountNo = (contactType.equals(Personal)) ?
                dataObject.getPortal().getPersonPTAccount() :
                dataObject.getPortal().getCommPTAccount();
        logger.info("Searching for " + contactType + " PC account " + expectedAccountNo + " in Portal");
        clearText(searchTxt);
        sendText(searchTxt, expectedAccountNo);
        click(paSearchButton);
        addStepInfoToReport("Search for Policy Center account " + expectedAccountNo + " in Portal");
    }

    public boolean isPCAccountFound() {
        String expectedAccountNo = (contactType.equals(Personal)) ?
                dataObject.getPortal().getPersonPTAccount() :
                dataObject.getPortal().getCommPTAccount();
        String accountInUI = getText(accountInUITxt);
        String accountInUIValue = accountInUI.replaceAll("\\D", "");
        return expectedAccountNo.equalsIgnoreCase(accountInUIValue);
    }

    public void portalMenuClick(String menuItem) {
        String menuPath = "//*[@id='existing-account-search']//div//div[1]//div[1]//label[contains(@for,'" + menuItem + "')]";
        clickMenu(Button.xpath(menuPath));
    }

    public void changeLanguage(String language) {
        logger.info("Change the language to " + language);
        try {
            Dropdown userDropDown = Dropdown.xpath("//span[@class='gw-pl-dropdown-toggle']/span[contains(text(),'" + userName + "')]/../..");
            waitForElementVisible(userDropDown);
            click(userDropDown);
            waitForAttributeContains(userDropDown, "class", "gw-open");
            click(preferencesButton);
            waitForAttributeNotContains(userDropDown, "class", "gw-open");
            waitForUrlContains("preferences");

            Dropdown langDropdown = Dropdown.xpath("//div[@class='gw-control-group']/div/div");
            click(langDropdown);
            waitForAttributeContains(langDropdown, "class", "gw-open");
            Link languageOption;
            if (language.equals(ENGLISH)) {
                languageOption = Link.xpath("//span[contains(text(),'" + language + "')]/..");
            } else {
                languageOption = Link.xpath("(//span[contains(text(),'" + language + "')]/..)[2]");
            }
            click(languageOption);
            waitForAttributeNotContains(langDropdown, "class", "gw-open");
            addStepInfoToReport("Changed language in Portal to " + language);
        } catch (Exception e) {
            e.printStackTrace();
            String imagePath = screenshotDirectory + File.separator + "changPortalLanguageIssue.png";
            logger.error("Can't change language in Portal, please check the screenshot at " + imagePath + " for more debug information");
            TestNGResultHandling.takeScreenShot(imagePath);
            throw new PortalLanguageSettingException("Can't change language in Portal");
        }
    }

    public void checkIfProducerCodeIsAssigned() {
        waitForElementVisible(assignedCodes);
        String codes = getText(assignedCodes);
        if (!codes.contains(dataObject.getProducerCode())) {
            throw new NonExistProducerCodeException("Producer code " + dataObject.getProducerCode() + " is not assigned to this Portal account");
        }
    }

    public void cancelSubmission() {
        if (submissionPage == null) {
            submissionPage = new SubmissionPage();
        }
        submissionPage.cancel();
        submissionPage.closePortalErrorPopup();
    }

    public boolean isWarrantyEndDateAFutureDate() {
        return DateHelper.isFutureDate(warrantyEndDate);
    }
}
