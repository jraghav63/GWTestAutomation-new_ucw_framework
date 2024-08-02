package ucw.pages.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Button;
import ucw.elements.TextBox;
import ucw.exceptions.PortalLoginException;
import ucw.pages.BasePage;
import ucw.utils.TestNGResultHandling;

import java.io.File;
import java.util.Arrays;


public class PortalLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(PortalLoginPage.class);
    public static String userName = "CAPPORTAL01"; // Default username
    private static final TextBox usernameField = TextBox.xpath("//input[@type='text']");
    private static final TextBox passwordField = TextBox.xpath("//input[@type='password']");
    private static final Button loginButton = Button.xpath("//button[contains(@class,'Login')]");

    public Portal login() {
        String user;
        try {
            if (ssoAuthentication) {
                user = config.getSsoAccount();
                userName = user.split("/")[0];
                loginSSO(user);
            } else {
                user = config.getPortalAccount();
                userName = user.split("/")[0];
                sendText(usernameField, userName);
                sendText(passwordField, user.split("/")[1]);
                click(loginButton);
            }
            logger.info("Logged in to the Portal with user " + user);
            addStepInfoToReport("Setup - Logged in to Portal with user " + user);
        } catch (Exception e) {
            String imagePath = screenshotDirectory + File.separator + "portalLoginIssue.png";
            logger.error("Can't login to Portal, please check the screenshot at " + imagePath + " for more debug information");
            TestNGResultHandling.takeScreenShot(imagePath);
            throw new PortalLoginException("Can't login to Portal");
        }
        return new Portal();
    }
}
