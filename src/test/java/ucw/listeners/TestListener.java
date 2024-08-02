package ucw.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ucw.pages.BasePage;
import ucw.utils.TestNGResultHandling;
import ucw.utils.extentreports.ExtentManager;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ucw.utils.extentreports.ExtentTestManager.getTestMethod;

public class TestListener extends BasePage implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    public void onStart(ITestContext context) {
        logger.info("Start test execution");
    }

    public void onFinish(ITestContext context) {
        ExtentManager.extentReports.setSystemInfo("Environment", getCurrentEnvironment().toString());
        ExtentManager.extentReports.setSystemInfo("Browser", "Chrome");
        ExtentManager.extentReports.setSystemInfo("Machine", "local");
        ExtentManager.extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
        logger.info("Start running test case");
    }

    public void onTestSuccess(ITestResult result) {
        String methodName = getTestMethodName(result);
        getTestMethod(methodName).pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
         String imagePath = screenshotDirectory
                             + File.separator
                             + "error_" + result.getName().trim()
                             + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                             + ".png";
         TestNGResultHandling.takeScreenShot(imagePath);
         String methodName = getTestMethodName(result);
         getTestMethod(methodName).fail(result.getThrowable(),
                 MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build()
         );
    }

    public void onTestSkipped(ITestResult result) {
        String methodName = getTestMethodName(result);
        getTestMethod(methodName).skip("Test skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
    }
}
