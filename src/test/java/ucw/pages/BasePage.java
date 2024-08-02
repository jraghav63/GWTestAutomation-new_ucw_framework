package ucw.pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ucw.elements.*;
import ucw.enums.*;
import ucw.models.config.CenterUrl;
import ucw.models.config.Config;
import ucw.models.config.GithubEnvironment;
import ucw.models.config.JenkinsEnvironment;
import ucw.models.testdata.CentersDataObject;
import ucw.pages.billingcenter.BCLoginPage;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.claimcenter.CCLoginPage;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.contactmanager.CMLoginPage;
import ucw.pages.contactmanager.ContactManager;
import ucw.pages.github.GithubLoginPage;
import ucw.pages.github.GithubMainPage;
import ucw.pages.jenkins.JenkinsLoginPage;
import ucw.pages.jenkins.JenkinsMainPage;
import ucw.pages.policycenter.PCLoginPage;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.pages.portal.PortalLoginPage;
import ucw.utils.BrowserFactory;
import ucw.utils.TestDataGenerator;
import ucw.utils.TestNGResultHandling;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.Keys.*;
import static org.openqa.selenium.WindowType.TAB;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static ucw.enums.AccountType.Person;
import static ucw.enums.Environments.DEV;
import static ucw.enums.Labels.*;
import static ucw.enums.Languages.ENGLISH;
import static ucw.enums.Makes.*;
import static ucw.enums.ProductNames.*;
import static ucw.utils.extentreports.ExtentTestManager.getTestClass;
import static ucw.utils.extentreports.ExtentTestManager.getTestMethod;

public class BasePage {
    private static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    private static String methodName;
    public static final String screenshotDirectory = System.getProperty("user.dir") + File.separator + "build" + File.separator + "generated";
    private PCLoginPage pcLoginPage;
    private BCLoginPage bcLoginPage;
    private CCLoginPage ccLoginPage;
    private CMLoginPage cmLoginPage;
    public static WebDriver driver;
    public static Config config;
    public static CenterUrl centerUrl;
    public static Actions actions;
    public static WebDriverWait wait;
    public static JavascriptExecutor jsExecutor;
    private static String pcTab, ccTab, cmTab, bcTab, portalTab, jenkinsTab, githubTab;
    public static CentersDataObject dataObject;
    public static Select select;
    public static JenkinsEnvironment jenkinsEnvironment;
    public static GithubEnvironment githubEnvironment;
    public static final Faker faker = new Faker();
    public static final Random rand = new Random();
    public static int systemFutureYear, maxPower;
    private static final Button alertOKButton = Button.xpath("//span[text()='OK']");
    private static final TextBox mainSearchTxt = TextBox.xpath("//*[@id='QuickJump-inputWrap']//input");
    protected static final Button adminTabButton = Button.xpath("//*[@id='TabBar:AdminTab-btnInnerEl']");
    protected static final Button tagsJV = Button.xpath("//li[contains(text(),'JV')]");
    private static final Button logoutButton = Button.id(":TabLinkMenuButton-btnIconEl");
    private static final Button logoutIconButton = Button.id(":TabLinkMenuButton-btnIconEl");
    private static final Button logoutDdBtn = Button.xpath("//span[@id='TabBar:LogoutTabBarLink-textEl']");
    private static final Button internationDdBtn = Button.xpath("//span[@id='TabBar:LanguageTabBarLink-textEl']");
    private static final Button langDdBtn = Button.xpath("//span[@id='TabBar:LanguageTabBarLink:languageSwitcher-textEl']");
    protected static final Items menuItemPath = Items.xpath("//div[@id=':tabs-targetEl']/a/span/span/span[2]");
    private static final Label errorPopup = Label.xpath("//div[text()='HTTP request failed: 504' or text()='HTTP request failed: 404']");
    private static final TextBox ssoUsernameTextbox = TextBox.id("username");
    private static final TextBox ssoPasswordTextbox = TextBox.id("password");
    private static final Button ssoSubmitButton = Button.xpath("//a[normalize-space()='Submit']");
    private static Map<String, String[]> typesCountry;
    private static String[] labels;
    private static String[] types;
    protected static final Makes[] makesHasModelSelector = {ALFAROMEO, LANCIA};
    public static boolean ssoAuthentication;
    protected static AccountType accountType;
    protected static ContactType contactType;

    public void initialize() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(NON_NULL);
        config = objectMapper.readValue(new File("config.json"), Config.class);
        if ((System.getProperty("env") != null) && !(System.getProperty("env").isEmpty())) {
            config.setEnvironment(Environments.valueOf(System.getProperty("env")));
        }
        if ((System.getProperty("timeout") != null) && !(System.getProperty("timeout").isEmpty())) {
            config.setTimeout(Integer.parseInt(System.getProperty("timeout")));
        }
        if ((System.getProperty("selfHeal") != null) && !(System.getProperty("selfHeal").isEmpty())) {
            config.setSelfHealing(Boolean.parseBoolean(System.getProperty("selfHeal")));
        }
        logger.info("Start running test on " + getCurrentEnvironment() + " environment");
        centerUrl = config.getCenterUrls()
                .stream().filter(env -> env.getEnv().equals(config.getEnvironment()))
                .findFirst().orElse(null);
        if (centerUrl == null) {
            throw new IllegalArgumentException("Invalid environment argument: " + config.getEnvironment());
        }
        jenkinsEnvironment = config.getJenkins();
        githubEnvironment = config.getGithub();
        int currentYear = Year.now().getValue();
        systemFutureYear = (config.getEnvironment().equals(DEV)) ? config.getFutureYear() : currentYear;
        ssoAuthentication = !getCurrentEnvironment().equals(DEV);
    }

    public void openBrowser() {
        driver = BrowserFactory.getDriver(config);
        wait = BrowserFactory.getDriverWait(config, driver);
        jsExecutor = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public String getText(By byElement) {
        waitForElementVisible(byElement);
        return driver.findElement(byElement).getText();
    }

    public String getText(WebElement elem) {
        return elem.getText();
    }

    public void clearText(By byElement) {
        driver.findElement(byElement).sendKeys(Keys.chord(CONTROL, "a", DELETE));
    }

    public String getAttributeValue(By byElement, String attr) {
        int retries = 3;
        String result = null;
        while (retries > 0) {
            try {
                result = driver.findElement(byElement).getAttribute(attr);
                break;
            } catch (StaleElementReferenceException e) {
                retries = retries - 1;
            }
        }
        return result;
    }

    public PCLoginPage goToPolicyCenter() {
        driver.get(centerUrl.getPolicyCenterUrl());
        addStepInfoToReport("Setup - Go to Policy Center");
        return new PCLoginPage();
    }

    public BCLoginPage goToBillingCenter() {
        driver.get(centerUrl.getBillingCenterUrl());
        addStepInfoToReport("Setup - Go to Billing Center");
        return new BCLoginPage();
    }

    public CCLoginPage goToClaimCenter() {
        driver.get(centerUrl.getClaimCenterUrl());
        addStepInfoToReport("Setup - Go to Claim Center");
        return new CCLoginPage();
    }

    public CMLoginPage goToContactManager() {
        driver.get(centerUrl.getContactManagerUrl());
        addStepInfoToReport("Setup - Go to Contact Manager");
        return new CMLoginPage();
    }

    public PortalLoginPage goToPortalPage() {
        driver.get(centerUrl.getPortalUrl());
        addStepInfoToReport("Setup - Go to Portal");
        return new PortalLoginPage();
    }

    public GithubLoginPage goToGithubPage() {
        driver.get(githubEnvironment.getUrl());
        return new GithubLoginPage();
    }

    public JenkinsLoginPage goToJenkinsPage() {
        driver.get(jenkinsEnvironment.getUrl());
        return new JenkinsLoginPage();
    }

    public SoapAPIBuilder initSoapApi() {
        return new SoapAPIBuilder();
    }

    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    public Config getConfig() {
        return config;
    }

    public Environments getCurrentEnvironment() {
        return config.getEnvironment();
    }

    public Countries getCurrentCountry() {
        return dataObject.getCountry();
    }

    public void loginSSO(String user) {
        if (driver.getWindowHandles().size() == 1) {
            sendText(ssoUsernameTextbox, user.split("/")[0]);
            sendText(ssoPasswordTextbox, user.split("/")[1]);
            sendKeysByAction(ENTER);
        }
        addStepInfoToReport("Setup - Login by SSO with user " + user);
    }

    public PolicyCenter loginPolicyCenter() {
        logger.info("Go to Policy Center");
        openNewTab();
        pcTab = driver.getWindowHandle();
        pcLoginPage = goToPolicyCenter();
        return pcLoginPage.login();
    }

    public BillingCenter loginBillingCenter() {
        logger.info("Go to Billing Center");
        openNewTab();
        bcTab = driver.getWindowHandle();
        bcLoginPage = goToBillingCenter();
        return bcLoginPage.login();
    }

    public ClaimCenter loginClaimCenter() {
        logger.info("Go to Claim Center");
        openNewTab();
        ccTab = driver.getWindowHandle();
        ccLoginPage = goToClaimCenter();
        return ccLoginPage.login();
    }

    public ContactManager loginContactManager() {
        logger.info("Go to Contact Manager");
        openNewTab();
        cmTab = driver.getWindowHandle();
        cmLoginPage = goToContactManager();
        return cmLoginPage.login();
    }

    public Portal loginPortal() {
        logger.info("Go to Portal");
        openNewTab();
        portalTab = driver.getWindowHandle();
        Portal portal = goToPortalPage().login();
        portal.changeLanguage(ENGLISH);
        portal.checkIfProducerCodeIsAssigned();
        return portal;
    }

    public GithubMainPage loginGithub() {
        openNewTab();
        githubTab = driver.getWindowHandle();
        GithubLoginPage githubLoginPage = goToGithubPage();
        githubLoginPage.login(githubEnvironment);
        return new GithubMainPage();
    }

    public JenkinsMainPage loginJenkins() {
        openNewTab();
        jenkinsTab = driver.getWindowHandle();
        JenkinsLoginPage jenkinsLoginPage = goToJenkinsPage();
        jenkinsLoginPage.login(jenkinsEnvironment);
        return new JenkinsMainPage();
    }

    public void tearDown() {
        logger.debug("Teardown and quit driver");
        driver.quit();
    }

    public void openNewTab() {
        if (driver.getCurrentUrl().contains("http")) {
            driver.switchTo().newWindow(TAB);
            switchToNewTab();
        }
    }

    public void switchToNewTab() {
        Set<String> windows = driver.getWindowHandles();
        driver.switchTo().window(new ArrayList<>(windows).get(windows.size() - 1));
    }

    public void switchToPolicyCenterTab() {
        driver.switchTo().window(pcTab);
        closeErrorPopup();
    }

    public void switchToBillingCenterTab() {
        driver.switchTo().window(bcTab);
        closeErrorPopup();
    }

    public void switchToClaimCenterTab() {
        driver.switchTo().window(ccTab);
        closeErrorPopup();
    }

    public void switchToContactManagerTab() {
        driver.switchTo().window(cmTab);
        closeErrorPopup();
    }

    public void switchToPortalTab() {
        driver.switchTo().window(portalTab);
    }

    public void focusFirstInputField() {
        wait.ignoring(ElementClickInterceptedException.class).until(elementToBeClickable(mainSearchTxt)).click();
    }

    public List<WebElement> findElements(By byElement) {
        return driver.findElements(byElement);
    }

    public int getNumberOfElements(By byElement) {
        return driver.findElements(byElement).size();
    }

    public void setDropDown(By byElement, Object value) {
        logger.debug("Set dropdown with value " + value);
        waitForElementVisible(byElement);
        int maxRetries = 3;
        for (int i = 1; i <= maxRetries; i++) {
            try {
                sendTextOnly(byElement, Keys.chord(CONTROL, "a", DELETE));
                sendTextOnly(byElement, value);
                sendTextOnly(byElement, ENTER);
                clickPageBody();
                focusFirstInputField();
                waitForAttributeToBe(byElement, "value", value);
                waitForPageLoadComplete();
                break;
            } catch (TimeoutException e) {
                if (i == maxRetries) {
                    throw e;
                } else {
                    logger.debug("Retried " + (i + 1) + " time(s) for selecting option from dropdown");
                }
            }
        }
    }

    public void setDropDownRandomValue(By byElement) {
        waitForElement(byElement);
        click(byElement);
        Dropdown dropDownList = Dropdown.xpath("//div[contains(@class,'x-boundlist-floating') and not(contains(@style,'display: none'))]");
        waitForElementVisible(dropDownList);
        List<WebElement> items = driver.findElements(Items.xpath("//div[contains(@class,'x-boundlist-floating') and not(contains(@style,'display: none'))]//li"));
        int origin = 0;
        if (getText(items.get(0)).equals("<none>")) {
            origin = 1;
        }
        WebElement randomItem = items.get(rand.nextInt(origin, items.size()));
        String randomValue = getText(randomItem);
        click(randomItem);
        logger.info("Picked random value " + randomValue);
        clickPageBody();
        waitForPageLoadComplete(500);
    }

    public void setDropDown(By byElement, Object value, int listIndex) {
        logger.debug("Set dropdown with value " + value);
        waitForElementVisible(byElement);
        sendText(byElement, Keys.chord(CONTROL, "a"));
        waitForElementVisible(byElement);
        sendText(byElement, value);
        Dropdown dropDownList = Dropdown.xpath("(//li[text()='" + value + "']/../../..)[" + listIndex + "]");
        waitForAttributeNotContains(dropDownList, "style", "display: none");
        waitForElementVisible(Label.xpath("(//li[text()='" + value + "'])[" + listIndex + "]"));
        focusFirstInputField();
        waitForAttributeContains(dropDownList, "style", "display: none");
        waitForPageLoadComplete();
    }

    public void select1stValueOfDropdown(By byElement) {
        waitForElement(byElement);
        click(byElement);
        sendText(byElement, Keys.DOWN);
        sendText(byElement, Keys.ENTER);
        waitForAttributeToBeNotEmpty(byElement, "value");
        clickPageBody();
        logger.info("Selected the first value of dropdown: " + getAttributeValue(byElement, "value"));
    }

    public void clickMenu(String menuItemName) {
        WebElement menuElement = driver.findElement(Items.xpath("//div[@id=':tabs-innerCt']/div/a/span[contains(@id,'" + menuItemName + "')]"));
        actions.moveToElement(menuElement).moveByOffset(30, 0).click().build().perform();
    }

    public void clickMenu(WebElement menu) {
        actions.moveToElement(menu).moveByOffset(30, 0).click().build().perform();
    }

    public void clickMenu(By byElement) {
        actions.moveToElement(driver.findElement(byElement)).moveByOffset(30, 0).click().build().perform();
    }

    public void setVirtualDate(By byElement, String dateValue) {
        WebElement dateElement = driver.findElement(byElement);
        String date = dateValue.replaceAll("-", "/");
        int yrCalc = Integer.parseInt(date.split("/")[2]);
        if (yrCalc > 20 && yrCalc < 99) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "19" + date.split("/")[2];
        } else if (yrCalc > 1 && yrCalc <= 20) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "20" + date.split("/")[2];
        }
        dateElement.sendKeys(date);
        clickPageBody();
        focusFirstInputField();
    }

    public void setEffectiveDate(By byElement, String value) {
        WebElement dateElement = driver.findElement(byElement);
        String date = value.replaceAll("-", "/");
        int yrCalc = Integer.parseInt(date.split("/")[2]);
        if (yrCalc > 20 && yrCalc < 99) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "19" + date.split("/")[2];
        } else if (yrCalc > 1 && yrCalc <= 20) {
            date = date.split("/")[0] + "/" + date.split("/")[1] + "/" + "20" + date.split("/")[2];
        }
        dateElement.sendKeys(Keys.chord(CONTROL, "a"));
        dateElement.sendKeys(date);
        focusFirstInputField();
        clickPageBody();
        assert isElementDisplayed(byElement);
    }

    public boolean isElementDisplayed(By byElement) {
        return driver.findElement(byElement).isDisplayed();
    }

    public boolean isElementDisplayed(By byElement, int timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
        boolean result = driver.findElement(byElement).isDisplayed();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getTimeout()));
        return result;
    }

    public void jsClick(By byElement) {
        WebElement elem = driver.findElement(byElement);
        jsExecutor.executeScript("arguments[0].click();", elem);
    }

    public void hoverAndClick(By byElement) {
        WebElement elem = driver.findElement(byElement);
        actions.moveToElement(elem).click(elem).build().perform();
        try {
            sleep(1000);
        } catch (InterruptedException ignore) {}
    }

    public void hover(By byElement) {
        WebElement elem = driver.findElement(byElement);
        actions.moveToElement(elem).build().perform();
    }

    public void clickPageBody() {
        click(Form.xpath("//body"));
        waitForAttributeNotContains(Form.tagName("body"), "class", "x-masked");
    }

    public void waitForPageLoadComplete() {
        try {
            wait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
            sleep(100);
        } catch (InterruptedException ignored) {}
    }

    public void waitForPageLoadComplete(int millis) {
        try {
            wait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
            sleep(millis);
        } catch (InterruptedException ignored) {}
    }

    public void waitForElement(By byElement) {
        int retries = 3;
        while (retries > 0) {
            try {
                driver.findElement(byElement).isDisplayed();
                sleep(500);
                break;
            } catch (StaleElementReferenceException | InterruptedException | NoSuchElementException e) {
                retries = retries - 1;
            }
        }
    }

    public void waitForElementVisible(By byElement) {
        int retries = 3;
        while (retries > 0) {
            try {
                wait.until(visibilityOfElementLocated(byElement));
                break;
            } catch (StaleElementReferenceException e) {
                // Stale element exception occurred, retry locating the element
                retries = retries - 1;
            }
        }
    }

    public void waitForElementClickable(By byElement) {
        int retries = 3;
        while (retries > 0) {
            try {
                wait.until(elementToBeClickable(byElement));
                break;
            } catch (StaleElementReferenceException e) {
                // Stale element exception occurred, retry locating the element
                retries = retries - 1;
            }
        }
    }

    public void waitForElementDisappear(By byElement, int timeoutInSeconds) {
        wait.withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .until(invisibilityOfElementLocated(byElement));
    }

    public void waitUntilSizeThan0(By byElement) {
        wait.until(driver -> !driver.findElements(byElement).isEmpty());
    }

    public void waitForElementDisappear(By byElement) {
        wait.until(invisibilityOfElementLocated(byElement));
    }

    public void waitForTextToBe(By byElement, String expectedText) {
        wait.until(textToBe(byElement, expectedText));
        logger.debug("Element has text changed as expected");
    }

    public void waitForTextContains(By byElement, String expectedText) {
        wait.until(textMatches(byElement, Pattern.compile(expectedText)));
        logger.debug("Element text contains expected text");
    }

    public void waitForTextNotToBe(By byElement, String expectedText) {
        wait.until(not(textToBe(byElement, expectedText)));
        logger.debug("Element text has value as expected");
    }

    public void waitForTextNotNull(By byElement) {
        wait.until(not(textToBe(byElement, null)));
        logger.debug("Element text has value now");
    }

    public void waitForAttributeToBe(By byElement, String attr, Object expectedText) {
        wait.until(attributeToBe(byElement, attr, String.valueOf(expectedText)));
        logger.debug("Attribute " + attr + "'s value changed as expected text " + expectedText);
    }

    public void waitForAttributeNotToBe(By byElement, String attr, String expectedText) {
        wait.until(not(attributeToBe(byElement, attr, expectedText)));
    }

    public void waitForAttributeContains(By byElement, String attr, String expectedText) {
        wait.ignoring(StaleElementReferenceException.class)
                .until(attributeContains(byElement, attr, expectedText));
    }

    public void waitForAttributeContains(WebElement element, String attr, String expectedText) {
        wait.ignoring(StaleElementReferenceException.class)
                .until(attributeContains(element, attr, expectedText));
    }

    public void waitForAttributeNotContains(By byElement, String attr, String expectedText) {
        wait.ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until(not(attributeContains(byElement, attr, expectedText)));
    }

    public void waitForAttributeToBeNotEmpty(By byElement, String attr) {
        waitForElement(byElement);
        clickPageBody();
        wait.ignoring(StaleElementReferenceException.class)
                .until(attributeToBeNotEmpty(driver.findElement(byElement), attr));
    }

    public void waitForUrlContains(String expectedText) {
        wait.until(urlContains(expectedText));
    }

    public String getPage() {
        return driver.getPageSource();
    }

    public void click(WebElement elem) {
        int retries = 3;
        while (retries > 0) {
            try {
                elem.click();
                sleep(500);
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | InterruptedException e) {
                retries = retries - 1;
            }
        }
    }

    public void click(By byElement) {
        int retries = 3;
        while (retries > 0) {
            try {
                driver.findElement(byElement).click();
                sleep(500);
                break;
            } catch (StaleElementReferenceException |
                    InterruptedException |
                    ElementNotInteractableException e) {
                retries = retries - 1;
            }
        }
    }

    public void sendText(By byElement, Keys key) {
        waitForElement(byElement);
        driver.findElement(byElement).sendKeys(key);
    }

    public void sendText(By byElement, Object text) {
        int retries = 3;
        while (retries > 0) {
            try {
                driver.findElement(byElement).sendKeys(text.toString());
                break;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                retries = retries - 1;
            }
        }
        waitForPageLoadComplete();
        clickPageBody();
    }

    public void sendText(WebElement elem, String text) {
        elem.sendKeys(text);
    }

    public void sendTextOnly(By byElement, Object text) {
        int retries = 3;
        while (retries > 0) {
            try {
                driver.findElement(byElement).sendKeys(text.toString());
                break;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                retries = retries - 1;
            }
        }
        waitForPageLoadComplete();
    }

    public void clickLinkText(String linkText) {
        waitForElement(Link.linkText(linkText));
        wait.until(ExpectedConditions.elementToBeClickable(Link.linkText(linkText)));
        driver.findElement(Link.linkText(linkText)).click();
    }

    public void closePopup() {
        click(alertOKButton);
        clickPageBody();
        waitForPageLoadComplete();
    }

    public int getTableRows(String tablePath) {
        return wait.until(driver -> driver.findElements(Table.linkText(tablePath))).size();
    }

    public void scrollDown() {
        jsExecutor.executeScript("window.scrollBy(0,500)", "");
    }

    public void scrollUp() {
        jsExecutor.executeScript("window.scrollBy(0,-500)", "");
    }

    public void setDataObject() {
        dataObject = TestDataGenerator.loadDataFromFile("center_data.json");
        dataObject.getBillingCenter().setAccountNoBilling(centerUrl.getAccountNoBilling());
        setRandomAccountType();
        setRandomContactType();
    }

    public CentersDataObject getDataObject() {
        return dataObject;
    }

    public void setMethodName(String name) {
        methodName = name;
    }

    public void logTheGeneratedData() throws JsonProcessingException {
        String output = objectMapper.writeValueAsString(dataObject);
        logger.info("Used data\n" + output);
    }

    public void exportDataToFile(String fileName) {
        File dataFile = new File(
                "build"
                + File.separator
                + "generated"
                + File.separator
                + fileName + ".json");
        logger.info("Export ran data to file " + dataFile.getAbsolutePath());
        try {
            objectMapper.writeValue(dataFile, dataObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectByText(By byElement, Object option) {
        waitForElement(byElement);
        select = new Select(driver.findElement(byElement));
        select.selectByVisibleText(option.toString());
        logger.info("Selected option " + option);
    }

    public void selectRandom(By byElement) {
        select = new Select(driver.findElement(byElement));
        int randomValue = rand.nextInt(1, select.getOptions().size());
        logger.info("Selected random option " + getText(select.getOptions().get(randomValue)));
        select.selectByIndex(randomValue);
    }

    public List<String> getAllOptions(Dropdown dropdown) {
        select = new Select(driver.findElement(dropdown));
        List<WebElement> options = select.getOptions();
        List<String> optionValues = new ArrayList<>();
        options.forEach(option -> optionValues.add(option.getText()));
        return optionValues;
    }

    public boolean dropDownValuesExist(By byElement, String[] expectedValues) {
        WebElement dropdown = driver.findElement(byElement);
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        List<String> actualValues = new ArrayList<>();
        for (WebElement option : options) {
            actualValues.add(getText(option));
        }
        actualValues.remove("Please Select");
        logger.info("Actual dropdown options: " + actualValues);
        logger.info("Expected dropdown options: " + Arrays.asList(expectedValues));
        boolean result = actualValues.equals(Arrays.asList(expectedValues));
        if (result) {
            logger.info("The drop down values are matching with expected values");
        } else {
            logger.info("The drop down values are not matching with expected values");
        }
        return result;
    }

    public void pressAltShiftT() {
        actions.keyDown(Keys.ALT).keyDown(Keys.SHIFT).sendKeys("T").keyUp(Keys.ALT)
                .keyUp(Keys.SHIFT).build().perform();
    }

    public void sendKeysByAction(Object keys) {
        actions.sendKeys(keys.toString()).build().perform();
    }

    public void sendKeysByAction(Keys key) {
        actions.sendKeys(key).build().perform();
    }
    public void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    public void logout() {
        click(logoutButton);
        click(logoutDdBtn);
        if (getPage().contains("Do you really want to log out?")) {
            closePopup();
        }
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = rand.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static <T extends Class<T>> String randomConstant(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String result = null;
        try {
            result = (String) fields[rand.nextInt(fields.length)].get(clazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setLanguage(String language) {
        logger.info("Set language to " + language);
        click(logoutIconButton);
        click(internationDdBtn);
        click(langDdBtn);
        Link langPath = Link.xpath("//span[contains(@id,'TabBar:LanguageTabBarLink:languageSwitcher:')][text()='" + language
                + "']/parent::a//parent::div");
        if (getAttributeValue(langPath, "class").contains("x-menu-item-unchecked")) {
            click(langPath);
            waitForPageLoadComplete();
        }
        clickPageBody();
        waitForPageLoadComplete(3000); //Wait for all texts are changed to new language
        addStepInfoToReport("Changed system language to " + language);
    }

    public void waitForFileDownloadComplete(File file) {
        wait.until(x -> file.exists());
        waitForPageLoadComplete(500);
    }

    public BasePage selectProducerCode(String producerCode) {
        dataObject.setProducerCode(producerCode);
        return this;
    }

    public BasePage selectPortalProducerCode(String producerCode) {
        dataObject.getPortal().setProducerCode(producerCode);
        return this;
    }

    public BasePage selectCountry(Countries country) {
        logger.info("Selected country " + country);
        dataObject.setCountry(country);
        selectVirtualProduct();
        selectProducerCode();
        return this;
    }

    private void selectVirtualProduct() {
        String productName, egvoProductName = null;
        switch (dataObject.getCountry()) {
            case France -> {
                productName = GVOFRANCE;
                egvoProductName = EGVOSAFRANCE;
            }
            case Spain -> {
                productName = GVOSPAIN;
                egvoProductName = EGVOSASPAIN;
            }
            case Italy -> {
                productName = GARANZIAITALY;
                egvoProductName = GARANZIASAITALY;
            }
            case Germany -> productName = GARANTIEGERMANY;
            default -> throw new IllegalStateException("Unexpected value: " + dataObject.getCountry());
        }
        dataObject.getPolicyCenter().setProductName(productName);
        dataObject.getPolicyCenter().setProdCodeTypeForEGVOStandalone(egvoProductName);
        dataObject.getBillingCenter().setDelinquencyPlan("DP1 for Dealers - " + dataObject.getCountry());
    }

    private void selectProducerCode() {
        switch (getCurrentEnvironment()) {
            case TRAINING -> {
                switch (getCurrentCountry()) {
                    case France -> dataObject.setProducerCode("FRTPA40");
                    case Spain -> dataObject.setProducerCode("010627N");
                    case Italy -> dataObject.setProducerCode("IT00B4S");
                    case Germany -> dataObject.setProducerCode("010627N");
                }
            }
            case STAGING -> {
                switch (getCurrentCountry()) {
                    case France -> dataObject.setProducerCode("000000F");
                    case Spain -> dataObject.setProducerCode("001012E");
                    case Italy -> dataObject.setProducerCode("IT0054H");
                    case Germany -> dataObject.setProducerCode("001817U");
                }
            }
            default -> dataObject.setProducerCode(centerUrl.getProducerCode());
        }
    }

    public BasePage selectRegistrationDate(int vehicleAge) {
        dataObject.getPolicyCenter().setRegDate(TestDataGenerator.generateRegDate(vehicleAge));
        dataObject.getPortal().setRegDate(TestDataGenerator.generateRegDate(vehicleAge));
        return this;
    }

    public BasePage selectRegistrationDateInRange(int minAge, int maxAge) {
        dataObject.getPolicyCenter().setRegDate(TestDataGenerator.generateRegDate(rand.nextInt(minAge, maxAge)));
        dataObject.getPortal().setRegDate(TestDataGenerator.generateRegDate(rand.nextInt(minAge, maxAge)));
        return this;
    }

    public BasePage selectCoverageType(String type) {
        logger.info("Selected coverage type " + type);
        long mileage = TestDataGenerator.generateMileageFromCoverageType(type);
        String regDate = TestDataGenerator.generateRegDateFromCoverageType(type);
        dataObject.getPolicyCenter().setRegDate(regDate);
        dataObject.getPortal().setRegDate(regDate);
        dataObject.getPolicyCenter().setMileage(mileage);
        dataObject.getPortal().setMileage(mileage);
        dataObject.getPolicyCenter().setCoverageType(type);
        dataObject.getPortal().setCoverageType(type);
        dataObject.getClaimCenter().setVehicleMileage(mileage + 100);
        return this;
    }

    private void changeCoverageType(String type) {
        logger.info("Changed coverage type to " + type);
        long mileage = TestDataGenerator.generateMileageFromCoverageType(type);
        String regDate = TestDataGenerator.generateRegDateFromCoverageType(type);
        dataObject.getPolicyCenter().setCoverageType(type);
        dataObject.getPolicyCenter().setRegDateChange(regDate);
        dataObject.getPolicyCenter().setMileageUpdated(mileage);
    }

    public BasePage selectRandomCoverageType() {
        typesCountry = CoverageTypesInCountry.types.get(dataObject.getCountry());
        types = typesCountry.get(dataObject.getPolicyCenter().getAdminLabel());
        String type = types[rand.nextInt(types.length)];
        selectCoverageType(type);
        return this;
    }

    public BasePage selectLicensePlate(String value) {
        dataObject.getPolicyCenter().setLicensePlate(value);
        dataObject.getPortal().setLicensePlate(value);
        return this;
    }

    public BasePage selectAnotherRandomCoverageType() {
        typesCountry = CoverageTypesInCountry.types.get(dataObject.getCountry());
        types = typesCountry.get(dataObject.getPolicyCenter().getAdminLabel());
        String type;
        do {
            type = types[rand.nextInt(types.length)];
        } while (type.equals(dataObject.getPolicyCenter().getCoverageType()));
        changeCoverageType(type);
        return this;
    }

    public BasePage selectLabel(String label) {
        dataObject.getPolicyCenter().setAdminLabel(label);
        return this;
    }

    public BasePage selectWarrantyDuration(int duration) {
        dataObject.getPolicyCenter().setWarrantyDuration(duration);
        return this;
    }

    public BasePage selectMaintenanceDuration(int duration) {
        dataObject.getPolicyCenter().setMaintenanceDuration(duration);
        return this;
    }

    public BasePage selectMaintenanceMileage(long mileage) {
        dataObject.getPolicyCenter().setMaintenanceMileage(mileage);
        return this;
    }

    public BasePage selectManagementType(String type) {
        dataObject.getPolicyCenter().setManagementType(type);
        return this;
    }

    public BasePage selectVehicleSpecific(String spec) {
        dataObject.getPolicyCenter().setVehicleSpecific(spec);
        return this;
    }

    public BasePage selectVehicleType(String type) {
        dataObject.getPolicyCenter().setVehicleType(type);
        return this;
    }

    public BasePage selectRandomLabelExceptThese(String[] excludedLabels) {
        String label;
        do {
            label = randomConstant(Labels.class);
        } while (Arrays.asList(excludedLabels).contains(label));
        dataObject.getPolicyCenter().setAdminLabel(label);
        return this;
    }

    public BasePage selectRandomLabel() {
        labels = LabelsInCountry.labels.get(dataObject.getCountry());
        String label = labels[rand.nextInt(labels.length)];
        selectLabel(label);
        selectRandomCar();
        return this;
    }

    public BasePage selectRandomLicensePlate() {
        dataObject.getPolicyCenter().setLicensePlate(String.valueOf(rand.nextInt(10000, 99999)));
        dataObject.getPortal().setLicensePlate(String.valueOf(rand.nextInt(10000, 99999)));
        return this;
    }

    public BasePage selectRandomCountry() {
        selectCountry(randomEnum(Countries.class));
        return this;
    }

    public BasePage selectRandomCountryExcept(Countries excludeCountry) {
        Countries includedCountry;
        do {
            includedCountry = randomEnum(Countries.class);
        } while (includedCountry.equals(excludeCountry));
        selectCountry(includedCountry);
        return this;
    }

    public BasePage selectEnergyType(EnergyTypes type) {
        dataObject.getPolicyCenter().setEnergyType(type.toString());
        dataObject.getPortal().setEnergyType(type.toString());
        return this;
    }

    public BasePage selectEnergyTypeFromThese(EnergyTypes[] types) {
        EnergyTypes type = types[rand.nextInt(types.length)];
        dataObject.getPolicyCenter().setEnergyType(type.toString());
        dataObject.getPortal().setEnergyType(type.toString());
        return this;
    }

    public BasePage selectRandomEnergyType() {
        dataObject.getPolicyCenter().setEnergyType(randomEnum(EnergyTypes.class).toString());
        dataObject.getPortal().setEnergyType(randomEnum(EnergyTypes.class).toString());
        return this;
    }

    public BasePage selectCar(Makes car) {
        String[] models = Models.brands.get(car);
        dataObject.getPolicyCenter().setMake(car.toString());
        dataObject.getPolicyCenter().setModel(models[rand.nextInt(models.length)]);

        dataObject.getPortal().setMake(car.toString());
        dataObject.getPortal().setModel(models[rand.nextInt(models.length)]);
        selectRandomEnergyType();
        selectRandomLicensePlate();
        selectRandomCarPower();
        return this;
    }

    public BasePage selectModel(String model) {
        dataObject.getPolicyCenter().setModel(model);
        dataObject.getPortal().setModel(model);
        return this;
    }

    public BasePage selectModelFromThese(String[] models) {
        String model = models[rand.nextInt(models.length)];
        dataObject.getPolicyCenter().setModel(model);
        dataObject.getPortal().setModel(model);
        return this;
    }

    public BasePage selectRandomCar() {
        Makes car;
        switch (dataObject.getPolicyCenter().getAdminLabel()) {
            case ARC -> car = ALFAROMEO;
            case DSC -> car = DS;
            case LC -> car = LANCIA;
            default -> car = randomEnum(Makes.class);
        }
        selectCar(car);
        return this;
    }

    public BasePage selectRandomCarPower() {
        switch (dataObject.getCountry()) {
            case France, Spain -> maxPower = 250;
            case Italy -> maxPower = 249;
            case Germany -> maxPower = 300;
        }
        dataObject.getPolicyCenter().setCarPower(rand.nextInt(99, maxPower));
        dataObject.getPortal().setCarPower(rand.nextInt(99, maxPower));
        return this;
    }

    public BasePage selectCarPowerInRange(int min, int max) {
        dataObject.getPolicyCenter().setCarPower(rand.nextInt(min, max));
        dataObject.getPortal().setCarPower(rand.nextInt(min, max));
        return this;
    }

    public BasePage selectCarPower(int carPower) {
        dataObject.getPolicyCenter().setCarPower(carPower);
        dataObject.getPortal().setCarPower(carPower);
        return this;
    }

    public BasePage selectMileageLimitedAt(long maxMileage) {
        long mileageValue = rand.nextLong(maxMileage - 10000, maxMileage);
        dataObject.getPolicyCenter().setMileage(mileageValue);
        dataObject.getPortal().setMileage(mileageValue);
        dataObject.getClaimCenter().setVehicleMileage(mileageValue + 100);
        return this;
    }

    public BasePage selectMileageInRange(long minMileage, long maxMileage) {
        long mileageValue = rand.nextLong(minMileage, maxMileage);
        dataObject.getPolicyCenter().setMileage(mileageValue);
        dataObject.getPortal().setMileage(mileageValue);
        dataObject.getClaimCenter().setVehicleMileage(mileageValue + 100);
        return this;
    }

    public BasePage selectEGVOCoverageType(String type) {
        logger.info("Selected coverage type " + type + " for EGVO Standalone product");
        dataObject.getPolicyCenter().setEgvoCoverageType(type);
        return this;
    }

    public BasePage selectAnotherRandomEnergyType() {
        EnergyTypes type;
        do {
            type = randomEnum(EnergyTypes.class);
        } while (type.toString().equals(dataObject.getPolicyCenter().getEnergyType()));
        dataObject.getPolicyCenter().setEnergyChange(type.toString());
        return this;
    }

    public BasePage selectDifferentMileage(long mileage) {
        dataObject.getPolicyCenter().setMileageUpdated(mileage);
        return this;
    }

    public boolean isDocumentDownloaded(String fileName) {
        String userHome = System.getProperty("user.home");
        File file = new File(userHome + File.separator + "Downloads" + File.separator + fileName);
        waitForFileDownloadComplete(file);
        boolean result = file.exists();
        boolean deleted;
        if (result) {
            logger.debug("File " + fileName + " is downloaded successfully");
            deleted = file.delete();
            if (deleted) {
                logger.debug("File " + fileName + " is deleted successfully");
            }
        }
        return result;
    }

    public void addClassLevelErrorToReport(String message) {
        String imagePath = screenshotDirectory
                + File.separator + "error_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ".png";
        TestNGResultHandling.takeScreenShot(imagePath);
        getTestClass().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
    }

    public void addStepInfoToReport(String stepInfo) {
        String imagePath = screenshotDirectory
                + File.separator
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ".png";
        TestNGResultHandling.takeScreenShot(imagePath);
        if (methodName == null) {
            getTestClass().info(stepInfo, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
        } else {
            getTestMethod(methodName).info(stepInfo, MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
        }
    }

    public void closeErrorPopup() {
        try {
            if (isElementDisplayed(errorPopup, 1)) {
                switchToActiveElement();
                closePopup();
            }
        } catch (NoSuchElementException ignore) {}
    }

    public String getTextColor(Label text) {
        return driver.findElement(text).getCssValue("color");
    }

    public void setRandomAccountType() {
        accountType = (getCurrentEnvironment().equals(DEV)) ? AccountType.values()[rand.nextInt(AccountType.values().length)] : Person;
        logger.info("Set account type as " + accountType);
    }

    public void setAccountTypeAs(AccountType type) {
        accountType = type;
    }

    public void generateRandomContactInformation() {
        dataObject.setCompanyName(faker.company().name().replace("'", ""));
        dataObject.setContactFirstName(faker.name().firstName().replace("'", ""));
        dataObject.setContactLastName(faker.name().lastName().replace("'", ""));
        dataObject.getPortal().setFirstName(faker.name().firstName().replace("'", ""));
        dataObject.getPortal().setLastName(faker.name().lastName().replace("'", ""));
        dataObject.getPortal().setCompany(faker.company().name().replace("'", ""));
    }

    public void setRandomContactType() {
        contactType = ContactType.values()[rand.nextInt(ContactType.values().length)];
        logger.info("Set contact type as " + contactType);
    }

    public BasePage selectMakerWarrantyEndDate(String value) {
        dataObject.getPolicyCenter().setWarrantyEndDate(value);
        return this;
    }

    public String getTextFromJSExecutor(By byElement) {
        return (String) jsExecutor.executeScript("return arguments[0].value;", driver.findElement(byElement));
    }
}
