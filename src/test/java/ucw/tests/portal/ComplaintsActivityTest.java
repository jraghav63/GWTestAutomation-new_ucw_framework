package ucw.tests.portal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.models.testdata.ComplaintsActivity;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class ComplaintsActivityTest extends BaseTest {
    private PolicyCenter policyCenter;
    private Portal portal;

    @BeforeClass
    public void goToCenters() {
        basePage.selectManagementType(INSURED);
        startTest("UCWAR-2918: Enable dealers to raise complaints from the portal");
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.atInternalSystemPage().changeClock(PRESENT);
        portal = basePage.loginPortal();
    }

    @AfterClass
    public void revertSystemClock() {
        basePage.switchToPolicyCenterTab();
        policyCenter.atInternalSystemPage().changeClock(FUTURE);
    }

    @Test(description = "Validate the Complaint Activity created successfully in Portal")
    public void test_complaintsActivityCreation() {
        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.clickMenu("Desktop");

        basePage.switchToPortalTab();
        portal.atSubmissionPage().addNewSubmission();
        assertTrue(portal.atSubmissionPage().isPolicySubmitted(), "Policy has not been submitted successfully in Portal");

        portal.returnHome();
        portal.searchPolicyNumber();
        assertTrue(portal.isPolicyNumberFound(), "Policy number is not found in Portal");
        portal.clickPolicySearchResult();
        portal.atPoliciesPage().navigateToActivityTab();
        portal.atPoliciesPage().createComplaintsActivity();
        assertTrue(portal.atPoliciesPage().isActivityCreated(), "Complaints activity is not created successfully");
        portal.atPoliciesPage().viewCreatedActivity(0);
        ComplaintsActivity portalActivity = portal.atPoliciesPage().getActivityDetailInformation();

        basePage.switchToPolicyCenterTab();
        policyCenter.atSearchPage().searchPortalPolicy();
        policyCenter.atPolicyPage().waitForSummaryScreen();
        assertTrue(policyCenter.atSearchPage().isPortalPolicyFound(), "Portal Policy is not found in Policy Center");
        ComplaintsActivity pcActivity = policyCenter.atPolicyPage().viewCurrentActivity(0);

        assertEquals(portalActivity.getComplaintsId(), pcActivity.getComplaintsId(), "Complaints Id is not matched as expected");
        assertEquals(portalActivity.getSubject(), pcActivity.getSubject(), "Activity subject is not matched as expected");
        assertEquals(portalActivity.getComplaintTitle(), pcActivity.getComplaintTitle(), "Complaint title is not matched as expected");
        assertEquals(portalActivity.getAssignTo(), pcActivity.getAssignTo(), "Assigned to is not matched as expected");
        assertEquals(portalActivity.getDateReceived(), pcActivity.getDateReceived(), "Date Received is not matched as expected");
        assertEquals(portalActivity.getNewNote().getComplaintDetails(), pcActivity.getNewNote().getComplaintDetails(), "Complaint details is not matched as expected");
        assertEquals(portalActivity.getNewNote().getRelatedTo(), pcActivity.getNewNote().getRelatedTo(), "Related to is not matched as expected");
    }
}
