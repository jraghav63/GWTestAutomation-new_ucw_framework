package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.*;

public class DesktopPage extends BillingCenter {
    private static final Logger logger = LogManager.getLogger(DesktopPage.class);
    private static final Button desktopMenuActionButton = Button.id("DesktopGroup:DesktopMenuActions-btnInnerEl");
    private static final Link paymentTxt = Link.xpath("//span[@id='DesktopGroup:DesktopMenuActions:DesktopMenuActions_NewPayment-textEl']");
    private static final Link suspensePaymentTxt = Link.xpath("//span[@id='DesktopGroup:DesktopMenuActions:DesktopMenuActions_NewPayment:DesktopMenuActions_NewSuspensePayment-textEl']");
    private static final Form suspensePaymentScreen = Form.id("NewSuspensePayment:NewSuspensePaymentScreen:0");
    private static final Label suspensePaymentCurrency = Label.id("NewSuspensePayment:NewSuspensePaymentScreen:NewSuspensePaymentDV:Currency-inputEl");
    private static final Link periodSessionItemTxt = Link.xpath("//span[@id='TabBar:PoliciesTab:0:PolicyPeriodSessionItemId-textEl']");
    protected static final Title polSummaryTxt = Title.id("PolicySummary:PolicySummaryScreen:ttlBar");
    private static final Label polSummaryCurrency = Label.id("PolicySummary:PolicySummaryScreen:Currency-inputEl");
    private static final Button accountNumberButton = Button.xpath("//span[@id='PolicyGroup:PolicyInfoBar:AccountNumber-btnInnerEl']");
    private static final Label accountSumCurrency = Label.id("AccountSummary:AccountSummaryScreen:Currency-inputEl");
    private static final Link accountDetail = Link.xpath("//td[@id='AccountGroup:MenuLinks:AccountGroup_AccountOverview:AccountOverview_AccountDetailSummary']//span");
    private static final Title accountDetailSumScreen = Title.id("AccountDetailSummary:AccountDetailSummaryScreen:ttlBar");
    private static final Label accountDetailCurrency = Label.id("AccountDetailSummary:AccountDetailSummaryScreen:AccountDetailDV:Currency-inputEl");
    private static final Title accountSumTxt = Title.id("AccountSummary:AccountSummaryScreen:ttlBar");

    public boolean validateCurrency() {
        logger.info("Validate currency localization of Billing Center");
        boolean result = true;
        String eurCurrency = "EUR";
        click(desktopMenuActionButton);
        click(paymentTxt);
        click(suspensePaymentTxt);
        waitForElementVisible(suspensePaymentScreen);
        String suspenseCurrency = getText(suspensePaymentCurrency);
        result &= suspenseCurrency.contains(eurCurrency);
        if (result) {
            logger.info(eurCurrency + " is the default currency in New suspense payment screen");
            addStepInfoToReport(eurCurrency + " is the default currency in New suspense payment screen");
        } else {
            logger.info("Actual currency in Account summary screen is " + suspenseCurrency);
            addStepInfoToReport("Actual currency in Account summary screen is " + suspenseCurrency);
        }
        clickMenu(policiesTab);
        click(periodSessionItemTxt);
        waitForElementVisible(polSummaryTxt);
        String policySummaryCurrency = getText(polSummaryCurrency);
        result &= policySummaryCurrency.contains(eurCurrency);
        if (result) {
            logger.info(eurCurrency + " is the default currency in Policy summary screen");
            addStepInfoToReport(eurCurrency + " is the default currency in Policy summary screen");
        } else {
            logger.info("Actual currency in Account summary screen is " + policySummaryCurrency);
            addStepInfoToReport("Actual currency in Account summary screen is " + policySummaryCurrency);
        }
        click(accountNumberButton);
        waitForElementVisible(accountSumTxt);
        String accountSummaryCurrency = getText(accountSumCurrency);
        addStepInfoToReport("Actual currency in Account summary screen is " + accountSummaryCurrency);
        result &= accountSummaryCurrency.contains(eurCurrency);
        click(accountDetail);
        waitForElementVisible(accountDetailSumScreen);
        String accDetailCurrency = getText(accountDetailCurrency);
        result &= accDetailCurrency.contains(eurCurrency);
        if (result) {
            logger.info(eurCurrency + " is default in Account summary screen");
            addStepInfoToReport(eurCurrency + " is default in Account summary screen");
        } else {
            logger.info("Actual currency in Account summary screen is " + accDetailCurrency);
            addStepInfoToReport("Actual currency in Account summary screen is " + accDetailCurrency);
        }
        return result;
    }
}
