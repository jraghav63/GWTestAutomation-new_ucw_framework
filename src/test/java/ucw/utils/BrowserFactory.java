package ucw.utils;

import com.epam.healenium.SelfHealingDriver;
import com.epam.healenium.SelfHealingDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ucw.enums.OSArch;
import ucw.models.config.Config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static ucw.enums.OSArch.LINUX;
import static ucw.enums.OSArch.WIN;

public class BrowserFactory {
    private static final Logger logger = LogManager.getLogger(BrowserFactory.class);

    public static WebDriver getDriver(Config config) {
        WebDriver driver;
        ChromeOptions options = setupChromeOptions(config.isHeadless());
        if (config.isSelfHealing()) {
            logger.info("Init self-healing ChromeDriver");
            driver = SelfHealingDriver.create(new ChromeDriver(options));
        } else {
            logger.info("Init ChromeDriver");
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getTimeout()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getTimeout()));
        return driver;
    }

    public static WebDriverWait getDriverWait(Config config, WebDriver driver) {
        if (config.isSelfHealing()) {
            return new SelfHealingDriverWait(driver, Duration.ofSeconds(config.getTimeout()));
        } else {
            return new WebDriverWait(driver, Duration.ofSeconds(config.getTimeout()));
        }
    }

    public static ChromeOptions setupChromeOptions(boolean headless) {
        logger.info("Setup Chrome options with headless mode is " + headless);
        OSArch os = OSUtils.getCurrentOS();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        if (os.equals(WIN)) {
            logger.debug("Chrome driver initialization on Windows machine");
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--proxy-server='direct://'");
                options.addArguments("--proxy-bypass-list=*");
                options.addArguments("--disable-browser-side-navigation");
            }
        } else if (os.equals(LINUX)) {
            logger.debug("Chrome driver initialization on Linux machine");
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--proxy-server='direct://'");
                options.addArguments("--proxy-bypass-list=*");
                options.addArguments("--no-sandbox");
                options.addArguments("--remote-allow-origins=*");
            }
        }
        logger.debug("Add notification disabled preferences");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");
        return options;
    }
}
