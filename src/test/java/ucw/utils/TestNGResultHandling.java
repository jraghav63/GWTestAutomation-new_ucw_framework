package ucw.utils;

import com.epam.healenium.SelfHealingDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import ucw.pages.BasePage;

import java.io.File;
import java.io.IOException;

public class TestNGResultHandling {
    private static File scrFile;
    private static File screenshotName;
    public static void takeScreenShot(String imagePath) {
        if (BasePage.driver instanceof SelfHealingDriver) {
            scrFile = ((TakesScreenshot) ((SelfHealingDriver) BasePage.driver).getDelegate()).getScreenshotAs(OutputType.FILE);
        } else {
            scrFile = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
        }
        screenshotName = new File(imagePath);
        try {
            FileUtils.copyFile(scrFile, screenshotName);

            Reporter.log("<br> <img src=" + screenshotName
                    + " class=\"img-rounded\" width=1000"
                    + " height=450" + "/> <br>"
            );
            Reporter.log("<a href=" + screenshotName + "></a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
