package ucw.pages.github;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Button;
import ucw.elements.TextBox;
import ucw.models.config.GithubEnvironment;
import ucw.pages.BasePage;

public class GithubLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(GithubLoginPage.class);
    private static final Button signInButton = Button.partialLinkText("Sign in");
    private static final TextBox userNameTxt = TextBox.id("login_field");
    private static final TextBox passwordTxt = TextBox.id("password");
    private static final Button loginButton = Button.name("commit");

    public void login(GithubEnvironment githubEnvironment) {
        logger.info("Login Github");
        click(signInButton);
        waitForElementVisible(userNameTxt);
        sendText(userNameTxt, githubEnvironment.getUsername());
        sendText(passwordTxt, githubEnvironment.getPassword());
        click(loginButton);
    }
}
