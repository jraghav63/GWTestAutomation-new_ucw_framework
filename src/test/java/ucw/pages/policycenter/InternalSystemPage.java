package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import ucw.elements.*;
import ucw.enums.ClockTypes;
import ucw.utils.DateHelper;
import java.time.Year;

import static ucw.enums.Environments.DEV;

public class InternalSystemPage extends PolicyCenter {
    private static final Logger logger = LogManager.getLogger(InternalSystemPage.class);
    private static final Title batchProcessInfoLbl = Title.id("BatchProcessInfo:BatchProcessScreen:ttlBar");
    private static final Tab internalToolsTab = Tab.id("InternalToolsTabBar:UnsupportedToolsTab-btnInnerEl");
    private static final Link systemClockLeftItem = Link.xpath("//td[@id='UnsupportedTools:MenuLinks:UnsupportedTools_SystemClock']//span");
    private static final Button changeDateButton = Button.id("SystemClock:SystemClockScreen:ChangeDate-btnInnerEl");
    private static final TextBox clockInput = TextBox.xpath("//input[@id='SystemClock:SystemClockScreen:Date-inputEl']");
    private static final Label timeLabel = Label.xpath("//label[@id='SystemClock:SystemClockScreen:0']");
    private static final Button actionsButton = Button.id("UnsupportedTools:InternalToolsMenuActions-btnInnerEl");
    private static final Button returnAppButton = Button.id("UnsupportedTools:InternalToolsMenuActions:ReturnToApp-textEl");
    private static final Label errorMsg = Label.xpath("//div[@id='SystemClock:SystemClockScreen:_msgs']/div");

    public void changeClock(ClockTypes clockType) {
        if (getCurrentEnvironment().equals(DEV)) {
            pressAltShiftT();
            changeSystemClock(clockType);
            returnToPolicyCenter();
            waitForElementVisible(desktopTab);
        } else {
            logger.info("Running on " + getCurrentEnvironment() + " environment so won't need to change clock");
        }
    }

    private void changeSystemClock(ClockTypes clockType) {
        logger.info("Change system clock to " + clockType + " time");
        openSystemClock();
        waitForAttributeToBeNotEmpty(clockInput, "value");
        String currentSystemClock = getAttributeValue(clockInput, "value");
        logger.info("Current system clock is " + currentSystemClock);
        switch (clockType) {
            case FUTURE -> systemFutureYear = getConfig().getFutureYear();
            case PRESENT -> systemFutureYear = Year.now().getValue();
        }
        String targetYear = String.valueOf(systemFutureYear);
        String currentTime = DateHelper.getCurrentDateWithFormatter().replace(String.valueOf(Year.now().getValue()), targetYear);
        String finalTimeValue;
        int maxRetries = 10;
        for (int i = 1; i <= maxRetries; i++) {
            try {
                clearText(clockInput);
                finalTimeValue = currentTime + currentSystemClock.substring(10);
                sendText(clockInput, finalTimeValue);
                click(changeDateButton);
                waitForTextContains(timeLabel, targetYear);
                addStepInfoToReport("Changed the system clock successfully to " + finalTimeValue);
                break;
            } catch (TimeoutException | NoSuchElementException e) {
                if (isElementDisplayed(errorMsg, 2) && getText(errorMsg).contains("Cannot set time backwards")) {
                    currentSystemClock = DateHelper.addHourToSystemClock(currentSystemClock, 1);
                }
            }
        }
    }

    private void openSystemClock() {
        waitForElementVisible(batchProcessInfoLbl);
        waitForTextNotNull(batchProcessInfoLbl);
        click(internalToolsTab);
        click(systemClockLeftItem);
        waitForElementVisible(clockInput);
        logger.info("Opened System clock");
        addStepInfoToReport("Opened System clock");
    }

    private void returnToPolicyCenter() {
        click(actionsButton);
        click(returnAppButton);
        addStepInfoToReport("Return to Policy Center");
    }
}
