package ucw.pages.jenkins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import ucw.elements.Button;
import ucw.elements.TextBox;
import ucw.models.config.JenkinsEnvironment;
import ucw.pages.BasePage;

public class JenkinsLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(JenkinsLoginPage.class);
    private static final TextBox userNameTxt = TextBox.id("j_username");
    private static final TextBox passwordTxt = TextBox.name("j_password");
    private static final Button loginButton = Button.name("Submit");

    public void login(JenkinsEnvironment jenkinsEnvironment) {
        logger.info("Login Jenkins");
        sendText(userNameTxt, jenkinsEnvironment.getUsername());
        sendText(passwordTxt, jenkinsEnvironment.getPassword());
        click(loginButton);
    }
}
