package ucw.pages.billingcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Form;
import ucw.elements.Label;
import ucw.elements.Link;
import ucw.elements.Title;

public class AdministrationPage extends BillingCenter {
    private static final Logger logger = LogManager.getLogger(AdministrationPage.class);
    private static final Link securityZones = Link.xpath("//td[@id='Admin:MenuLinks:Admin_UsersAndSecurity:UsersAndSecurity_SecurityZones']//span");
    private static final Form securityZonesScreen = Form.id("SecurityZones:SecurityZonesScreen:0");
    private static final Link zoneItaly = Link.xpath("//a[text()='Security Zone Italy']");
    private static final Label zoneDetailItaly = Label.xpath("//div[@id='SecurityZoneDetail:SecurityZoneDetailScreen:SecurityZoneDetailDV:Name-bodyEl']//div[text()='Security Zone Italy']");
    private static final Link businessSettingLink = Link.xpath("//*[@id='Admin:MenuLinks:Admin_BusinessSettings']/div");
    private static final Link delinquencyPlanLink = Link.xpath("//*[@id='Admin:MenuLinks:Admin_BusinessSettings:BusinessSettings_DelinquencyPlans']");
    private static final Link delinquencyItaly = Link.id("DelinquencyPlans:DelinquencyPlansScreen:DelinquencyPlansLV:9:Name");
    private static final Title delinquencyDetailScreen = Title.id("DelinquencyPlanDetail:DelinquencyPlanDetailScreen:ttlBar");

    public void goToSecurityZones() {
        logger.info("Go to Security Zones");
        click(securityZones);
        waitForElementVisible(securityZonesScreen);
    }

    public boolean validateSecurityZones() {
        assert isElementDisplayed(zoneItaly);
        click(zoneItaly);
        waitForElementVisible(zoneDetailItaly);
        addStepInfoToReport("Check if zone detail is display");
        return isElementDisplayed(zoneDetailItaly);
    }

    public boolean checkDelinquencyPlanForItaly() {
        click(businessSettingLink);
        click(delinquencyPlanLink);
        waitForElementVisible(delinquencyItaly);
        String delinquencyPlanValue = getText(delinquencyItaly);
        logger.info("New Delinquency plan for Italy is " + delinquencyPlanValue);
        click(delinquencyItaly);
        waitForElementVisible(delinquencyDetailScreen);
        addStepInfoToReport("Check if delinquency details screen display");
        return isElementDisplayed(delinquencyDetailScreen);
    }
}
