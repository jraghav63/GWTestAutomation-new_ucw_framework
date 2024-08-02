package ucw.pages.claimcenter;

import ucw.elements.Button;
import ucw.elements.TextBox;
import ucw.exceptions.ClaimCenterLoginException;
import ucw.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.utils.TestNGResultHandling;

import java.io.File;

public class CCLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CCLoginPage.class);
    private static final TextBox usernameField = TextBox.id("Login:LoginScreen:LoginDV:username-inputEl");
    private static final TextBox passwordField = TextBox.id("Login:LoginScreen:LoginDV:password-inputEl");
    private static final Button loginButton = Button.id("Login:LoginScreen:LoginDV:submit-btnInnerEl");

    public ClaimCenter login() {
        String user;
        try {
            if (ssoAuthentication) {
                user = config.getSsoAccount();
                loginSSO(user);
            } else {
                user = config.getCcAccount();
                sendText(usernameField, user.split("/")[0]);
                sendText(passwordField, user.split("/")[1]);
                click(loginButton);
            }
            logger.info("Logged in to the Claim Center with user " + user);
            addStepInfoToReport("Setup - Logged in to the Claim Center");
        } catch (Exception e) {
            String imagePath = screenshotDirectory + File.separator + "ccLoginIssue.png";
            logger.error("Can't login to Claim Center, please check the screenshot at " + imagePath + " for more debug information");
            TestNGResultHandling.takeScreenShot(imagePath);
            throw new ClaimCenterLoginException("Can't login to Claim Center");
        }
        return new ClaimCenter();
    }
}
