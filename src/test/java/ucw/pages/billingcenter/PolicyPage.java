package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import ucw.elements.*;

public class PolicyPage extends BillingCenter {
    private static final Logger logger = LogManager.getLogger(PolicyPage.class);
    private static final Button policyMenu = Button.xpath("//*[@id='TabBar:PoliciesTab-btnInnerEl']");
    private static final TextBox policySearchBox = TextBox.xpath("//*[@id='Policies:PolicySearchScreen:PolicySearchDV:PolicyNumberCriterion-inputEl']");
    private static final Button policySearchButton = Button.xpath("//*[@id='Policies:PolicySearchScreen:PolicySearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final Link searchResult = Link.xpath("//*[@id='Policies:PolicySearchScreen:PolicySearchResultsLV:0:PolicyNumber']");
    private static final Label managementTypeLbl = Label.xpath("//*[@id='PolicyDetailSummary:PolicyDetailSummaryScreen:PolicyDetailDV:ManagementType-inputEl']");
    private static final Link policyDetails = Link.xpath("//*[@id='PolicyGroup:MenuLinks:PolicyGroup_PolicyOverview:PolicyOverview_PolicyDetailSummary']/div/span");
    private static final Title polSummaryTxt = Title.id("PolicySummary:PolicySummaryScreen:ttlBar");
    private static final Table polSumTable = Table.id("PolicySummary-table");

    public boolean searchPolicy(String policyNumber) {
        logger.info("Searching for policy " + policyNumber + " in Billing Center");
        boolean isFound = true;
        click(policyMenu);
        clearText(policySearchBox);
        sendText(policySearchBox, policyNumber);
        int retry = 5;
        while (retry > 0) {
            try {
                click(policySearchButton);
                click(searchResult);
                waitForElementVisible(polSummaryTxt);
                waitForElementVisible(polSumTable);
                isFound = isElementDisplayed(polSumTable);
                logger.info("Policy " + policyNumber + " is found in Billing Center & clicked on Policy");
                addStepInfoToReport("Searched policy number " + policyNumber + " in Billing Center");
                break;
            } catch (NoSuchElementException e) {
                retry = retry - 1;
                if (retry > 0) {
                    waitForPageLoadComplete(3000); //BC results could be pending due to queue, retry
                } else {
                    isFound = false;
                    logger.error("Policy " + policyNumber + " is not found in Billing Center");
                }
            }
        }
        return isFound;
    }

    public String getManagementType() {
        click(policyDetails);
        String mgmtType = getText(managementTypeLbl);
        addStepInfoToReport("Get current management type is " + mgmtType);
        return mgmtType;
    }
}
