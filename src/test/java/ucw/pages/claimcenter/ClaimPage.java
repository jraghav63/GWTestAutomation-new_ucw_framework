package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ucw.elements.*;
import ucw.models.soap.SBEntry;
import ucw.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.ENTER;
import static ucw.enums.AccountType.Person;

public class ClaimPage extends ClaimCenter {
    private static final Logger logger = LogManager.getLogger(ClaimPage.class);
    private static final Title step1TitleTxt = Title.id("FNOLWizard:FNOLWizard_FindPolicyScreen:ttlBar");
    private static final Title newClaimSavedTxt = Title.id("NewClaimSaved:NewClaimSavedScreen:ttlBar");
    private static final TextBox stp1PolNumTxtBx = TextBox.xpath("//input[@id='FNOLWizard:FNOLWizard_FindPolicyScreen:FNOLWizardFindPolicyPanelSet:policyNumber-inputEl']");
    private static final Label newClaimTxt = Label.xpath("//label[@id='FNOLWizard:FNOLWizard_FindPolicyScreen:FNOLWizardFindPolicyPanelSet:2']");
    private static final Button nextButton = Button.id("FNOLWizard:Next-btnInnerEl");
    private static final Label claimLossDateError = Label.cssSelector("img.error_icon");
    private static final Dropdown nameQuickClaimDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:ReportedBy_Name-inputEl']");
    private static final Title quickClaimAutoHeadingTxt = Title.id("FNOLWizard:FNOLWizard_NewQuickClaimScreen:ttlBar");
    private static final Link nameDdIcon = Link.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:ReportedBy_Name:ReportedBy_NameMenuIcon']");
    private static final Button newPersonButtonTxt = Button.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:ReportedBy_Name:ClaimNewPersonAndCompanyPicker_PSAMenuItemSet:ClaimNewPersonOnlyPickerMenuItemSet_NewPersonMenuItem-textEl']");
    private static final Title newContactHeadingTxt = Title.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ttlBar']");
    private static final Dropdown prefixDd = Dropdown.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PersonNameInputSet:GlobalPersonNameInputSet:Prefix-inputEl']");
    private static final TextBox ccFirstNameTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PersonNameInputSet:GlobalPersonNameInputSet:FirstName-inputEl']");
    private static final TextBox ccLastNameTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PersonNameInputSet:GlobalPersonNameInputSet:LastName-inputEl']");
    private static final Label tagsEmployeeTxt = Label.xpath("//li[contains(@data-recordindex,'0')]");
    private static final Label tagsPolicyPersonTxt = Label.xpath("//li[contains(@data-recordindex,'1')]");
    private static final Button stp1SrchPolButton = Button.id("FNOLWizard:FNOLWizard_FindPolicyScreen:FNOLWizardFindPolicyPanelSet:Search");
    private static final Label errorMessage = Label.xpath("//*[@id='FNOLWizard:FNOLWizard_FindPolicyScreen:_msgs']/div");
    private static final Label stp1SrchpPolResTxt = Label.xpath("//div[@id='FNOLWizard:FNOLWizard_FindPolicyScreen:FNOLWizardFindPolicyPanelSet:PolicyResultLV-body']//td[2]//a");
    private static final Button actionButton = Button.id("Claim:ClaimMenuActions-btnInnerEl");
    private static final TextBox ccEmailTxt = TextBox.id("NewContactPopup:ContactDetailScreen:ContactBasicsDV:PersonContactInfoInputSet:Primary-inputEl");
    private static final TextBox ccAddressTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox address2Txt = TextBox.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:0:VehicleIncidentPanelSet:VehicleIncidentAutoBodyDV:AutobodyInputGroup:AddressLine2-inputEl']");
    private static final TextBox ccPostalCodeTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:PostalCode-inputEl']");
    private static final TextBox ccCityTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:City-inputEl']");
    private static final Label warningMsg = Label.xpath("//tbody[@id='NewClaimMileageInconsistencyWorksheet-tbody']/tr/td[contains(@id,'ext-element')]/div/div/div/div/div/span");
    private static final Button closeButton = Button.xpath("//tbody[@id='NewClaimMileageInconsistencyWorksheet-tbody']/tr/td[contains(@id,'ext-element')]/div/div/div/a");
    private static final Button ccUpdateButton = Button.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV_tb:ContactDetailToolbarButtonSet:CustomUpdateButton-btnInnerEl']");
    private static final Table duplicateClaimTable = Table.id("NewClaimDuplicatesWorksheet-table");
    private static final Dropdown primaryAddressCountry = Dropdown.xpath("//input[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:Country-inputEl']");
    private static final TextBox primaryAddress1 = TextBox.xpath("//input[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox vehicleMileageTxt = TextBox.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:CauseAndDefaultDV:MileageAtDateOfLoss_PSA-inputEl']");
    private static final Dropdown causeDdTxt = Dropdown.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:CauseAndDefaultDV:Claim_Cause_PSA-inputEl']");
    private static final Dropdown causeSubgroupDdTxt = Dropdown.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:CauseAndDefaultDV:Claim_CauseSubGroup_PSA-inputEl']");
    private static final Dropdown defaultDdTxt = Dropdown.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:CauseAndDefaultDV:Claim_Default_PSA-inputEl']");
    private static final Dropdown defaultSubgroupDdTxt = Dropdown.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:CauseAndDefaultDV:Claim_DefaultSubGroup_PSA-inputEl']");
    private static final Button finishButton = Button.xpath("//a[@id='FNOLWizard:Finish']");
    private static final Label dupContactMsg = Label.xpath("//*[@id='DuplicateContactPopup:ttlBar']");
    private static final Link selectLink = Link.xpath("//*[@id='DuplicateContactPopup:DuplicateContactLV:0:SelectContact']");
    private static final Link newCompanyTxt = Link.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:ReportedBy_Name:ClaimNewPersonAndCompanyPicker_PSAMenuItemSet:NewPersonAndCompanyOnlyPickerMenuItemSet-textEl']");
    private static final TextBox companyNameTxt = TextBox.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:OrganizationName:GlobalContactNameInputSet:Name-inputEl']");
    private static final Dropdown cmpCountryDdTxt = Dropdown.id("NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryAddressInputSet:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:Country-inputEl");
    private static final Button personVendorDdIcon = Button.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryContact:PrimaryContactMenuIcon']");
    private static final Button newPersonVendorTxt = Button.xpath("//*[@id='NewContactPopup:ContactDetailScreen:ContactBasicsDV:PrimaryContact:ClaimNewPersonOnlyPickerMenuItemSet:ClaimNewPersonOnlyPickerMenuItemSet_NewPersonMenuItem-textEl']");
    private static final Label claimNoStmtTxt = Label.xpath("//label[@id='NewClaimSaved:NewClaimSavedScreen:NewClaimSavedDV:Header']");
    private static final Link viewNewClaimLink = Link.xpath("//div[@id='NewClaimSaved:NewClaimSavedScreen:NewClaimSavedDV:GoToClaim-inputEl']");
    private static final Title claimSumTxt = Title.id("ClaimSummary:ClaimSummaryScreen:ttlBar");
    private static final Label claimProgressLbl = Label.xpath("//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:ServiceRequestDV:Progress-inputEl']");
    private static final Dropdown locationDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:CCAddressInputSet:globalAddressContainer:Address_Picker-inputEl']");
    private static final Dropdown lossCauseDdTxt = Dropdown.xpath("//*[@id='FNOLWizard:FNOLWizard_NewQuickClaimScreen:QuickClaimDV:Claim_LossCause_PSA-inputEl']");
    private static final Dropdown stp3LocationDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:Address_Picker-inputEl']");
    private static final Dropdown stp3CityDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:City-inputEl']");
    private static final Dropdown stp3CountryDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:Country-inputEl']");
    private static final TextBox stp3Addr1TxtBx = TextBox.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:AddressLine1-inputEl']");
    private static final TextBox stp3Addr2TxtBx = TextBox.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:AddressLine2-inputEl']");
    private static final Dropdown stp3PostCodeDdTxt = Dropdown.xpath("//input[@id='FNOLWizard:AutoWorkersCompWizardStepSet:FNOLWizard_NewLossDetailsScreen:LossDetailsAddressDV:AddressDetailInputSetRef:CCAddressInputSet:globalAddressContainer:globalAddress:GlobalAddressInputSet:PostalCode-inputEl']");
    private static final Button actionsButton = Button.xpath("//*[@id='Claim:ClaimMenuActions-btnInnerEl']");
    private static final Button serviceRequest = Button.xpath("//a[@id='Claim:ClaimMenuActions:ClaimNewOtherMenuItemSet:ClaimMenuActions_NewOther:ClaimMenuActions_NewServiceRequest-itemEl']");
    private static final Button serviceTypePicker = Button.xpath("//div[@id='NewServiceRequest:NewServiceRequestScreen:NewServiceRequestDV:Kind-trigger-picker']");
    private static final Link vendorName = Link.xpath("//div[@id='NewServiceRequest:NewServiceRequestScreen:NewServiceRequestDV:Specialist-trigger-picker']");
    private static final CheckBox serviceCheckbox = CheckBox.xpath("//img[@class='x-grid-checkcolumn ']");
    private static final Button removeButton = Button.xpath("//span[contains(text(),'emove')]");
    private static final Button addButton = Button.xpath("//span[text()='Add']");
    private static final CheckBox mcCheckbox = CheckBox.xpath("(//input[@class=' x-tree-checkbox'])[2]");
    private static final Button submitButton = Button.xpath("//span[text()='Submit']");
    private static final Dropdown typeCheckScreenDdTxt = Dropdown.xpath("//input[@id='QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:PrimaryPayee_Type-inputEl']");
    private static final Label amtPayRepairerTxt = Label.xpath("//*[contains(text(),'Amount to Pay Repairer')]//../../td[2]/div");
    private static final Label availableResourcesTxt = Label.xpath("//*[@id='QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:Transaction_AvailableReserves-inputEl']");
    private static final TextBox repairerCategoryTxt = TextBox.xpath("//table[@class='x-grid-item']//tr[@class='  x-grid-row']//div[contains(text(),'Repairer')]//..//../td[3]");
    private static final Label availableResourcesErrorTxt = Label.xpath("//*[@id='QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:_msgs']/div/text()");
    private static final Button finishCheckButton = Button.xpath("//*[@id='QuickCreateCheckWizard:Finish-btnInnerEl']");
    private static final Button cancelCheckButton = Button.xpath("//*[@id='QuickCreateCheckWizard:Cancel-btnInnerEl']");
    private static final Button okPopUpButton = Button.xpath("//span[contains(text(),'OK')]");
    private static final Link financialSummaryTxt = Link.xpath("//*[@id='Claim:MenuLinks:Claim_ClaimFinancialsGroup:ClaimFinancialsGroup_ClaimFinancialsSummary']/div");
    private static final Image imageIconImg = Image.xpath("//*[@id='ClaimFinancialsSummary:ClaimFinancialsSummaryScreen:financialsPanel:FinancialsSummaryPanelSet:FinancialsSummaryLV:0:0:0:0:ts:tsMenuIcon']");
    private static final Button editReserveButton = Button.xpath("//*[@id='ClaimFinancialsSummary:ClaimFinancialsSummaryScreen:financialsPanel:FinancialsSummaryPanelSet:FinancialsSummaryLV:0:0:0:0:ts:QuickMenu_EditReserve-textEl']");
    private static final TextBox newAvailReserveTxt = TextBox.xpath("//div[@class='x-grid-item-container']//table//tr[@class='  x-grid-row']//td[8]//div");
    private static final Button saveReserveButton = Button.xpath("//*[@id='NewReserveSet:NewReserveSetScreen:Update-btnInnerEl']");
    private static final Tab servicesLink = Tab.xpath("//span[text()='Services']");
    private static final Table servicesDetailTable = Table.id("ClaimServiceRequests/Details-table");
    private static final Button cancelServiceButton = Button.id("ClaimServiceRequests:StateToolbar:Cancel-btnInnerEl");
    private static final Form cancelReasonPrompt = Form.id("OperationReasonPromptPopup-tbody");
    private static final TextBox reasonTextArea = TextBox.xpath("//textarea[@id='OperationReasonPromptPopup:ReasonForOperation-inputEl']");
    private static final Button confirmCancelButton = Button.id("OperationReasonPromptPopup:Update-btnInnerEl");
    private static final Title servicesHeadingTxt = Title.xpath("//div[@class='x-title x-container-title x-container-title-default x-box-item x-title-default x-title-rotate-none x-title-align-left']//span[contains(text(),'Services')]");
    private static final Button addQuoteButton = Button.xpath("//*[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:AddQuote']");
    private static final Button updateQuoteButton = Button.xpath("//*[@id='AddReviseQuotePopup:Update-btnInnerEl']");
    private static final Button workPlanButton = Button.id("Claim:MenuLinks:Claim_ClaimWorkplan");
    private static final CheckBox selectWorkPlanCheckbox = CheckBox.xpath("//div[@class='x-grid-cell-inner x-grid-cell-inner-checkcolumn']//*[@class='x-grid-checkcolumn ']");
    private static final Button approveWorkPlanButton = Button.xpath("//*[@id='ClaimWorkplan:ClaimWorkplanScreen:ClaimWorkplan_ApproveButton-btnInnerEl']");
    private static final TextBox refNoQuoteTxt = TextBox.xpath("//*[@id='AddReviseQuotePopup:StatementDV:ReferenceNumber-inputEl']");
    private static final Form gtMotiveBody = Form.id("AddReviseQuotePopup:StatementDV:GTMotivePart_PSALV-body");
    private static final TextBox quoteCommentsTxt = TextBox.xpath("//*[@id='AddReviseQuotePopup:StatementDV:comment-inputEl']");
    private static final Button quoteCategory = Button.xpath("//div[@class='x-grid-view x-grid-with-col-lines x-grid-with-row-lines x-fit-item x-grid-view-default']//table[@class='x-grid-item']//tbody//tr[@class='  x-grid-row']//td[2]//div");
    private static final TextBox platformNumber = TextBox.xpath("//div[@class='x-grid-view x-grid-with-col-lines x-grid-with-row-lines x-fit-item x-grid-view-default']//table[@class='x-grid-item']//tbody//tr[@class='  x-grid-row']//td[15]//div");
    private static final TextBox platformAmount = TextBox.xpath("//div[@class='x-grid-view x-grid-with-col-lines x-grid-with-row-lines x-fit-item x-grid-view-default']//table[@class='x-grid-item']//tbody//tr[@class='  x-grid-row']//td[16]//div");
    private static final Button addLineItemsButton = Button.xpath("//*[@id='AddReviseQuotePopup:StatementDV:GTMotivePart_PSALV_tb:Add-btnInnerEl']");
    private static final Button addInvoiceButton = Button.xpath("//*[@id='ClaimServiceRequests:ServiceRequestPanelRow:AddInvoice']");
    private static final TextBox invoiceRefNoTxt = TextBox.xpath("//*[@id='NewInvoice:StatementDV:ReferenceNumber-inputEl']");
    private static final TextBox invoiceCommentTxt = TextBox.xpath("//*[@id='NewInvoice:StatementDV:Comment-inputEl']");
    private static final TextBox invoiceDateTxt = TextBox.xpath("//*[@id='NewInvoice:StatementDV:InvoiceIssueDate_PSA-inputEl']");
    private static final TextBox invoiceAmount = TextBox.xpath("//div[@class='x-grid-view x-grid-with-col-lines x-grid-with-row-lines x-fit-item x-grid-view-default']//table[@class='x-grid-item']//tbody//tr[@class='  x-grid-row']//td[4]//div");
    private static final Button updateInvoiceButton = Button.xpath("//*[@id='NewInvoice:Update-btnInnerEl']");
    private static final Button approveQuoteButton = Button.xpath("//*[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:ApproveQuote']");
    private static final Button updateApproveQuoteButton = Button.xpath("//*[@id='ApproveQuotePopup:Update-btnInnerEl']");
    private static final Button updateApproveInvoiceButton = Button.xpath("//*[@id='ClaimServiceRequests:InvoicePanelSet:ApproveInvoice']");
    private static final Tab invoiceTabTxt = Tab.xpath("//*[@id='ClaimServiceRequests:InvoicesTab-btnInnerEl']");
    private static final Dropdown nameCheckScreenDdTxt = Dropdown.xpath("//*[@id='QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:PrimaryPayee_Name-inputEl']");
    private static final Label chkAmtNotEqualMsgTxt = Label.xpath("//*[@id='WebMessageWorksheet:WebMessageWorksheetScreen:grpMsgs']/div[contains(text(),'Check Amount should be equal to Amount to Pay Repairer')]");
    private static final Button clearCheckScreenButton = Button.xpath("//*[@id='WebMessageWorksheet:WebMessageWorksheetScreen:WebMessageWorksheet_ClearButton-btnInnerEl']");
    private static final Label paymentStatusTxt = Label.xpath("*//div[contains(text(),'Pending approval')]");
    private static final Button payInvoiceButton = Button.xpath("//*[@id='ClaimServiceRequests:InvoicePanelSet:PayInvoice']");
    private static final Button payFinishButton = Button.xpath("//*[@id='QuickCreateCheckWizard:Finish-btnInnerEl']");
    private static final Label vat = Label.xpath("//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:latestQuoteSummary_PSA:QuoteCalculationSummary_PSAInputSet:grantedAmountVATValue_PSA-inputEl']");
    private static final Label claimStateLbl = Label.xpath("//span[contains(@id,'Claim:ClaimInfoBar:State-btnInnerEl')]/span[2]");
    private static final Label productNameLbl = Label.xpath("//span[contains(@id,'Claim:ClaimInfoBar:Product-btnInnerEl')]/span[2]");
    private static final Link claimPolicy = Link.xpath("//*[@id='Claim:MenuLinks:Claim_ClaimPolicyGroup']/div/span");
    private static final Label managementTypeLbl = Label.xpath("//*[@id='ClaimPolicyGeneral:ClaimPolicyGeneralScreen:PolicyGeneralPanelSet:PolicyGeneralDV:ManagementType_PSA-inputEl']");
    private static final Title claimPolicyHeading = Title.id("ClaimPolicyGeneral:ClaimPolicyGeneralScreen:ttlBar");
    private static final Label timeProcessTxt = Label.xpath("//td//div[text()='Time To Process The Quote']//parent::td//following-sibling::td//div[1]");
    private static final Dropdown nameCheckScreenTxt = Dropdown.xpath("//input[@id='QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:PrimaryPayee_Name-inputEl']");
    private static final Link payAmountTxt = Link.xpath("//div[@class='x-grid-view x-grid-with-col-lines x-grid-with-row-lines x-fit-item x-grid-view-default']//table[@class='x-grid-item']//tbody//tr[@class='  x-grid-row']//td[3]//div");
    private static final Button payNextButton = Button.xpath("//*[@id='QuickCreateCheckWizard:Next-btnInnerEl']");
    private static final Button othersButton = Button.xpath("//*[@id='Claim:ClaimMenuActions:ClaimMenuActions_NewTransaction:ClaimMenuActions_NewOtherTrans-textEl']");
    private static final Button recoveriesButton = Button.xpath("//*[@id='Claim:ClaimMenuActions:ClaimMenuActions_NewTransaction:ClaimMenuActions_NewOtherTrans:ClaimMenuActions_NewTransaction_RecoverySet-textEl']");
    private static final Label recoveredAmountLabel = Label.xpath("//*[@id='ClaimSummary:ClaimSummaryScreen:ClaimSummaryHeadlinePanelSet:Recovered-inputEl']");
    private static final Button editRecoveryButton = Button.xpath("//*[@id='NewRecoverySet:NewRecoveryScreen:Edit-btnInnerEl']");
    private static final Button updateRecoveryButton = Button.xpath("//*[@id='NewRecoverySet:NewRecoveryScreen:Update-btnInnerEl']");
    private static final Button summaryButton = Button.xpath("(//*[text()='Summary'])[1]");
    private static final Label paidAmountLabel = Label.xpath("//*[@id='ClaimSummary:ClaimSummaryScreen:ClaimSummaryHeadlinePanelSet:Paid-inputEl']");
    private static final Label diffLbl = Label.xpath("//span[@id='Claim:ClaimInfoBar:DiffInDays-btnInnerEl']/span[2]");
    private static final Label dateOfLoss = Label.xpath("//div[@id='ClaimLossDetails:ClaimLossDetailsScreen:LossDetailsPanelSet:LossDetailsCardCV:LossDetailsDV:LossDate-inputEl']");
    private static final Label policyEffectDate = Label.id("ClaimPolicyGeneral:ClaimPolicyGeneralScreen:PolicyGeneralPanelSet:PolicyGeneralDV:EffectiveDate-inputEl");
    private static final Button lossDetailsButton = Button.xpath("//span[contains(@class,'x-tree-node-text') and text()='Loss Details']");
    private static final Button policyButton = Button.xpath("//span[contains(@class, 'x-tree-node-text ') and text()='Policy']");
    private static final Button reserveButton = Button.xpath("//a[@id='Claim:ClaimMenuActions:ClaimMenuActions_NewTransaction:ClaimMenuActions_NewTransaction_ReserveSet-itemEl']");
    private static final Button reopenClaimButton = Button.id("Claim:ClaimMenuActions:ClaimFileMenuItemSet:ClaimMenuActions_ClaimActions:ClaimMenuActions_ReopenClaim-textEl");
    private static final Button reopenClaimScreenButton = Button.id("ReopenClaimPopup:ReopenClaimScreen:Update-btnInnerEl");
    private static final Button exposureButton = Button.xpath("//table[contains(@id,'treeview')]/tbody/tr/td[contains(@id,'ClaimExposures')]");
    private static final CheckBox exposureCheckbox = CheckBox.xpath("//table[contains(@id, 'gridview')]/tbody/tr/td/div/img");
    private static final Button closeExposureButton = Button.id("ClaimExposures:ClaimExposuresScreen:ClaimExposures_CloseExposure-btnInnerEl");
    private static final Button closeExposureScreenButton = Button.id("CloseExposurePopup:CloseExposureScreen:Update-btnInnerEl");
    private static final Button closeClaimButton = Button.xpath("//span[text()='Close Claim']");
    private static final Dropdown outcomeCloseClaim = Dropdown.id("CloseClaimPopup:CloseClaimScreen:CloseClaimInfoDV:Outcome-inputEl");
    private static final Button closeClaimScreenButton = Button.id("CloseClaimPopup:CloseClaimScreen:Update-btnInnerEl");
    private static final Label distanceLbl = Label.xpath("//span[contains(@id,'Claim:ClaimInfoBar:DiffInDistance-btnInnerEl')]/span[2]");
    private static final Button vehiclesButton = Button.xpath("//span[@class = 'x-tree-node-text ' and text() = 'Vehicles']");
    private static final Label vehicleMileageLoss = Label.xpath("//div[@id='ClaimLossDetails:ClaimLossDetailsScreen:LossDetailsPanelSet:LossDetailsCardCV:LossDetailsDV:MileageAtDateOfLoss_PSA-inputEl']");
    private static final Label vehiclesMileagePol = Label.id("ClaimPolicyVehicles:ClaimPolicyVehiclesScreen:PolicyVehicleDetailPanelSet:PolicyVehicleDetailDV:VehicleMIleage_PSA-inputEl");
    private static final Button documentsButton = Button.xpath("//span[text()='Documents']");
    private static final Button quoteDocument = Button.xpath("//*[contains(text(),'CLAIMS_VALIDATED_QUOTE')]");
    private static final Items partRows = Items.xpath("//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:GTMotivePart_PSALV-body']//table[not(contains(@class,'summary'))]");
    private static final Label serviceBoxPrice = Label.xpath("((//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:0-body']//table)[4]//td)[2]/div");
    private static final Label platformPrice = Label.xpath("((//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:0-body']//table)[4]//td)[3]/div");
    private static final Items operationRows = Items.xpath("//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:GTMotiveOperation_PSALV-body']//table[not(contains(@class,'summary'))]");
    private static final Label gtMotiveWarrantyCheckLbl = Label.id("ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:gtMotiveWarrantyCheck-labelEl");
    private static final Label previousSameVINLbl = Label.id("ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:previousWarrantyClaim-labelEl");
    private static final Button payeeMenuIcon = Button.id("QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:PrimaryPayee_Name:PrimaryPayee_NameMenuIcon");
    private static final Button payeeSearchOption = Button.id("QuickCreateCheckWizard:QuickCheckWizard_QuickCheckBasicsScreen:QuickCheckBasicsDV:PrimaryPayee_Name:MenuItem_Search-textEl");
    private static final Title addressBookTitle = Title.id("AddressBookPickerPopup:AddressBookSearchScreen:ttlBar");
    private static final TextBox abCompanyNameTextbox = TextBox.xpath("//input[@id='AddressBookPickerPopup:AddressBookSearchScreen:AddressBookSearchDV:NameInputSet:GlobalContactNameInputSet:Name-inputEl']");
    private static final Button abSearchButton = Button.id("AddressBookPickerPopup:AddressBookSearchScreen:AddressBookSearchDV:SearchAndResetInputSet:SearchLinksInputSet:Search");
    private static final Button abFirstResultSelectButton = Button.id("AddressBookPickerPopup:AddressBookSearchScreen:AddressBookSearchLV:0:_Select");
    private static final Link abSearchResultName = Link.id("AddressBookPickerPopup:AddressBookSearchScreen:AddressBookSearchLV:0:DisplayName");

    public void createClaim() {
        logger.info("Claim creation with " + accountType + " contact starts");
        waitForElementVisible(step1TitleTxt);
        setClaimStepOne();
        viewCreatedClaim();
    }

    private void setClaimStepOne() {
        logger.info("Set the step one in new claim");
        sendText(stp1PolNumTxtBx, dataObject.getPolicyNumber());
        click(stp1SrchPolButton);
        waitForElementVisible(newClaimTxt);
        waitForTextToBe(stp1SrchpPolResTxt, dataObject.getPolicyNumber());
        logger.info("Policy " + getText(stp1SrchpPolResTxt) + " is found in Claim Center");
        addStepInfoToReport("Search policy " + dataObject.getPolicyNumber() + " in Claim Center");
        click(nextButton);
        handleClaimLossDateError();
        createContact();
    }

    public void createClaimWithInconsistencyMileage() {
        logger.info("Create claim with vehicle mileage on the new claim <= Primary mileage");
        waitForElementVisible(step1TitleTxt);
        setClaimStepOne();
        String warningMsgValue = getText(warningMsg);
        logger.info(warningMsgValue);
        assert warningMsgValue.equalsIgnoreCase("Warning: Inconsistency Mileage");
        click(closeButton);
        logger.info("Clicked on Close button.");
        click(finishButton);
        waitForElementVisible(newClaimSavedTxt);

        viewCreatedClaim();
    }

    private void handleClaimLossDateError() {
        try {
            if (isElementDisplayed(claimLossDateError, 5)) {
                click(nextButton);
            }
        } catch (NoSuchElementException e) {
            logger.info("Claim loss date is not displayed, continue");
        }
    }

    private void createContact() {
        logger.info("Create a contact from Quick Claim Auto screen");
        waitForElementVisible(quickClaimAutoHeadingTxt);
        if (accountType.equals(Person)) {
            createPersonContact();
        } else {
            createCompanyContact();
        }
        fillLossDetails();
    }

    private void fillLossDetails() {
        logger.info("Fill loss details");
        sendText(vehicleMileageTxt, dataObject.getClaimCenter().getVehicleMileage());
        setDropDownRandomValue(causeDdTxt);
        setDropDownRandomValue(causeSubgroupDdTxt);
        setDropDownRandomValue(defaultDdTxt);
        setDropDownRandomValue(defaultSubgroupDdTxt);
        addStepInfoToReport("Filled all claim loss details");

        click(finishButton);
        handleDuplicateClaim();
    }

    private void createPersonContact() {
        click(nameDdIcon);
        waitForElementVisible(newPersonButtonTxt);
        hoverAndClick(newPersonButtonTxt);
        waitForElementVisible(newContactHeadingTxt);
        setDropDownRandomValue(prefixDd);
        String firstName = faker.name().firstName().replace("'", "");
        String lastName = faker.name().lastName().replace("'", "");

        sendText(ccFirstNameTxt, firstName);
        sendText(ccLastNameTxt, lastName);
        dataObject.getClaimCenter().setFirstName(firstName);
        dataObject.getClaimCenter().setLastName(lastName);
        //selecting tags among Employee or Policy Person
        if (dataObject.getClaimCenter().getTags().equalsIgnoreCase("Employee")) {
            click(tagsEmployeeTxt);
        } else if (dataObject.getClaimCenter().getTags().equalsIgnoreCase("Policy Person")) {
            click(tagsPolicyPersonTxt);
        }
        clickPageBody();
        setDropDown(cmpCountryDdTxt, dataObject.getCountry());
        sendText(ccEmailTxt, firstName.toLowerCase() + lastName.toLowerCase() + "@email.com");
        sendText(ccAddressTxt, faker.address().streetAddress());
        sendText(ccPostalCodeTxt, rand.nextInt(10000, 99999));
        sendText(ccCityTxt, faker.address().city());
        addStepInfoToReport("Filled out all person details");
        click(ccUpdateButton);
        try {
            if (isElementDisplayed(dupContactMsg, 5)) {
                logger.info("Duplicate Claim has found and being handled");
                click(selectLink);
            }
        } catch (Exception e) {
            logger.info("No duplicate claims");
        }

        waitForElementVisible(quickClaimAutoHeadingTxt);
        String fullName = getAttributeValue(nameQuickClaimDdTxt, "value");
        logger.info("Full name of Claim Contact is: " + fullName); //Name is being displayed in Last Name First Name format
        dataObject.getClaimCenter().setFullName(fullName);
        waitForElementVisible(quickClaimAutoHeadingTxt);
        addStepInfoToReport("Created Person contact");
    }

    private void createCompanyContact() {
        click(nameDdIcon);
        waitForElementVisible(newCompanyTxt);
        hoverAndClick(newCompanyTxt);
        waitForElementVisible(newContactHeadingTxt);
        String cmpName = faker.company().name();
        logger.info("Company contact name in Claim Center is " + cmpName);
        sendText(companyNameTxt, cmpName);
        dataObject.getClaimCenter().setCompanyName(cmpName);
        setDropDown(cmpCountryDdTxt, dataObject.getCountry());
        click(tagsJV);
        sendText(ccAddressTxt, faker.address().streetAddress());
        sendText(ccPostalCodeTxt, rand.nextInt(10000, 99999));
        sendText(ccCityTxt, faker.address().city());
        click(personVendorDdIcon);
        waitForElementVisible(newPersonVendorTxt);
        click(newPersonVendorTxt);
        addContactInfoForCompany();
        click(ccUpdateButton);
        waitForElementVisible(quickClaimAutoHeadingTxt);
        sendText(address2Txt, faker.address().streetAddress());
        String fullName = getAttributeValue(nameQuickClaimDdTxt, "value");
        logger.info("Full name of claim contact: " + fullName);
        dataObject.getClaimCenter().setFullName(fullName);
        addStepInfoToReport("Created Company contact");
    }

    private void viewCreatedClaim() {
        String claimNoLine = getText(claimNoStmtTxt);
        logger.info("Claim no line is " + claimNoLine);
        String[] splitClaim = claimNoLine.split(" ");
        logger.info("The Claim number generated is: " + splitClaim[1]);
        dataObject.getClaimCenter().setClaimNo(splitClaim[1]);
        waitForPageLoadComplete(2000);
        waitForElementVisible(viewNewClaimLink);
        click(viewNewClaimLink);
        waitForElementVisible(claimSumTxt);
        clickMenu("TabBar:ClaimTab");

        String claimNoFile = dataObject.getClaimCenter().getClaimNo();
        Link existingClaimNoLink = Link.xpath("//span[contains(text(), '" + claimNoFile + "')]");
        click(existingClaimNoLink);
        addStepInfoToReport("Claim number " + splitClaim[1] + " is created");
    }

    private void addContactInfoForCompany() {
        logger.info("Add contact information for company contact in Contact Manager");
        setDropDownRandomValue(prefixDd);
        String firstName = faker.name().firstName().replace("'", "");
        sendText(ccFirstNameTxt, firstName);
        dataObject.getContactManager().setFirstName(firstName);
        String lastName = faker.name().lastName().replace("'", "");
        sendText(ccLastNameTxt, lastName);
        dataObject.getContactManager().setLastName(lastName);

        if (dataObject.getContactManager().getTags().equalsIgnoreCase("Policy Person")) {
            click(tagsPolicyPersonTxt);
        } else if (dataObject.getContactManager().getTags().equalsIgnoreCase("Employee")) {
            click(tagsEmployeeTxt);
        }
        clickPageBody();
        sendText(ccEmailTxt, firstName.toLowerCase() + lastName.toLowerCase() + "@email.com");
        setDropDownRandomValue(primaryAddressCountry);
        sendText(primaryAddress1, faker.address().streetAddress());
        addStepInfoToReport("Filled all mandatory fields of contact information for company");
        click(ccUpdateButton);
        try {
            click(selectLink);
        } catch (NoSuchElementException ignore) {
        }
    }

    private void handleDuplicateClaim() {
        try {
            if (isElementDisplayed(duplicateClaimTable, 5)) {
                click(finishButton);
            }
        } catch (NoSuchElementException e) {
            logger.info("No duplicate claims");
        }
    }

    public boolean isClaimCreated() {
        waitForElement(claimSumTxt);
        boolean result = isElementDisplayed(claimSumTxt);
        if (result) {
            logger.info("Claim creation completed");
        }
        return result;
    }

    private void createCompanyContact(String companyName) {
        click(nameDdIcon);
        waitForElementVisible(newCompanyTxt);
        hoverAndClick(newCompanyTxt);
        waitForElementVisible(newContactHeadingTxt);
        logger.info("Company contact name in Claim Center is " + companyName);
        sendText(companyNameTxt, companyName);
        dataObject.getClaimCenter().setCompanyName(companyName);
        setDropDownRandomValue(cmpCountryDdTxt);
        click(tagsJV);
        clickPageBody();
        sendText(ccAddressTxt, faker.address().streetAddress());
        sendText(ccPostalCodeTxt, rand.nextInt(10000, 99999));
        sendText(ccCityTxt, faker.address().city());
        click(personVendorDdIcon);
        click(newPersonVendorTxt);
        clickPageBody();
        addContactInfoForCompany();
        click(ccUpdateButton);
        waitForElementVisible(quickClaimAutoHeadingTxt);
        sendText(address2Txt, faker.address().streetAddress());
        String fullName = getAttributeValue(nameQuickClaimDdTxt, "value");
        logger.info("Full name of claim contact: " + fullName);
        dataObject.getClaimCenter().setFullName(fullName);
    }

    public void addMobilityCoverService() {
        logger.info("New Service creation starts");
        click(actionsButton);
        click(serviceRequest);
        click(serviceCheckbox);
        click(removeButton);
        click(addButton);
        click(mcCheckbox);
        click(addButton);
        logger.info("Mobility Cover Service is added");
        click(serviceTypePicker);
        sendKeysByAction(ARROW_DOWN);
        sendKeysByAction(ARROW_DOWN);
        sendKeysByAction(ENTER);
        clickPageBody();
        click(vendorName);
        sendKeysByAction(ARROW_DOWN);
        sendKeysByAction(ENTER);
        clickPageBody();
        click(submitButton);
        logger.info("New Service is created");
        addStepInfoToReport("New Service is created");
    }

    public boolean isSelfManageErrorDisplayed() {
        logger.info("Get self manage error message");
        waitForElementVisible(step1TitleTxt);
        sendText(stp1PolNumTxtBx, dataObject.getPolicyNumber());
        click(stp1SrchPolButton);
        String errorMsgValue = getText(errorMessage);
        logger.info("When searched for Self-Managed policies in Claim Center then we get error message as " + errorMsgValue);
        addStepInfoToReport("When searched for Self-Managed policies in Claim Center then we get error message as " + errorMsgValue);
        return isElementDisplayed(errorMessage);
    }

    public void createPayment() {
        logger.info("Add quote");
        click(servicesLink);
        click(addQuoteButton);
        waitForElementVisible(updateQuoteButton);
        sendText(refNoQuoteTxt, dataObject.getClaimCenter().getQuoteRef());
        sendText(quoteCommentsTxt, "Adding the Quote");
        click(addLineItemsButton);
        waitForElementVisible(gtMotiveBody);
        click(platformAmount);
        sendKeysByAction(dataObject.getClaimCenter().getQuoteAmount1());
        clickPageBody();
        click(updateQuoteButton);
        click(approveQuoteButton);
        click(updateApproveQuoteButton);
        logger.info("Quote is added");

        logger.info("Add invoice");
        waitForElementVisible(addInvoiceButton);
        click(addInvoiceButton);
        waitForElementVisible(invoiceRefNoTxt);
        sendText(invoiceRefNoTxt, dataObject.getClaimCenter().getInvoiceRef());
        String todayDate = DateHelper.getSimpleToday();
        sendText(invoiceDateTxt, todayDate);
        sendText(invoiceCommentTxt, "Adding the Invoice");
        click(quoteCategory);
        sendKeysByAction(ARROW_DOWN);
        sendKeysByAction(ENTER);
        click(invoiceAmount);
        sendKeysByAction(dataObject.getClaimCenter().getInvoiceAmount1());
        clickPageBody();
        click(updateInvoiceButton);
        click(workPlanButton);
        click(selectWorkPlanCheckbox);
        click(approveWorkPlanButton);
        click(servicesLink);
        waitForElementVisible(servicesDetailTable);
        click(invoiceTabTxt);
        click(updateApproveInvoiceButton);
        click(payInvoiceButton);

        setDropDown(nameCheckScreenDdTxt, dataObject.getClaimCenter().getCompanyName());
        setDropDown(typeCheckScreenDdTxt, dataObject.getClaimCenter().getTypeCheck());
        String amountPayRepairerUI = getText(amtPayRepairerTxt);//580,00$
        logger.info("Amount Repairer Value from UI without formatting " + amountPayRepairerUI);
        String[] amountRepairWithoutEuro = amountPayRepairerUI.split(" ");//"€"
        String amountRepairWithComma = amountRepairWithoutEuro[0];//580,00
        logger.info("Amount to Pay Repairer with comma is " + amountRepairWithComma);
        sendText(repairerCategoryTxt, amountRepairWithComma);
        // comparison of Available reserves with the Amount to Repairer is done below to validate and solve error messages
        String availableResourcesWithEuro = getText(availableResourcesTxt);
        String[] availableResourcesWithoutEuro = availableResourcesWithEuro.split(" ");//"€"
        String availableResourcesWithComma = availableResourcesWithoutEuro[0];//580,00
        logger.info("Available Resources with comma is " + availableResourcesWithComma);
        //replacing , with decimal for comparison
        String amountRepairWithDecimal = amountRepairWithComma.replace(',', '.');//580.00
        String availableResourcesWithDecimal = availableResourcesWithComma.replace(',', '.');//580.00
        //converting string to float
        float amountRepairerFloat = Float.parseFloat(amountRepairWithDecimal); //580.00
        logger.info("Amount Repairer Value in float " + amountRepairerFloat);
        float amtRepairerUpdatedFloat = amountRepairerFloat + 10;//this variable will be used to update the reserve value in financial Summary by 10
        String amtRepairerUpdatedString = String.valueOf(amtRepairerUpdatedFloat);

        float availableResourcesFloat = Float.parseFloat(availableResourcesWithDecimal);
        logger.info("Available Resources in float is " + availableResourcesFloat);
        click(repairerCategoryTxt);
        sendKeysByAction(amountRepairWithComma);
        if (availableResourcesFloat < amountRepairerFloat) {
            click(finishCheckButton);
            waitForElementVisible(availableResourcesErrorTxt);

            String errorMessageUI = getText(availableResourcesErrorTxt);
            String errorMessageExpected = "Line Items : This payment cannot be added because the sum of its line items' amounts exceeds the available reserves for its ReserveLine.";
            assert errorMessageUI.equalsIgnoreCase(errorMessageExpected);
            click(cancelCheckButton);
            switchToActiveElement();
            click(okPopUpButton);

            //continue from edit reserve option
            click(financialSummaryTxt);
            waitForElementVisible(imageIconImg);
            click(editReserveButton);
            sendText(newAvailReserveTxt, amtRepairerUpdatedString);
            click(saveReserveButton);
            click(servicesLink);
            waitForElementVisible(servicesHeadingTxt);
            waitForElementVisible(servicesDetailTable);
            click(invoiceTabTxt);
            click(payInvoiceButton);
            click(repairerCategoryTxt);
            sendKeysByAction(amountRepairWithComma);
            click(payFinishButton);
            waitForElementVisible(chkAmtNotEqualMsgTxt);
            click(clearCheckScreenButton);
            sendText(repairerCategoryTxt, amountRepairWithComma);
        }

        click(payFinishButton);
        waitForElementVisible(paymentStatusTxt);
        logger.info("Claim creation completed");
    }

    public boolean isPaymentCreated() {
        waitForElementVisible(paymentStatusTxt);
        boolean result = isElementDisplayed(paymentStatusTxt);
        if (result) {
            logger.info("Claim payment creation completed");
        }
        return result;
    }

    public void goToQuoteScreen() {
        click(servicesLink);
        waitForElementVisible(servicesDetailTable);
        click(addQuoteButton);
        waitForElementVisible(updateQuoteButton);
    }

    public void addQuote() {
        logger.info("Adding quote");
        goToQuoteScreen();
        sendText(refNoQuoteTxt, dataObject.getClaimCenter().getQuoteRef());
        sendText(quoteCommentsTxt, "Adding the Quote");
        click(addLineItemsButton);
        waitForElementVisible(gtMotiveBody);
        inputPlatformAmount(dataObject.getClaimCenter().getQuoteAmount());
        inputPlatformNumber(1);
        click(updateQuoteButton);
        click(approveQuoteButton);
        click(updateApproveQuoteButton);
        logger.info("Quote is added");
        addStepInfoToReport("Quote is added");
    }

    private void inputPlatformAmount(int amount) {
        click(platformAmount);
        sendKeysByAction(amount);
        clickPageBody();
        waitForPageLoadComplete(1000);
    }

    private void inputPlatformNumber(int value) {
        click(platformNumber);
        sendKeysByAction(value);
        clickPageBody();
        waitForPageLoadComplete(1000);
    }

    public String getVAT() {
        String vatUI = getText(vat);
        dataObject.getClaimCenter().setVatInUI(vatUI);
        logger.info("Actual VAT value is " + vatUI);
        addStepInfoToReport("Actual VAT value is " + vatUI);
        return vatUI;
    }

    public void checkStatus() {
        String status = getText(claimStateLbl);
        logger.info("Status of the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + status);
    }

    public void checkProductName() {
        String productName = getText(productNameLbl);
        logger.info("Product name for the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + productName);
    }

    public String getManagementType() {
        click(claimPolicy);
        waitForElementVisible(claimPolicyHeading);
        return getText(managementTypeLbl);
    }

    public String getTimeToProcessQuote() {
        logger.info("Get time value to process Quote");
        scrollDown();
        String timeProcess = getText(timeProcessTxt);
        logger.info("Time to process the Quote is: " + timeProcess);
        return timeProcess;
    }

    public void addInvoiceAndPay() {
        logger.info("Payment creation starts");
        click(addInvoiceButton);
        waitForElementVisible(updateInvoiceButton);
        sendText(invoiceRefNoTxt, dataObject.getClaimCenter().getInvoiceRef());
        sendText(invoiceCommentTxt, "Adding the Invoice");

        String todayDate = DateHelper.getSimpleToday();
        sendText(invoiceDateTxt, todayDate);
        click(quoteCategory);
        sendKeysByAction(ARROW_DOWN);
        sendKeysByAction(ENTER);
        click(invoiceAmount);
        sendKeysByAction(dataObject.getClaimCenter().getInvoiceAmount1());
        clickPageBody();
        click(updateInvoiceButton);

        click(invoiceTabTxt);
        click(updateApproveInvoiceButton);
        click(payInvoiceButton);
        enterCheckBasics();
        setCheckDetails();
        logger.info("Payment is created successfully");
    }

    private void enterCheckBasics() {
        logger.info("Enter check basics");
        click(payeeMenuIcon);
        click(payeeSearchOption);
        searchPayeeInAddressBook(dataObject.getCompanyName());
        setDropDown(typeCheckScreenDdTxt, "Vendor");
        click(payAmountTxt);
        sendKeysByAction(dataObject.getClaimCenter().getPaymentAmount());
        clickPageBody();
        waitForPageLoadComplete(500);
        logger.info("Payment amount is: " + dataObject.getClaimCenter().getPaymentAmount());
        addStepInfoToReport("Entered check basics");
        click(payNextButton);
    }

    private void setCheckDetails() {
        logger.info("Set check details");
        addStepInfoToReport("Set check details");
        click(payFinishButton);
    }

    private void searchPayeeInAddressBook(String name) {
        logger.info("Search for payee " + name + " in Address Book screen");
        waitForElementVisible(addressBookTitle);
        sendText(abCompanyNameTextbox, name);
        click(abSearchButton);
        waitForElementVisible(abFirstResultSelectButton);
        waitForTextToBe(abSearchResultName, name);
        addStepInfoToReport("Found the payee in search result");
        click(abFirstResultSelectButton);
    }

    public boolean addRecoveryDetail() {
        logger.info("Add recovery detail");
        click(actionsButton);
        click(othersButton);
        click(recoveriesButton);
        click(editRecoveryButton);
        click(updateRecoveryButton);
        click(summaryButton);
        String paidAmount = getText(paidAmountLabel);
        String recoveryAmount = getText(recoveredAmountLabel);
        boolean result = paidAmount.equalsIgnoreCase(recoveryAmount);
        if (result) {
            logger.info("Recovery is created successfully");
            addStepInfoToReport("Recovery is created successfully");
        }
        return result;
    }

    public String getDiffValue() {
        return getText(diffLbl);
    }

    public String getActualDiffDate() {
        click(lossDetailsButton);

        String dateOfLossValue = getText(dateOfLoss);
        logger.info("Date of Loss is : " + dateOfLossValue);
        click(policyButton);

        String policyEffectiveDateValue = getText(policyEffectDate);
        logger.info("Policy effective date is " + policyEffectiveDateValue);

        String dateDiff = DateHelper.getDiffBetweenTwoDays(policyEffectiveDateValue, dateOfLossValue);
        return dateDiff + " days";
    }

    public void reserveClaim() {
        click(actionButton);
        logger.info("Clicked on Action button");
        click(reserveButton);
        logger.info("Clicked on Reserve");

        String availableAmount = getText(By.xpath("//table[contains(@id,'gridview')]/tbody/tr/td/div/div/a"));
        logger.info("New available reserve amount is " + availableAmount);
        addStepInfoToReport("New available reserve amount is " + availableAmount);
    }

    public void reopenClaim() {
        click(actionButton);
        logger.info("Clicked on Action button");
        click(reopenClaimButton);
        click(reopenClaimScreenButton);
        logger.info("Claim is reopened");

        waitForElement(claimStateLbl);
        String claimState = getText(claimStateLbl);
        logger.info("Status of the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + claimState);
        addStepInfoToReport("Status of the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + claimState);
    }

    public void closeExposure() {
        click(exposureButton);
        logger.info("Clicked on Exposures button");
        click(exposureCheckbox);
        click(closeExposureButton);
        click(closeExposureScreenButton);
        logger.info("Exposure is closed");
        addStepInfoToReport("Exposure is closed");
    }

    public void closeClaim() {
        click(actionButton);
        logger.info("Clicked on Action button");
        click(closeClaimButton);
        setDropDown(outcomeCloseClaim, "Completed");
        click(closeClaimScreenButton);
        logger.info("Claim is closed.");

        waitForElement(claimStateLbl);
        String claimState = getText(claimStateLbl);
        logger.info("Status of the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + claimState);
        addStepInfoToReport("Status of the claim " + dataObject.getClaimCenter().getClaimNo() + " is: " + claimState);
    }

    public String getMileageDistanceValue() {
        return getText(distanceLbl);
    }

    public String getMileageDistance() {
        click(lossDetailsButton);
        logger.info("Clicked on Loss Details button.");

        String lossVehicleMileageValue = getText(vehicleMileageLoss);
        logger.info("Vehicle Mileage on Loss details is: " + lossVehicleMileageValue);
        click(policyButton);
        click(vehiclesButton);

        String vehiclesMileageValue = getText(vehiclesMileagePol);
        logger.info("Vehicle Mileage on Loss details is: " + vehiclesMileageValue);

        int mileageKMDiff = Integer.parseInt(lossVehicleMileageValue) - Integer.parseInt(vehiclesMileageValue);
        return mileageKMDiff + " km";
    }

    public void createRecovery() {
        logger.info("Recovery process starts by clicking on Services link for the already opened claim");
        click(servicesLink);
        logger.info("Clicked on Services Tab");
        click(addQuoteButton);
        waitForElementVisible(updateQuoteButton);
        sendText(refNoQuoteTxt, dataObject.getClaimCenter().getQuoteRef());
        sendText(quoteCommentsTxt, "Adding the Quote");
        click(addLineItemsButton);
        waitForElementVisible(gtMotiveBody);
        click(platformAmount);
        sendKeysByAction(dataObject.getClaimCenter().getQuoteAmount1());
        click(updateQuoteButton);
        click(approveQuoteButton);
        click(updateApproveQuoteButton);
        logger.info("Clicked on Approve quote");

        addInvoiceAndPay();
        assert addRecoveryDetail();
    }

    public boolean validateDocument() {
        logger.info("Validate Quote document");
        click(documentsButton);
        click(quoteDocument);
        logger.info("Quote document is validated");
        addStepInfoToReport("Quote document is validated");
        return true;
    }

    public void cancelService() {
        click(servicesLink);
        waitForElementVisible(servicesDetailTable);
        click(cancelServiceButton);
        waitForElementVisible(cancelReasonPrompt);
        sendText(reasonTextArea, faker.lorem().sentence());
        click(confirmCancelButton);
        waitForElementVisible(servicesDetailTable);
        addStepInfoToReport("Cancelled Claim service");
    }

    public String getProgress() {
        waitForElementVisible(claimProgressLbl);
        waitForTextNotToBe(claimProgressLbl, "");
        String claimProgressValue = getText(claimProgressLbl);
        addStepInfoToReport("Actual Claim progress value is " + claimProgressValue);
        return claimProgressValue;
    }

    public List<SBEntry> getParts() {
        List<SBEntry> parts = new ArrayList<>();
        List<WebElement> rows = findElements(partRows);
        for (int i = 1; i <= rows.size(); i++) {
            SBEntry entry = new SBEntry();
            String rowElement = "(//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:GTMotivePart_PSALV-body']//table[not(contains(@class,'summary'))])[" + i + "]";
            entry.setRef(getText(Label.xpath("(" + rowElement + "//td)[2]/div")));
            Label description = Label.xpath("(" + rowElement + "//td)[3]/div");
            entry.setDescription(getText(description));
            entry.setDescriptionStyle(getTextColor(description));
            entry.setComment(getText(Label.xpath("(" + rowElement + "//td)[4]/div")));
            entry.setQuantity(getText(Label.xpath("(" + rowElement + "//td)[5]/div")));
            entry.setUnitaryPrice(getText(Label.xpath("(" + rowElement + "//td)[6]/div")));
            entry.setPriceTTC(getText(Label.xpath("(" + rowElement + "//td)[9]/div")));
            parts.add(entry);
        }
        return parts;
    }

    public List<SBEntry> getOperations() {
        List<SBEntry> operations = new ArrayList<>();
        List<WebElement> rows = findElements(operationRows);
        for (int i = 1; i <= rows.size(); i++) {
            SBEntry entry = new SBEntry();
            String rowElement = "(//div[@id='ClaimServiceRequests:ServiceRequestPanelRow:QuotePanelSet:StatementDV:GTMotiveOperation_PSALV-body']//table[not(contains(@class,'summary'))])[" + i + "]";
            entry.setRef(getText(Label.xpath("(" + rowElement + "//td)[2]/div")));
            Label description = Label.xpath("(" + rowElement + "//td)[3]/div");
            entry.setDescription(getText(description));
            entry.setDescriptionStyle(getTextColor(description));
            entry.setComment(getText(Label.xpath("(" + rowElement + "//td)[4]/div")));
            entry.setQuantity(getText(Label.xpath("(" + rowElement + "//td)[5]/div")));
            entry.setUnitaryPrice(getText(Label.xpath("(" + rowElement + "//td)[6]/div")));
            entry.setPriceTTC(getText(Label.xpath("(" + rowElement + "//td)[9]/div")));
            operations.add(entry);
        }
        return operations;
    }

    public void goToServices() {
        logger.info("Navigate to Services tab");
        click(servicesLink);
        waitForElementVisible(servicesDetailTable);
        addStepInfoToReport("User is navigated to Services tab");
    }

    public String getServiceBoxPriceColor() {
        return getTextColor(serviceBoxPrice);
    }

    public float getServiceBoxPrice() {
        return Float.parseFloat(getText(serviceBoxPrice).replace(",", "."));
    }

    public float getGTMotivePrice() {
        return Float.parseFloat(getText(platformPrice).replace(",", "."));
    }

    public boolean isGTMotiveWarrantyCheckDisplay() {
        return isElementDisplayed(gtMotiveWarrantyCheckLbl);
    }

    public boolean isPreviousWarrantyClaimOnSameVINDisplay() {
        return isElementDisplayed(previousSameVINLbl);
    }
}