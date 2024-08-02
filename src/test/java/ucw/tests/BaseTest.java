package ucw.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ucw.pages.BasePage;
import ucw.utils.OSUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;

import static ucw.utils.extentreports.ExtentTestManager.getTestMethod;
import static ucw.utils.extentreports.ExtentTestManager.startMethod;

public class BaseTest {
    protected static final Random rand = new Random();
    public static BasePage basePage;
    public static String testName;
    protected String dataSetInfo, dataFileName;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        basePage = new BasePage();
        basePage.initialize();
        OSUtils.killChromeDriverProcesses();
    }

    @BeforeClass
    public void beforeClass(ITestContext context) {
        basePage.openBrowser();
        basePage.setDataObject();
    }

    @AfterClass
    public void afterClass() {
        basePage.tearDown();
    }

    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context) {
        dataSetInfo = null;
        Test test = method.getAnnotation(Test.class);
        testName = method.getName();
        dataFileName = testName;
        basePage.setMethodName(testName);
        String description = (test != null) ? test.description() : "";
        startMethod(testName, description);
        basePage.generateRandomContactInformation();
    }

    @AfterMethod
    public void afterMethod() throws JsonProcessingException {
        if (dataSetInfo != null) {
            getTestMethod(testName).info(dataSetInfo);
        }
        basePage.logTheGeneratedData();
        basePage.exportDataToFile(dataFileName);
        basePage.setMethodName(null);
    }
}
