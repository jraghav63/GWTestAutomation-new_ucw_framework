package ucw.pages.billingcenter;

import ucw.elements.Button;
import ucw.elements.TextBox;
import ucw.exceptions.BillingCenterLoginException;
import ucw.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.utils.TestNGResultHandling;

import java.io.File;

public class BCLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(BCLoginPage.class);
    private static final TextBox usernameField = TextBox.id("Login:LoginScreen:LoginDV:username-inputEl");
    private static final TextBox passwordField = TextBox.id("Login:LoginScreen:LoginDV:password-inputEl");
    private static final Button loginButton = Button.id("Login:LoginScreen:LoginDV:submit-btnInnerEl");

    public BillingCenter login() {
        String user;
        try {
            if (ssoAuthentication) {
                user = config.getSsoAccount();
                loginSSO(user);
            } else {
                user = config.getBcAccount();
                sendText(usernameField, user.split("/")[0]);
                sendText(passwordField, user.split("/")[1]);
                click(loginButton);
            }
            logger.info("Logged in to the Billing Center with user " + user);
            addStepInfoToReport("Setup - Logged in to the Billing Center");
        } catch (Exception e) {
            String imagePath = screenshotDirectory + File.separator + "bcLoginIssue.png";
            logger.error("Can't login to Billing Center, please check the screenshot at " + imagePath + " for more debug information");
            TestNGResultHandling.takeScreenShot(imagePath);
            throw new BillingCenterLoginException("Can't login to Billing Center");
        }
        return new BillingCenter();
    }
}
