package ucw.utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import ucw.utils.DateHelper;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();
    public static final String reportFile = "build/extent-reports/index.html";

    public synchronized static ExtentReports getExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFile);
        reporter.config().setDocumentTitle("CBI API Automation Test Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
