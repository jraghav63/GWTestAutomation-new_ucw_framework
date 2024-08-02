package ucw.pages.policycenter;

import ucw.elements.Button;
import ucw.elements.Table;
import ucw.elements.TextBox;
import ucw.exceptions.PolicyCenterLoginException;
import ucw.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.utils.TestNGResultHandling;
import java.io.File;

public class PCLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(PCLoginPage.class);
    private static final TextBox usernameField = TextBox.id("Login:LoginScreen:LoginDV:username-inputEl");
    private static final TextBox passwordField = TextBox.id("Login:LoginScreen:LoginDV:password-inputEl");
    private static final Button loginButton = Button.id("Login:LoginScreen:LoginDV:submit-btnInnerEl");
    private static final Button actionButton = Button.id("Desktop:Desktop_PSAMenuActions-btnEl");
    private static final Table myActivitiesTable = Table.id("DesktopActivities-table");

    public PolicyCenter login() {
        String user;
        try {
            if (ssoAuthentication) {
                user = config.getSsoAccount();
                loginSSO(user);
            } else {
                user = config.getPcAccount();
                sendText(usernameField, user.split("/")[0]);
                sendText(passwordField, user.split("/")[1]);
                click(loginButton);
                waitForElementVisible(actionButton);
                waitForElementVisible(myActivitiesTable);
                waitForPageLoadComplete(500);
            }
            logger.info("Logged in to the Policy Center with user " + user);
            addStepInfoToReport("Setup - Login to Policy Center");
        } catch (Exception e) {
            String imagePath = screenshotDirectory + File.separator + "pcLoginIssue.png";
            logger.error("Can't login to Policy Center, please check the screenshot at " + imagePath + " for more debug information");
            TestNGResultHandling.takeScreenShot(imagePath);
            throw new PolicyCenterLoginException("Can't login to Policy Center");
        }
        return new PolicyCenter();
    }
}
