package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Link;
import ucw.elements.TextBox;
import ucw.elements.Title;

public class SearchPage extends ClaimCenter {
    private static final Logger logger = LogManager.getLogger(SearchPage.class);
    private static final TextBox claimNoTxt = TextBox.xpath("//input[@id='TabBar:ClaimTab:ClaimTab_FindClaim-inputEl']");
    private static final Title claimSummaryScreen = Title.id("ClaimSummary:ClaimSummaryScreen:ttlBar");
    private static final Link searchClaimNo = Link.xpath("//*[@id='TabBar:ClaimTab:ClaimTab_FindClaim_Button']");

    public void searchClaim() {
        logger.info("Search claim " + dataObject.getClaimCenter().getClaimNo() + " in Claim Center");
        clickMenu("Claim");
        logger.info("Clicked on Claim Menu");
        sendTextOnly(claimNoTxt, dataObject.getClaimCenter().getClaimNo());
        click(searchClaimNo);
        waitForElementVisible(claimSummaryScreen);
        logger.info("Claim summary screen is displayed");
        addStepInfoToReport("Searched and found claim number " + dataObject.getClaimCenter().getClaimNo() + " in Claim Center");
    }
}
