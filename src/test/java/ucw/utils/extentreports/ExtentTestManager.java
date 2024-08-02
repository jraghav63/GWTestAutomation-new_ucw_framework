package ucw.utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestClasses = new HashMap<>();
    static Map<String, ExtentTest> extentTestMethods = new HashMap<>();
    static ExtentReports extent = ExtentManager.getExtentReports();

    public static synchronized ExtentTest getTestClass() {
        return extentTestClasses.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest getTestMethod(String testName) {
        return extentTestMethods.get(testName);
    }

    public static synchronized void startTest(String testName) {
        extentTestClasses.put((int) Thread.currentThread().getId(), extent.createTest(testName));
    }

    public static synchronized void startMethod(String testName, String desc) {
        desc = (desc == null || desc.isEmpty()) ? testName : desc;
        ExtentTest testClass = extentTestClasses.get((int) Thread.currentThread().getId());
        ExtentTest testMethod = testClass.createNode(desc, "Test method: " + testName);
        extentTestMethods.put(testName, testMethod);
    }
}
