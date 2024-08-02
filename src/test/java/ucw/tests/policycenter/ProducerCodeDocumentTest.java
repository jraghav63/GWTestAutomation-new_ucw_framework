package ucw.tests.policycenter;

import org.testng.annotations.*;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

//UCWAR-1196
public class ProducerCodeDocumentTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1196: Upload/download documents in the BO Policy for Producers Code Tab");
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "Producer code document tab")
    public void test_documentTab() {
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().uploadDocument("src/test/resources/UserSetup.docx");
        assertTrue(basePage.isDocumentDownloaded("UserSetup.docx"),"Producer code document tab validation failed");
        policyCenter.atAdministrationPage().deleteUploadedDocument();
    }
}
