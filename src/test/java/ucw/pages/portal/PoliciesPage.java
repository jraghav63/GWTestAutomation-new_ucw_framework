package ucw.pages.portal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import ucw.elements.*;
import ucw.models.testdata.ComplaintsActivity;

public class PoliciesPage extends Portal {
    private static final Logger logger = LogManager.getLogger(PoliciesPage.class);
    private static final Label brandValue = Label.xpath("((//tr[@ng-repeat='vehicle in vehicles'])[1]/td)[4]");
    private static final Tab complaintsActivityTab = Tab.xpath("//div[@tile-title='Complaints Activity']");
    private static final Form activityForm = Form.xpath("//form[@name='complaintsActivityForm']");
    private static final Dropdown subjectDropdown = Dropdown.id("subjStatus");
    private static final TextBox complaintTitle = TextBox.xpath("//span[text()='Complaint Title']/../following-sibling::div/div/input");
    private static final Dropdown priorityDropdown = Dropdown.xpath("//select[@gw-pl-select='activityInfoView.priority.value']");
    private static final TextBox complainantNameTxt = TextBox.xpath("//span[text()='Complainant Name']/../following-sibling::div/div/input");
    private static final TextBox claimNumberTxt = TextBox.xpath("//span[text()='Claim Number']/../following-sibling::div/div/input");
    private static final Dropdown relatedToDropdown = Dropdown.id("relatedToStatus");
    private static final TextArea complaintsDetailsArea = TextArea.xpath("//textarea[@ng-model='noteInfo.text.value']");
    private static final Button addActivityButton = Button.xpath("//button[@ng-click='showAddActivity()']");
    private static final Button activityExpandButton = Button.xpath("//i[contains(@ng-class,('activity.expanded'))]");
    private static final Button saveActivityButton = Button.xpath("//button[@ng-click='save(complaintsActivityForm)']");
    private static final Label createdMessage = Label.xpath("//div[contains(text(),'Activity created')]");
    private static final Items createdActivity = Items.xpath("//div[@gw-activities-view and @activities='activitiesFuture']");
    private String complaintsSectionValue = "//div[contains(text(),'SECTION')]/following-sibling::div";
    private static final Label complaintsDetailsLabel = Label.xpath("//div[@class='gw-note-details']/div[text()='Complaints']/following-sibling::div");
    private static final Label relatedToLabel = Label.xpath("//div[@ng-if=\"note.relatedTo.includes('Policy')\"]");
    private static final String[] complaintSections = {"ESCALATION DATE", "CONTRACT", "COMPLAINT TITLE", "COMPLAINTS ID",
        "COMPLAINANT NAME", "CLAIM NUMBER", "ASSIGNED TO", "DATE RECEIVED"};

    public void navigateToActivityTab() {
        click(complaintsActivityTab);
        waitForAttributeContains(complaintsActivityTab, "class", "gw-active");
        waitForElementVisible(addActivityButton);
    }

    public void createComplaintsActivity() {
        clickAddActivity();
        fillComplaintsActivityInformation();
        clickSaveActivity();
    }

    public void clickAddActivity() {
        click(addActivityButton);
        waitForElementVisible(activityForm);
    }

    public void fillComplaintsActivityInformation() {
        selectRandom(subjectDropdown);
        sendText(complaintTitle, faker.lorem().sentence());
        selectRandom(priorityDropdown);
        sendText(complainantNameTxt, faker.name().fullName());
        selectRandom(relatedToDropdown);
        sendText(complaintsDetailsArea, faker.lorem().sentence(10));
    }

    public void clickSaveActivity() {
        click(saveActivityButton);
        waitForElementVisible(createdMessage);
        switchToActiveElement();
        click(okButton);
        waitForElementVisible(createdActivity);
    }

    public boolean isActivityCreated() {
        return isElementDisplayed(createdActivity);
    }

    public void viewCreatedActivity(int activityIndex) {
        WebElement expandButton = findElements(activityExpandButton).get(activityIndex);
        click(expandButton);
        waitForAttributeContains(expandButton, "class", "minus-square");
    }

    public ComplaintsActivity getActivityDetailInformation() {
        ComplaintsActivity complaintsActivity = new ComplaintsActivity();
        complaintsActivity.setEscalationDate(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "ESCALATION DATE"))
                )
        );
        complaintsActivity.setSubject(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "CONTRACT"))
                )
        );
        complaintsActivity.setComplaintTitle(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "COMPLAINT TITLE"))
                )
        );
        complaintsActivity.setComplaintsId(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "COMPLAINTS ID"))
                )
        );
        complaintsActivity.getNewNote().setComplainantName(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "COMPLAINANT NAME"))
                )
        );
        complaintsActivity.setAssignTo(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "ASSIGNED TO"))
                )
        );
        complaintsActivity.setDateReceived(
                getText(Label.xpath(
                        complaintsSectionValue.replace("SECTION", "DATE RECEIVED"))
                )
        );
        complaintsActivity.getNewNote().setComplaintDetails(getText(complaintsDetailsLabel));
        complaintsActivity.getNewNote().setRelatedTo(getText(relatedToLabel));
        return complaintsActivity;
    }

    public String getBrandValue() {
        logger.info("Get brand value in policy summary screen");
        waitForTextNotToBe(brandValue, "");
        return getText(brandValue);
    }
}
