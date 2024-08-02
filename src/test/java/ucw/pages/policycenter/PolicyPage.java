package ucw.pages.policycenter;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ucw.elements.*;
import ucw.enums.Makes;
import ucw.models.rating.Country;
import ucw.models.rating.RateTable;
import ucw.models.rating.Rating;
import ucw.models.testdata.ComplaintsActivity;
import ucw.utils.DateHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ucw.enums.BillingMethods.LISTBILL;
import static ucw.enums.Countries.Germany;
import static ucw.enums.CoverageTerms.ClientRelationServicePremium;
import static ucw.enums.CoverageTerms.ClientRelationServiceTaxes;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.*;
import static ucw.enums.RefundMethods.FULL_REIMBURSEMENT;
import static ucw.enums.RefundMethods.NO_REIMBURSEMENT;

public class PolicyPage extends PolicyCenter {
    private static final Logger logger = LogManager.getLogger(PolicyPage.class);
    public static WorksheetPage worksheetPage;
    private static final Label immobilizationTimeLbl = Label.id("StartPolicyChange:StartPolicyChangeScreen:StartPolicyChangeDV:immobilizationDays-labelEl");
    private static final TextBox immobilizationTimeTxt = TextBox.xpath("//*[@id='StartPolicyChange:StartPolicyChangeScreen:StartPolicyChangeDV:immobilizationDays-inputEl']");
    private static final TextBox assistanceExpDateTxt = TextBox.xpath("//div[contains(text(),'Assistance')]/../../td[3]/div");
    private static final TextBox warrantyExpDateTxt = TextBox.xpath("//div[contains(text(),'Warranty')]/../../td[3]/div");
    private static final Button changePolButton = Button.xpath("//*[@id='PolicyFile:PolicyFileMenuActions:PolicyFileMenuActions_NewWorkOrder:PolicyFileMenuActions_ChangePolicy-textEl']");
    private static final Dropdown reasonDdTxt = Dropdown.xpath("//*[@id='StartPolicyChange:StartPolicyChangeScreen:StartPolicyChangeDV:Reason-inputEl']");
    private static final Dropdown reasonCancelDdTxt = Dropdown.xpath("//*[@id='StartCancellation:StartCancellationScreen:CancelPolicyDV:Reason-inputEl']");
    private static final TextBox effectiveDateTxt = TextBox.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:PolicyChangeWizard_PolicyInfoScreen:PolicyChangeWizard_PolicyInfoDV:PolicyInfoInputSet:EffectiveDate-inputEl']");
    private static final TextBox writtenDateTxt = TextBox.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:PolicyChangeWizard_PolicyInfoScreen:PolicyChangeWizard_PolicyInfoDV:PolicyInfoInputSet:WrittenDate-inputEl']");
    private static final Label pcChangePopUpMsgTxt = Label.xpath("//div[contains(text(),'Do you want to issue this policy now?')]");
    private static final Button quoteInPolicyChangeTxt = Button.xpath("//*[@id='PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:JobWizardToolbarButtonSet:QuoteOrReview-btnInnerEl']");
    private static final Button nextButton = Button.xpath("//*[@id='StartPolicyChange:StartPolicyChangeScreen:NewPolicyChange-btnInnerEl']");
    private static final Button wizardNextButton = Button.id("PolicyChangeWizard:Next-btnInnerEl");
    private static final Form policySummaryScreen = Form.id("PolicyFile_Summary:Policy_Summary_PSAScreen:0");
    private static final Label changeTypeInTranscTableTxt = Label.xpath("//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_TransactionsLV-body']//div[contains(text(),'Policy Change')]/../../td[5]/div");
    private static final Label changeTransactionNumTxt = Label.xpath("//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_TransactionsLV-body']//div[contains(text(),'Policy Change')]/../../td[6]//a");
    private static final TextBox vinTxt = TextBox.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Vin_DV-inputEl']");
    private static final TextBox firstRegDateDatePicker = TextBox.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:FirstRegistrationDate-inputEl']");
    private static final Dropdown typeOfEnergyChange = Dropdown.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:TypeOfEnergy-inputEl']");
    private static final Dropdown typeOfEnergyDdTxt = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:TypeOfEnergy-inputEl']");
    private static final TextBox currentMileageTxt = TextBox.xpath("//*[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:CurrentMileage_DV-inputEl']");
    private static final Button otherCoverageButton = Button.id("PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OtherOtherCoverageCardTab-btnInnerEl");
    private static final Dropdown updatedCoverageTypeDd = Dropdown.xpath("//input[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:flavourWM2ID-inputEl']");
    private static final Button changeTreeImg = Button.xpath("//div[@id='PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:DifferencesPanelSet:DiffTreePanelSet:DiffTreePanelLV-body']//table[2]//img[2]");
    private static final Label vinUpdatedTxt = Label.xpath("//div[@id='PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:PolicyLineSummaryPanelSet:0:Vin-inputEl']");
    private static final Button issueInPolChangeButton = Button.xpath("//*[@id='PolicyChangeWizard:PolicyChangeWizard_QuoteScreen:JobWizardToolbarButtonSet:BindPolicyChange-btnInnerEl']");
    private static final Label mileageUpdatedTxt = Label.xpath("//span[contains(text(),'Current Mileage')]/../../../td[3]/div");
    private static final Label firstRegDateUpdatedTxt = Label.xpath("//span[contains(text(),'First Registration Date')]/../../../td[3]/div");
    private static final Label typeOfEnergyUpdatedTxt = Label.xpath("//span[contains(text(),'Type Of Energy')]/../../../td[3]/div");
    private static final Label coverageTypeUpdatedTxt = Label.xpath("//span[contains(text(),'Virtual Product Flavour')]/../../../td[3]/div");
    private static final Title quoteScreen = Title.id("PolicyChangeWizard:PolicyChangeWizard_QuoteScreen:ttlBar");
    private static final Title cancelConfirmationTitle = Title.id("CancellationWizard:CancellationWizard_QuoteScreen:ttlBar");
    private static final Label polChangeMsgTxt = Label.xpath("//*[@id='JobComplete:JobCompleteScreen:Message']");
    private static final Link jobNumber = Link.xpath("//div/a[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_TransactionsLV:0:JobNumber']");
    private static final Label changeCost = Label.xpath("//*[@id='PolicyChangeWizard:PolicyChangeWizard_QuoteScreen:Quote_Summary_PSADV:ChangeInCost-inputEl']");
    private static final Tab differencesTab = Tab.id("PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:DiffsTab-btnInnerEl");
    private static final Button expandDiffButton = Button.xpath("(//span[text()='PUW Vehicle']/preceding-sibling::img)[2]");
    private static final Button expandCoveragesButton = Button.xpath("(//span[text()='Coverages']/preceding-sibling::img)[3]");
    private static final Tab policyReviewTab = Tab.id("PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:PolicyReviewTab-btnInnerEl");
    private static final Form policyReviewSumPanel = Form.id("PolicyChangeWizard:PolicyChangeWizard_DifferencesScreen:PolicyLineSummaryPanelSet:0:0_ref-tbody");
    private static final Button newSubVinSearchIcon = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Vin_DV:VinSearch");
    private static final Button vinSearchIcon = Button.id("PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Vin_DV:VinSearch");
    private static final Button popupCancelButton = Button.xpath("//span[@class='x-btn-button x-btn-button-default-small x-btn-text    x-btn-button-center ']//span[text()='Cancel']");
    private static final Label vehicleErrorMessage = Label.xpath("//div[@class='message']");
    private static final Button cancelNowDdBtn = Button.xpath("//span[@id='CancellationWizard:CancellationWizard_QuoteScreen:JobWizardToolbarButtonSet:BindOptions:CancelNow-textEl']");
    private static final Label cancellationMessageTxt = Label.xpath("//*[@id='JobComplete:JobCompleteScreen:Message']");
    private static final Button cancelPolButton = Button.xpath("//*[@id='PolicyFile:PolicyFileMenuActions:PolicyFileMenuActions_NewWorkOrder:PolicyFileMenuActions_CancelPolicy-textEl']");
    private static final Dropdown sourceDdTxt = Dropdown.xpath("//*[@id='StartCancellation:StartCancellationScreen:CancelPolicyDV:Source-inputEl']");
    private static final Label refundMethodTxt = Label.xpath("//div[@id='StartCancellation:StartCancellationScreen:CancelPolicyDV:CalcMethod-inputEl']");
    private static final Button startCancellationButton = Button.xpath("//*[@id='StartCancellation:StartCancellationScreen:NewCancellation-btnInnerEl']");
    private static final Label cancelEffectiveDateTxt = Label.xpath("//*[@id='StartCancellation:StartCancellationScreen:CancelPolicyDV:CancelDate_date-inputEl']");
    private static final Button bindOptionsButton = Button.xpath("//*[@id='CancellationWizard:CancellationWizard_QuoteScreen:JobWizardToolbarButtonSet:BindOptions-btnInnerEl']");
    private static final Label cancelMsgTxt = Label.xpath("//*[@id='PolicyFile:PolicyFileMenuInfoBar:StatusAndExpDate-btnInnerEl']//span[contains(text(),Canceled)]");
    private static final Label cancelTransactionNumTxt = Label.xpath("//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_TransactionsLV-body']//div[contains(text(),'Cancellation')]/../../td[6]//a");
    private static final Button cancelButton = Button.xpath("//*[@id='StartCancellation:StartCancellationScreen:Cancel-btnInnerEl']");
    private static final Label changeInCostTxt = Label.xpath("//*[@id='CancellationWizard:CancellationWizard_QuoteScreen:Quote_Summary_PSADV:ChangeInCost-inputEl']");
    private static final Label changeInCostValueTxt = Label.xpath("//*[@id='CancellationWizard:CancellationWizard_QuoteScreen:Quote_Summary_PSADV:ChangeInCost-bodyEl']/div");
    private static final Label transcEffDateTxt = Label.xpath("//*[@id='CancellationWizard:CancellationWizard_QuoteScreen:Quote_Summary_PSADV:transaction-inputEl']");
    private static final Title startCancellationTxt = Title.xpath("//span[contains(text(),'Start Cancellation For Policy')]");
    private static final Label jobCompleteLbl = Label.id("JobComplete:JobWizardInfoBar:JobLabel");
    private static final Label accNoUITxt = Label.xpath("//*[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_AccountDV:Number-inputEl']");
    private static final Label type = Label.id("PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_AssocJobDV:Type-inputEl");
    private static final Button policyReInstatementButton = Button.xpath("//*[@id='PolicyFile:PolicyFileMenuActions:PolicyFileMenuActions_NewWorkOrder:PolicyFileMenuActions_ReinstatePolicy-textEl']");
    private static final TextBox reintReasonDescTxt = TextBox.xpath("//*[@id='ReinstatementWizard:ReinstatementWizard_ReinstatePolicyScreen:ReinstatePolicyDV:ReasonDescription-inputEl']");
    private static final Button reintQuoteButton = Button.xpath("//*[@id='ReinstatementWizard:ReinstatementWizard_ReinstatePolicyScreen:JobWizardToolbarButtonSet:QuoteOrReview-btnInnerEl']");
    private static final Button reinstatementButton = Button.xpath("//*[@id='ReinstatementWizard:ReinstatementWizard_QuoteScreen:JobWizardToolbarButtonSet:Reinstate-btnInnerEl']");
    private static final Label inForceMsgTxt = Label.xpath("//*[@id='PolicyFile:PolicyFileMenuInfoBar:StatusAndExpDate-btnInnerEl']//span[contains(text(),'In Force ')]");
    private static final Label reinTransactionNumTxt = Label.xpath("//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_TransactionsLV-body']//div[contains(text(),'Reinstatement')]/../../td[6]//a");
    private static final Label reinPopUpMsgTxt = Label.xpath("//div[contains(text(),'Are you sure you want to reinstate this policy?')]");
    private static final Label reinEffectiveDateTxt = Label.xpath("//*[@id='ReinstatementWizard:ReinstatementWizard_ReinstatePolicyScreen:ReinstatePolicyDV:ReinstatementDate_date-inputEl']");
    private static final Label reinstateMessage = Label.xpath("//*[@id='JobComplete:JobCompleteScreen:Message']");
    private static final Label marketingFeeTxt = Label.xpath("(//div[text()='Marketing Fee']/../following-sibling::td/div)[3]");
    private static final Label vehicleInfoSummaryMake = Label.xpath("(//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:PUWVehicleDetails:PUWVehicle_Details_PSALV-body']//tr/td)[3]/div");
    private static final Form warrantyField = Form.xpath("//fieldset[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup']");
    private static final Label duration = Label.xpath("//span[text()='Duration (Months)']/../following-sibling::div/div | //input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:intRangeTermRangeInput-inputEl']");
    private static final Label waitingPeriod = Label.xpath("//span[contains(text(),'Waiting Period')]/../following-sibling::div/div");
    private static final Label vehicleAgeLimTxt = Label.xpath("//span[text()='VehicleAge Limit']/../following-sibling::div/div");
    private static final Label mileageLimTxt = Label.xpath("//span[text()='Mileage Limit']/../following-sibling::div/div");
    private static final Dropdown durationMaintainTxt = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:0:CovTermInputSet:CovTermDirectInputSet:intRangeTermRangeInput-inputEl']");
    private static final Dropdown mileageMaintainTxt = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:intRangeTermRangeInput-inputEl']");
    private static final CheckBox assistanceCheckBoxEGVOM = CheckBox.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:2:CoverageInputSet:CovPatternWMInputGroup-legendChk']");
    private static final CheckBox assistanceCheckBoxEGVO = CheckBox.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup-legendChk']");
    private static final Button noWearAndTear = Button.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:2:CovTermInputSet:BooleanTermInput_true-inputEl']");
    private static final Dropdown durationMaintAlfaRomeo = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:0:CovTermInputSet:CovTermDirectInputSet:DirectTermInput-inputEl']");
    private static final Title vehicleTitleTxt = Title.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:ttlBar");
    private static final Dropdown billMethodDdTxt = Dropdown.xpath("//input[@id='SubmissionWizard:SubmissionWizard_PaymentScreen:BillingAdjustmentsDV:BillingMethod-inputEl']");
    private static final Button alertOKButton = Button.xpath("//span[contains(text(),'OK')]");
    private static final Button searchBillingAccButton = Button.xpath("//*[@id='SubmissionWizard:SubmissionWizard_PaymentScreen:BillingAdjustmentsDV:AccountContactBillingInputSet:AltBillingAccount:SearchBillingAccount-itemEl']");
    private static final Button alterBillingAccButton = Button.xpath("//*[@id='SubmissionWizard:SubmissionWizard_PaymentScreen:BillingAdjustmentsDV:AccountContactBillingInputSet:AltBillingAccount:AltBillingAccountMenuIcon']");
    private static final Button searchAccButton = Button.xpath("//*[@id='BillingAccountSearchPopup:SearchAndResetInputSet:SearchLinksInputSet:Search']");
    private static final TextBox accNumberTxt = TextBox.xpath("//*[@id='BillingAccountSearchPopup:AccountNumber-inputEl']");
    private static final TextBox accountCodeTxt = TextBox.xpath("//*[@id='BillingAccountSearchPopup:AccountRRDI-inputEl']");
    private static final Button selectBillingAccInAddBilButton = Button.xpath("//*[@id='BillingAccountSearchPopup:0:_Select']");
    private static final Dropdown coverageTypeDdTxt = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:flavourWM2ID-inputEl']");
    private static final Label offerDescriptionLabel = Label.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OfferDescriptionInputGroup-legendTitle");
    private static final Dropdown durationWarrantyTxt = Dropdown.xpath("//span[text()='Duration (Months)']/../following-sibling::div//input");
    private static final Dropdown durationMaintenanceDropdown = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:0:CovTermInputSet:CovTermDirectInputSet_fr_egvosa_wl_InputSet:intRangeTermRangeInput-inputEl']");
    private static final Dropdown mileageMaintenanceDropdown = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet_fr_egvosa_wl_InputSet:intRangeTermRangeInput-inputEl']");
    private static final Dropdown warrantyDurationDropdown = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:intRangeTermRangeInput-inputEl']");
    private static final Title searchAccPopupTitle = Title.id("BillingAccountSearchPopup:ttlBar");
    private static final CheckBox vehicleCheckbox = CheckBox.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableLV-body']//img");
    private static final Button removeButton = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel_tb:Remove-btnInnerEl");
    private static final Dropdown vehicleTypeDdTxt = Dropdown.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Type_DV-inputEl']");
    private static final Label vehicleTypeLbl = Label.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Type_DV-inputEl']");
    private static final TextBox firstRegDateDtTxt = TextBox.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:FirstRegistrationDate-inputEl");
    private static final Dropdown makeDdTxt = Dropdown.xpath("//input[contains(@id, 'Make_DV')]");
    private static final Items makeTypeList = Items.xpath("(//div[contains(@id,'boundlist') and @tabindex='0'])[1]//ul/li");
    private static final Items coverageTypeList = Items.xpath("(//div[contains(@id,'boundlist') and @tabindex='0'])[1]//ul/li");
    private static final TextBox modelTxt = TextBox.xpath("//input[contains(@id, 'Model_DV')]");
    private static final Items modelTypeList = Items.xpath("(//div[contains(@id,'boundlist') and @tabindex='0'])[1]//ul/li");
    private static final TextBox licensePlateTxt = TextBox.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:LicensePlate_DV-inputEl");
    private static final Button lpSearchButton = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:LicensePlate_DV:licensePlateSearch");
    private static final TextBox carPowerTxt = TextBox.xpath("//input[contains(@id, 'VehiclePower')]");
    private static final TextBox authLoadedWtTxt = TextBox.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:AuthorizedLoadedWeight-inputEl']");
    private static final Dropdown vehicleSpecificDdTxt = Dropdown.xpath("//*[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:VehicleSpecifities_Id-inputEl']");
    private static final TextBox mileageTxt = TextBox.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:CurrentMileage_DV-inputEl");
    private static final TextBox warrantyKMTxt = TextBox.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:MakerWarrantyKilometers-inputEl']");
    private static final TextBox warrantyEndDateTxt = TextBox.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:MakerWarrantyEnd-inputEl']");
    private static final Title polInfoTitleTxt = Title.id("SubmissionWizard:LOBWizardStepGroup:SubmissionWizard_PolicyInfoScreen:ttlBar");
    private static final Button polInfoNextButton = Button.id("SubmissionWizard:Next-btnInnerEl");
    private static final Button actionButton = Button.id("PolicyFile:PolicyFileMenuActions-btnInnerEl");
    private static final TextBox vinDdTxt = TextBox.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:Vin_DV-inputEl']");
    private static final Button vehicleAddButton = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel_tb:Add-btnInnerEl");
    private static final Title vehicleScreen = Title.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:ttlBar");
    private static final Button addVehicleButton = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel_tb:Add-btnInnerEl");
    private static final Button puwVehiclesButton = Button.xpath("//span[text()='PUW Vehicles']");
    private static final TextBox maintenanceMileageTextbox = TextBox.xpath("//input[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:1:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:DirectTermInput-inputEl']");
    private static final Tab coveragesTab = Tab.xpath("//span[@id='PolicyFile_PUWVehicle:PolicyFile_PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OtherOtherCoverageCardTab-btnInnerEl']");
    private static final Form puwVehiclesBody = Form.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV-body");
    private static final Button quoteLeftPanelButton = Button.xpath("//td[@id='PolicyFile:PolicyFileAcceleratedMenuActions:PolicyMenuItemSet:PolicyMenuItemSet_Pricing']//span");
    private static final Button puwVehiclesLeftPanelButton = Button.id("PolicyFile:PolicyFileAcceleratedMenuActions:PolicyMenuItemSet:PUWVehicle");
    private static final Title policyFileQuoteTitle = Title.xpath("//span[@id='PolicyFile_Pricing:PolicyFile_PricingScreen:0']");
    private static final Title puwVehiclesTitle = Title.id("PolicyFile_PUWVehicle:PolicyFile_PUWVehicleScreen:0");
    private static final Button ratingWorksheetButton = Button.id("PolicyFile_Pricing:PolicyFile_PricingScreen:0:RatingCumulDetailsPanelSet:RatingOverrideButtonDV:RatingOverrideButtonDV:ViewWorksheet");
    private static final Table ratingWsTable = Table.id("RatingWorksheetPopup-table");
    private static final Button expandAllButton = Button.id("RatingWorksheetPopup:Expand-btnInnerEl");
    private static final TextBox changeLicensePlate = TextBox.xpath("//input[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:LicensePlate_DV-inputEl']");
    private static final TextBox changeWarrantyEndDate = TextBox.xpath("//input[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:MakerWarrantyEnd-inputEl']");
    private static final TextBox changeWarrantyKM = TextBox.xpath("//input[@id='PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:MakerWarrantyKilometers-inputEl']");
    private static final Button changeQuoteButton = Button.id("PolicyChangeWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:JobWizardToolbarButtonSet:QuoteOrReview-btnInnerEl");
    private static final Button changeIssuePolicyButton = Button.id("PolicyChangeWizard:PolicyChangeWizard_QuoteScreen:JobWizardToolbarButtonSet:BindPolicyChange-btnInnerEl");
    private static final Title clientRelationServiceTxt = Title.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:2:CoverageInputSet:CovPatternWMInputGroup-legendTitle");
    private static final Button saveDraftButton = Button.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:JobWizardToolbarButtonSet:Draft-btnInnerEl");
    private static final Label createdByCell = Label.xpath("(//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableLV-body']//td)[3]/div");
    private static final Tab coverageButton = Tab.id("SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OtherOtherCoverageCardTab-btnInnerEl");
    private static final TextBox replacementPercentage = TextBox.xpath("//div[@id='PolicyFile_PUWVehicle:PolicyFile_PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:DirectTermInput-inputEl']");
    private static final CheckBox assistanceCbx = CheckBox.xpath("//div[text()='Assistance']/preceding-sibling::div//input");
    private static final Title paymentTxt = Title.id("SubmissionWizard:SubmissionWizard_PaymentScreen:ttlBar");
    private static final Title policyReviewTxt = Title.xpath("//div[contains(@class,'x-title-text x-title-text-default x-title-item')]//span[contains(text(),'Policy Review')]");
    private static final Link viewPolicyLink = Link.xpath("//div[@id='JobComplete:JobCompleteScreen:JobCompleteDV:ViewPolicy-inputEl']");
    private static final Title summaryPageTxt = Title.xpath("//div[contains(@class,'x-title-text x-title-text-default x-title-item')]//span[text()='Summary']");
    private static final Label policyNoUITxt = Label.xpath("//div[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_PolicyDV:PolicyNumber-inputEl']");
    private static final Button issuePolicyButton = Button.xpath("//*[@id='SubmissionWizard:SubmissionWizard_PaymentScreen:JobWizardBillingToolbarButtonSet:issue_PSA-btnInnerEl']");
    private static final Button quoteButton = Button.xpath("//*[@id='SubmissionWizard:SubmissionWizard_PolicyReview_PSAScreen:JobWizardToolbarButtonSet:QuoteOrReview-btnInnerEl']");
    private static final Title jobCompletedScreen = Title.id("JobComplete:JobCompleteScreen:ttlBar");
    private static final Title policyQuoteTxt = Title.id("SubmissionWizard:SubmissionWizard_QuoteScreen:ttlBar");
    private static final Label managementTypeLbl = Label.xpath("//*[@id='PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_PolicyDV:PolicyManagementType_PSA-inputEl']");
    private static final Label makerWarrantyEndDateValue = Label.xpath("//div[@id='PolicyFile_PUWVehicle:PolicyFile_PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:PUWVehicleDetailsDV:MakerWarrantyEnd-inputEl']");
    private static final Label sparePartsLabel = Label.xpath("//label[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:DirectTermInput-labelEl']/span");
    private static final Label sparePartsValue = Label.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:0:CoverageInputSet:CovPatternWMInputGroup:1:CovTermInputSet:CovTermDirectInputSet:DirectTermInput-inputEl']");
    private static final Label offerLabel = Label.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OfferDescriptionInputGroup:label-inputEl']");
    private static final Label offerLevel = Label.xpath("//div[@id='SubmissionWizard:LOBWizardStepGroup:LineWizardStepSet:PUWVehicleScreen:PUWVehiclePanelSet:CoverableListDetailPanel:CoverableDetailsCV:OfferDescriptionInputGroup:level-inputEl']");
    private static final Items currentActivities = Items.id("PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_ActivitiesLV-body");
    private static final Link activitySubject = Link.id("PolicyFile_Summary:Policy_Summary_PSAScreen:Policy_Summary_ActivitiesLV:0:Subject");
    private static final Table activityDetailTable = Table.id("ActivityDetailWorksheet-table");
    private static final Label subjectLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:Subject-inputEl']");
    private static final Label descriptionLabel = Label.xpath("//textarea[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:Description-inputEl']");
    private static final Label priorityLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:Priority-inputEl']");
    private static final Label escalationDateLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:EscalationDate-inputEl']");
    private static final Label assignedToLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:AssignedTo-inputEl']");
    private static final Label complaintsId = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailDV:referenceNumber-inputEl']");
    private static final Label dateReceivedLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailNoteDV:dateReceived-inputEl']");
    private static final Label complainantName = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailNoteDV:complaintantName-inputEl']");
    private static final Label relatedToLabel = Label.xpath("//div[@id='ActivityDetailWorksheet:ActivityDetailScreen:ActivityDetailNoteDV:RelatedTo-inputEl']");
    private static final List<Country> countries;

    static {
        try {
            countries = objectMapper.readValue(new File("src/test/resources/pc/rate_table.json"), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForSummaryPage() {
        logger.info("Wait for policy summary page is loaded completely");
        waitForElementVisible(summaryPageTxt);
    }

    public void clickActionButton() {
        click(actionButton);
    }

    public void changePolicy() {
        logger.info("Change policy with reason Prorogation and immobilization time " + dataObject.getPolicyCenter().getImmobilizationDays());
        click(changePolButton);
        setDropDown(reasonDdTxt, "Prorogation");
        waitForElement(immobilizationTimeTxt);
        sendText(immobilizationTimeTxt, dataObject.getPolicyCenter().getImmobilizationDays());
        click(nextButton);
        closePopup();
        waitForElementVisible(policySummaryScreen);
        String transactionNum = getText(changeTransactionNumTxt);
        logger.info("The Policy transaction number for Policy Change is " + transactionNum);
        addStepInfoToReport("The Policy transaction number for Policy Change is " + transactionNum);
    }

    public void cancel() {
        logger.info("Cancellation for Policy is being validated");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Conformity Warranty Issue");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod + " is displayed as expected for Conformity Warranty Issue");
        addStepInfoToReport("Refund Method " + refundMethod + " is displayed as expected for Conformity Warranty Issue");
        setDropDown(reasonCancelDdTxt, "Other");
        waitForPageLoadComplete();
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod1 = getText(refundMethodTxt);
        assert refundMethod1.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod1 + " is displayed as expected for Others");
        addStepInfoToReport("Refund Method " + refundMethod1 + " is displayed as expected for Others");
        setDropDown(reasonCancelDdTxt, "Satisfied or Reimbursed");
        waitForPageLoadComplete();
        waitForTextToBe(refundMethodTxt, FULL_REIMBURSEMENT);
        String refundMethod2 = getText(refundMethodTxt);
        assert refundMethod2.equalsIgnoreCase(FULL_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod2 + " is displayed as expected for Satisfied or Reimbursed");
        addStepInfoToReport("Refund Method " + refundMethod2 + " is displayed as expected for Satisfied or Reimbursed");
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        click(bindOptionsButton);
        click(cancelNowDdBtn);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);

        String cancellationMsg = getText(cancellationMessageTxt);
        logger.info(cancellationMsg);

        Link policyLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(policyLink);

        String transactionNum = getText(cancelTransactionNumTxt);
        logger.info("The Policy transaction number for Policy Cancellation is " + transactionNum);
        addStepInfoToReport("The Policy transaction number for Policy Cancellation is " + transactionNum);
    }

    public void changePolicyWithVehicleDataReason() {
        logger.info("Change policy vehicle data in Policy Center");
        click(changePolButton);
        setDropDown(reasonDdTxt, "Correction of vehicle data");// Create an input data field for this
        click(nextButton);
        click(wizardNextButton);
        String vinNumberUpdated = faker.vehicle().vin();
        click(vinTxt);
        clearText(vinTxt);
        sendText(vinTxt, vinNumberUpdated);
        click(vinSearchIcon);
        waitForElementVisible(vehicleErrorMessage);
        String existingCoverageType = dataObject.getPolicyCenter().getCoverageType();
        selectAnotherRandomCoverageType();
        String regDateUpdated = dataObject.getPolicyCenter().getRegDateChange();
        setEffectiveDate(firstRegDateDatePicker, regDateUpdated);

        long mileageUpdated = dataObject.getPolicyCenter().getMileageUpdated();
        clearText(currentMileageTxt);
        sendText(currentMileageTxt, mileageUpdated);

        click(otherCoverageButton);
        setDropDown(updatedCoverageTypeDd, dataObject.getPolicyCenter().getCoverageType());
        String coverageTypeUpdated = getAttributeValue(updatedCoverageTypeDd, "value");
        click(wizardNextButton);
        click(changeTreeImg);
        click(policyReviewTab);
        waitForElementVisible(policyReviewSumPanel);
        String updatedVinInUI = getText(vinUpdatedTxt);
        assert vinNumberUpdated.equalsIgnoreCase(updatedVinInUI);
        logger.info("As a result of Policy Change, existing vehicle number " + dataObject.getPolicyCenter().getVin() + " changed to " + updatedVinInUI + " as expected");
        addStepInfoToReport("As a result of Policy Change, existing vehicle number " + dataObject.getPolicyCenter().getVin() + " changed to " + updatedVinInUI + " as expected");

        click(differencesTab);
        click(expandDiffButton);
        long mileageInUI = Long.parseLong(getText(mileageUpdatedTxt));
        assert mileageUpdated == mileageInUI;
        logger.info("As a result of Policy Change, existing vehicle mileage " + dataObject.getPolicyCenter().getMileage() + " changed to " + mileageInUI + " as expected");

        String coverageTypeInUI = getText(coverageTypeUpdatedTxt);
        assert coverageTypeUpdated.equalsIgnoreCase(coverageTypeInUI);
        logger.info("As a result of Policy Change, existing coverage type " + existingCoverageType + " changed to " + coverageTypeInUI + " as expected");

        String firstRegInUI = getText(firstRegDateUpdatedTxt);
        try {
            regDateUpdated = new SimpleDateFormat("dd/MM/yy").format(
                    new SimpleDateFormat("dd/MM/yyyy").parse(
                            regDateUpdated.replace("-","/")
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert regDateUpdated.equalsIgnoreCase(firstRegInUI);
        logger.info("As a result of Policy Change, existing registration date " + dataObject.getPolicyCenter().getRegDate() + " changed to " + firstRegInUI + " as expected");
        addStepInfoToReport("Other information after policy change");

        // Write all change validations here
        click(quoteInPolicyChangeTxt);
        waitForElementVisible(quoteScreen);
        click(issueInPolChangeButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        String policyChangeMsg = getText(polChangeMsgTxt);
        assert policyChangeMsg.contains("Change");
        logger.info("Change Policy action completed successfully");
        addStepInfoToReport("Change Policy action completed successfully");
        Link policyLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(policyLink);
        waitForElementVisible(policySummaryScreen);

        String typeInTransactionTable = getText(changeTypeInTranscTableTxt);
        assert typeInTransactionTable.equalsIgnoreCase("Policy Change");
        logger.info("Type for Policy Change is displayed as expected");
        String transactionNum = getText(changeTransactionNumTxt);
        logger.info("The Policy Transaction number for Policy Change is " + transactionNum);
        addStepInfoToReport("The Policy Transaction number for Policy Change is " + transactionNum);
    }

    public void changeEGVOPolicyWithVehicleDataReason() {
        logger.info("Change EGVO policy vehicle data in Policy Center");
        click(changePolButton);
        setDropDown(reasonDdTxt, "Correction of vehicle data");
        click(nextButton);
        click(wizardNextButton);
        clearText(changeWarrantyEndDate);
        sendText(changeWarrantyEndDate, dataObject.getPolicyCenter().getWarrantyEndDate());
        clearText(changeWarrantyKM);
        sendText(changeWarrantyKM, dataObject.getPolicyCenter().getWarrantyKM());
        click(otherCoverageButton);
        select1stValueOfDropdown(updatedCoverageTypeDd);
        logger.info("Select coverage type " + getAttributeValue(updatedCoverageTypeDd, "value"));
    }

    public void cancelAsInsurer() {
        logger.info("Validating that when the User selects Insurer as Source then only the option Insurer Decision will be available for selection for the Reason field");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insurer");
        setDropDown(reasonCancelDdTxt, "Insurer Decision");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info(refundMethod + " is displayed as expected for Source: INSURER");
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        click(bindOptionsButton);
        click(cancelNowDdBtn);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        String cancellationMsg = getText(cancellationMessageTxt);
        logger.info(cancellationMsg);
        Link policyLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(policyLink);
    }

    public boolean isChangePolicyValidated() {
        click(changePolButton);
        setDropDown(reasonDdTxt, "Prorogation");
        waitForElementVisible(immobilizationTimeLbl);
        sendText(immobilizationTimeTxt, "10");
        String immobilizationTimeValue = getText(immobilizationTimeLbl);
        logger.info("Actual immobilization time label in UI is " + immobilizationTimeValue);
        assert immobilizationTimeValue.equalsIgnoreCase("Immobilization Time(Days)");
        click(nextButton);
        switchToActiveElement();
        waitForElementVisible(pcChangePopUpMsgTxt);
        String popUpMsgUI = getText(pcChangePopUpMsgTxt);
        click(popupCancelButton);
        click(nextButton);
        closePopup();
        waitForElementVisible(policySummaryScreen);
        return isElementDisplayed(policySummaryScreen);
    }

    public void cancelEffectiveDate() {
        logger.info("Validate when the user is on the Confirmation screen and clicks Bind option, " +
                "only Cancel Now option will be available and cancellation will occur at the date mentioned in the field cancellation effective date");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Satisfied or Reimbursed");
        waitForTextNotToBe(cancelEffectiveDateTxt, "");
        String cancelEffectiveDateValue = getText(cancelEffectiveDateTxt);
        assert !cancelEffectiveDateValue.isEmpty();
        dataObject.getPolicyCenter().setCancelEffectiveDate(cancelEffectiveDateValue);
        logger.info("The Cancellation effective date is " + cancelEffectiveDateValue);
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        click(bindOptionsButton);
        click(cancelNowDdBtn);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompleteLbl);
    }

    public boolean validateDifferentReasons() {
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Conformity Warranty Issue");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund method " + refundMethod + " is displayed for reason 'Conformity Warranty Issue'");
        setDropDown(reasonCancelDdTxt, "Other");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod + " is displayed for Reason 'Other'");
        setDropDown(reasonCancelDdTxt, "Satisfied or Reimbursed");
        waitForTextToBe(refundMethodTxt, FULL_REIMBURSEMENT);
        refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(FULL_REIMBURSEMENT);
        logger.info("Refund method " + refundMethod + " is displayed for Reason 'Satisfied or Reimbursed'");
        click(cancelButton);
        waitForElementVisible(policySummaryScreen);
        return isElementDisplayed(policySummaryScreen);
    }

    public void cancelWithSpecificReason(String reason) {
        switch (reason) {
            case "Satisfied or Reimbursed" -> cancelFullReimbursement();
            case "Conformity Warranty Issue" -> cancelConformityWarrantyIssue();
            case "Other" -> cancelOther();
        }
    }

    private void cancelFullReimbursement() {
        logger.info("Validating when the User selects the Reason as Satisfied or Reimbursed then the default Refund method should be Full Reimbursement");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Satisfied or Reimbursed");
        waitForTextToBe(refundMethodTxt, FULL_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(FULL_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod + " is displayed for Reason 'Satisfied or Reimbursed'");

        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        logger.info("Validating that when the refund method is Full Reimbursement, then the field change in cost will display - The total cost");
        String changeInCost = getText(changeInCostTxt);
        assert changeInCost.contains("-");
        logger.info("When the refund method is Full Reimbursement, then the field Change In Cost is displayed as " + changeInCost);
    }

    private void cancelConformityWarrantyIssue() {
        logger.info("Validating when the User selects the Reason as Conformity warranty issue then the default Refund method should be No Reimbursement");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Conformity Warranty Issue");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod + " is displayed for Reason 'Conformity Warranty Issue'");
        click(cancelButton);
        waitForElementVisible(policySummaryScreen);
    }

    public void cancelNoReimbursement() {
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Conformity Warranty Issue");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund Method " + refundMethod + " is displayed for Reason 'Conformity Warranty Issue'");
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        click(bindOptionsButton);
        click(cancelNowDdBtn);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompleteLbl);
    }

    private void cancelOther() {
        logger.info("Validating when the User selects the Reason as Other then the default Refund method should be No Reimbursement");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Other");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund method " + refundMethod + " is displayed for Reason 'Other'");
        click(cancelButton);
        waitForElementVisible(policySummaryScreen);
    }

    public boolean cancelInsurerDecision() {
        logger.info("Validating when the User selects Insurer as Source then only the option Insurer Decision will be available for selection for the Reason field");
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insurer");
        setDropDown(reasonCancelDdTxt, "Insurer Decision");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        boolean result = refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        if (result) {
            logger.info(refundMethod + " is displayed as expected for Source: Insurer");
            click(cancelButton);
            waitForElementVisible(policySummaryScreen);
        }
        return result;
    }

    public String getCostOfChangeInsurer() {
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insurer");
        setDropDown(reasonCancelDdTxt, "Insurer Decision");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info(refundMethod + " is displayed as expected for Source: Insurer");
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        return getText(changeInCostValueTxt);
    }

    public boolean checkChangeInCostForFullReimbursement() {
        click(cancelPolButton);
        waitForElementVisible(startCancellationTxt);
        setDropDown(sourceDdTxt, "Insured");
        setDropDown(reasonCancelDdTxt, "Conformity Warranty Issue");
        waitForTextToBe(refundMethodTxt, NO_REIMBURSEMENT);
        String refundMethod = getText(refundMethodTxt);
        assert refundMethod.equalsIgnoreCase(NO_REIMBURSEMENT);
        logger.info("Refund method " + refundMethod + " is displayed for Reason 'Conformity Warranty Issue'");
        String cancelEffectiveDate = getAttributeValue(cancelEffectiveDateTxt, "value");
        logger.info("The cancel effective date in start cancel page is " + cancelEffectiveDate);
        click(startCancellationButton);
        waitForElementVisible(cancelConfirmationTitle);
        String changeInCost = getText(changeInCostTxt);
        assert changeInCost.equalsIgnoreCase("0");
        logger.info("The Change in cost for Reason for Conformity Warranty Issue and the refund method for No Reimbursement is displaying " + changeInCost + " as expected");
        String transactionEffectiveDateValue = getText(transcEffDateTxt);
        String transactionEffDate = DateHelper.getFormattedDate(transactionEffectiveDateValue);
        logger.info("The Transaction Effective Date after formatting is "+ transactionEffDate);
        boolean result = cancelEffectiveDate.equalsIgnoreCase(transactionEffDate);
        if (result) {
            logger.info("The Cancellation Effective Date " + cancelEffectiveDate + " is matching with Transaction effective date " + transactionEffDate + " as expected");
        } else {
            logger.info("The Cancellation Effective Date " + cancelEffectiveDate + " is not matched with Transaction effective date " + transactionEffDate + " as expected");
        }
        return result;
    }

    public String getTypeOfPolicyTransaction() {
        return getText(changeTypeInTranscTableTxt);
    }

    public boolean isWarrantyExpirationDateExtended() {
        logger.info("Validating that when the user clicks on yes the current expiration date of the warranty is extended by the number of days indicated in the field Immobilization time in days");
        click(changePolButton);
        setDropDown(reasonDdTxt, "Prorogation");
        waitForElementVisible(immobilizationTimeLbl);
        sendText(immobilizationTimeTxt, "30");
        click(nextButton);
        closePopup();
        logger.info("The OK button is triggered from popup");
        waitForElementVisible(policySummaryScreen);
        click(jobNumber);
        waitForElement(assistanceExpDateTxt);
        String assistExpDate = getText(assistanceExpDateTxt);
        waitForElement(warrantyExpDateTxt);
        String warrantyExpDate = getText(warrantyExpDateTxt);
        String expDateDiff = DateHelper.getDiffBetweenTwoDays(assistExpDate, warrantyExpDate);
        logger.info("Expiration date diff is " + expDateDiff);
        return expDateDiff.equalsIgnoreCase("30");
    }

    public void savePolicyInformation() {
        waitForElementVisible(summaryPageTxt);
        waitForTextNotToBe(accNoUITxt, "");
        String accountNoUI = getText(accNoUITxt);
        logger.info("The Policy Center account number generated is: " + accountNoUI);
        dataObject.setAccountNumber(accountNoUI);
        waitForTextNotToBe(policyNoUITxt, "");
        String policyNoUI = getText(policyNoUITxt);
        logger.info("The Policy number generated is: " + policyNoUI);
        dataObject.setPolicyNumber(policyNoUI);
        logger.info("Policy creation completed");
        addStepInfoToReport("Policy creation completed");
    }

    private void goToChangePolicy() {
        click(actionButton);
        click(changePolButton);
    }

    private void selectChangeReason(String reason) {
        setDropDown(reasonDdTxt, reason);
        addStepInfoToReport("Selected change reason with value " + reason);
        click(nextButton);
        waitForElementVisible(effectiveDateTxt);
        waitForElementVisible(writtenDateTxt);
        click(wizardNextButton);
    }

    public void openRateWorksheet() {
        clickQuoteTab();
        goToRatingWorksheet();
        expandAllNodes();
    }
    private void changeNewVIN() {
        String vinNoUpdated = faker.vehicle().vin();
        logger.info("New VIN will be " + vinNoUpdated);
        clearText(vinTxt);
        sendText(vinTxt, vinNoUpdated);

        click(vinSearchIcon);
        waitForElementVisible(vehicleErrorMessage);
        dataObject.getPolicyCenter().setVinChange(vinNoUpdated);
        addStepInfoToReport("Change vehicle VIN to new value " + vinNoUpdated);
    }

    private void changeRegistrationDate() {
        String firstRegDateUpdated = dataObject.getPolicyCenter().getRegDateChange();
        setEffectiveDate(firstRegDateDatePicker, firstRegDateUpdated);

        String newDate = firstRegDateUpdated.replace("-", "/");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date regDate;
        try {
            regDate = dateFormat.parse(newDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dateFormat = new SimpleDateFormat("dd/MM/yy");
        String regDateUpdated = dateFormat.format(regDate);
        dataObject.getPolicyCenter().setRegDateChange(regDateUpdated);
        logger.info("Registration Date updated to: " + regDateUpdated);
    }

    private void changeTypeOfEnergy() {
        String typeOfEnergyUpdated = dataObject.getPolicyCenter().getEnergyChange();
        setDropDown(typeOfEnergyChange, typeOfEnergyUpdated);

    }

    private void changeLP() {
        clearText(changeLicensePlate);
        sendText(changeLicensePlate, dataObject.getPolicyCenter().getLicensePlate());

    }

    private void changeCurrentMileage() {
        long mileageUpdated = dataObject.getPolicyCenter().getMileageUpdated();
        clearText(currentMileageTxt);
        sendText(currentMileageTxt, mileageUpdated);

    }

    private void selectNewCoverageType() {
        click(otherCoverageButton);
        select1stValueOfDropdown(updatedCoverageTypeDd);
        addStepInfoToReport("Selected new coverage type");
        click(wizardNextButton);
    }

    private void expandChangeView() {
        click(changeTreeImg);
        addStepInfoToReport("Expanded the policy view");
    }

    private void switchToPolicyReviewTab() {
        click(policyReviewTab);
        waitForElementVisible(policyReviewSumPanel);
        addStepInfoToReport("Switch to Policy review tab");
    }

    private void finishPolicyChange() {
        click(quoteInPolicyChangeTxt);
        waitForElementVisible(quoteScreen);
        click(issueInPolChangeButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        addStepInfoToReport("Finish changing policy");
        String polChangeMsg = getText(polChangeMsgTxt);
        assert polChangeMsg.contains("Change");
        logger.info("Change Policy Action completed successfully");
    }

    private void clickPolicyLink() {
        Link policyLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(policyLink);
        waitForElementVisible(policySummaryScreen);
        addStepInfoToReport("Clicked on policy link");
    }

    private void expandTheDiffView() {
        click(differencesTab);
        try {
            if (isElementDisplayed(typeOfEnergyUpdatedTxt, 3)) {
                logger.info("All differences data are expanded");
            }
        } catch (NoSuchElementException e) {
            logger.info("All differences data are collapsed. Expanding...");
            click(expandDiffButton);
        }
    }

    private void verifyChangedVIN() {
        String updatedVinInUI = getText(vinUpdatedTxt);
        String expectedUpdatedVin = dataObject.getPolicyCenter().getVinChange();
        assert expectedUpdatedVin.equalsIgnoreCase(updatedVinInUI);
        logger.info("As a result of Policy Change, existing vehicle number " + dataObject.getPolicyCenter().getVin() + " changed to " + updatedVinInUI + " as expected");
        dataObject.getPolicyCenter().setVin(expectedUpdatedVin);
    }

    private void verifyChangedTypeOfEnergy() {
        String typeOfEnergyUI = getText(typeOfEnergyUpdatedTxt);
        assert dataObject.getPolicyCenter().getEnergyChange().equalsIgnoreCase(typeOfEnergyUI);
        logger.info("As a result of Policy Change, energy Type " + dataObject.getPolicyCenter().getEnergyType() + " changed to " + typeOfEnergyUI+ " as expected");
    }

    private void verifyChangedMileage() {
        long mileageUI = Long.parseLong(getText(mileageUpdatedTxt));
        assert dataObject.getPolicyCenter().getMileageUpdated() == mileageUI;
        logger.info("As a result of Policy Change, existing Mileage " + dataObject.getPolicyCenter().getMileage() + " changed to " + mileageUI+ " as expected");
    }

    private void verifyChangedRegistrationDate() {
        String firstRegInUI = getText(firstRegDateUpdatedTxt);
        String expectedFirstRegDate = dataObject.getPolicyCenter().getRegDateChange();
        assert expectedFirstRegDate.equalsIgnoreCase(firstRegInUI);
        logger.info("As a result of Policy Change, existing Registration Date " + dataObject.getPolicyCenter().getRegDate() + " changed to " + firstRegInUI + " as expected");
    }

    public void changePolicyAsVehicleDataCorrection() {
        goToChangePolicy();
        selectChangeReason("Correction of vehicle data");
        changeNewVIN();
        changeRegistrationDate();
        changeTypeOfEnergy();
        changeLP();
        changeMakerWarrantyEndDate();
        changeCurrentMileage();
        selectNewCoverageType();
    }

    private void changeMakerWarrantyEndDate() {
        if (dataObject.getPolicyCenter().getWarrantyEndDate() != null) {
            clearText(changeWarrantyEndDate);
            sendText(changeWarrantyEndDate, dataObject.getPolicyCenter().getWarrantyEndDate());
            logger.info("Changed the Maker warranty end date to " + dataObject.getPolicyCenter().getWarrantyEndDate());
        }
    }

    public void viewUpdatedPolicy() {
        expandChangeView();
        switchToPolicyReviewTab();
        finishPolicyChange();
        clickPolicyLink();
    }

    public boolean isPUWScreenValidated() {
        expandChangeView();
        switchToPolicyReviewTab();
        verifyChangedVIN();
        expandTheDiffView();
        verifyChangedTypeOfEnergy();
        verifyChangedMileage();
        verifyChangedRegistrationDate();
        finishPolicyChange();
        clickPolicyLink();
        String typeInTransactionTable = getText(changeTypeInTranscTableTxt);
        logger.info("Current actual type for Policy is " + typeInTransactionTable);
        return typeInTransactionTable.equalsIgnoreCase("Policy Change");
    }

    public void setChangeCost() {
        click(changeTransactionNumTxt);
        waitForElementVisible(quoteScreen);
        String changeCostValue = getText(changeCost);
        String changeCostInPC = changeCostValue
                .split(" ")[0]
                .replace(',', '.');
        if (changeCostInPC.equalsIgnoreCase("0")) {
            changeCostInPC = "0.00";
        }
        dataObject.getPolicyCenter().setCostChange(Double.parseDouble(changeCostInPC));
    }

    public String getCancelMsg() {
        logger.info("Get cancel message");
        return getText(cancelMsgTxt);
    }

    public String getTransactionType() {
        return getText(type);
    }

    public void reinstate() {
        logger.info("Policy reinstatement starts");
        click(policyReInstatementButton);
        switchToActiveElement();
        closePopup();

        String reason = "Reinstating the Policy (" + dataObject.getPolicyNumber() + ")";
        sendText(reintReasonDescTxt, reason);
        click(reintQuoteButton);
        waitForElementVisible(reinstatementButton);
        click(reinstatementButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);

        String reinstatementMsg = getText(reinstateMessage);
        logger.info(reinstatementMsg);
        addStepInfoToReport(reinstatementMsg);
    }

    public boolean isReinstateEffectiveDateMatched() {
        click(policyReInstatementButton);
        switchToActiveElement();
        closePopup();
        String reinsEffDate = getText(reinEffectiveDateTxt);
        dataObject.getPolicyCenter().setReinstateEffectiveDate(reinsEffDate);
        logger.info("The reinstatement effective date is " + reinsEffDate);
        String cancelEffectiveDate = dataObject.getPolicyCenter().getCancelEffectiveDate();
        boolean result = reinsEffDate.equalsIgnoreCase(cancelEffectiveDate);
        if (result) {
            logger.info("The cancel effective date " + cancelEffectiveDate + " is matched with Reinstatement effective date " + reinsEffDate + " as expected");
            String reason = "Reinstating, The Policy (" + dataObject.getPolicyNumber() + ")";
            sendText(reintReasonDescTxt, reason);
            click(reintQuoteButton);
            click(reinstatementButton);
            switchToActiveElement();
            closePopup();
            waitForElementVisible(jobCompletedScreen);
        } else {
            logger.info("The cancel effective date " + cancelEffectiveDate + " is not matched with Reinstatement effective date " + reinsEffDate + " as expected");
        }
        return result;
    }

    public String getReinstateMessage() {
        logger.info("Get reinstatement message");
        Link reinstateLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(reinstateLink);
        waitForPageLoadComplete();
        waitForElementVisible(reinTransactionNumTxt);
        String transactionNum = getText(reinTransactionNumTxt);
        logger.info("The Policy transaction number for Policy Reinstatement is " + transactionNum);
        return getText(inForceMsgTxt);
    }

    public boolean isPopupMessageDisplayAsExpected() {
        logger.info("Reinstatement popup message validation with Policy Reinstatement");
        click(policyReInstatementButton);
        switchToActiveElement();
        String popUpMsg = getText(reinPopUpMsgTxt);
        assert popUpMsg.equalsIgnoreCase("Are you sure you want to reinstate this policy?");
        logger.info("The popup message '" + popUpMsg + "' is displaying as expected");
        closePopup();

        String reason = "Reinstating the Policy (" + dataObject.getPolicyNumber() + ")";
        sendText(reintReasonDescTxt, reason);
        click(reintQuoteButton);
        click(reinstatementButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        String reinstatementMsg = getText(reinstateMessage);
        logger.info(reinstatementMsg);
        return true;
    }

    public boolean isReinstatementButtonPresent() {
        logger.info("Validate that reinstatement is Button is presented only if the cancellation Reasons are Other or Satisfied or Reimbursed");
        try {
            isElementDisplayed(reinstatementButton, 5);
            return true;
        } catch (NoSuchElementException e) {
            logger.info("The Reinstatement is not possible if the Cancellation motive other than Other or Satisfied or Reimbursed");
            return false;
        }
    }

    public String getMarketingFeeValue() {
        return getText(marketingFeeTxt);
    }

    public String getQuoteMarketingFee() {
        return getText(marketingFeeTxt);
    }

    public String getBrandName() {
        logger.info("Get Make value at summary screen");
        waitForElementVisible(vehicleInfoSummaryMake);
        return getText(vehicleInfoSummaryMake);
    }

    private void setVehicles() {
        logger.info("Adding vehicle details for Policy Center");
        waitForElementVisible(vehicleTitleTxt);
        addVehicleBatches();
        validateCoverage();
        addStepInfoToReport("Filled out all mandatory fields");
    }

    public void validateCoverageType() {
        clickCoverageTab();
        validateCoverage();
    }

    public void addVehicleDetails() {
        navigateToPUWVehicleScreen();
        inputVehicleDetailsInfo();
    }

    public void createNewPolicy() {
        submitPolicy();
        savePolicyInformation();
        logger.info("Policy creation completed");
    }

    public void submitPolicy() {
        navigateToPUWVehicleScreen();
        setVehicles();
        reviewPolicy();
        goToPayment();
        makePayment();
    }

    public void makePayment() {
        String billingMethod = dataObject.getPolicyCenter().getBillingMethod();
        logger.info("Current billing method is " + billingMethod);
        if (!getAttributeValue(billMethodDdTxt, "value").equals(billingMethod)) {
            setDropDown(billMethodDdTxt, billingMethod);

        }
        if (billingMethod.equalsIgnoreCase(LISTBILL)) {
            logger.info("Adding the List Bill account");
            click(alterBillingAccButton);
            waitForElementVisible(searchBillingAccButton);
            click(searchBillingAccButton);
            waitForElementVisible(searchAccPopupTitle);
            clearText(accNumberTxt);
            sendText(accNumberTxt, dataObject.getBillingCenter().getAccountNoBilling());

            clearText(accountCodeTxt);
            click(searchAccButton);
            waitForElement(selectBillingAccInAddBilButton);
            click(selectBillingAccInAddBilButton);
            logger.info("List Bill Account has been added");
        } else {
            switchToActiveElement();
            click(alertOKButton);
        }
        waitForPageLoadComplete();

        //wait for methods table visible
        waitForElementVisible(issuePolicyButton);
        click(issuePolicyButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        logger.info("Policy payment is completed");
        addStepInfoToReport("Policy payment is completed");
        click(viewPolicyLink);
    }

    public void clickCoverageTab() {
        waitForElementVisible(coverageButton);
        jsClick(coverageButton);
        logger.info("Clicked Coverage button");
    }

    private void addVehicleBatches() {
        logger.info("Clicking on Add vehicle button");
        inputVehicleDetailsInfo();
        clickCoverageTab();
    }

    public int getReplacementPercentage(){
        clickMenu("Policy");
        logger.info("Clicked on Policy Menu");
        sendText(searchPolTxt, dataObject.getPolicyNumber());
        logger.info("Entered Policy in Search option");
        click(searchPolButton);
        waitForPageLoadComplete();
        click(puwVehiclesButton);
        click(coveragesTab);
        return Integer.parseInt(getText(replacementPercentage));
    }

    private void navigateToEGVOPolicyCreation() {
        waitForElementVisible(polInfoTitleTxt);
        click(polInfoNextButton);
        logger.info("Adding the vehicle details for Policy Center");
        waitForElementVisible(vehicleScreen);

        logger.info("Clicking on Add button");
        click(vehicleAddButton);
        waitForElementVisible(puwVehiclesBody);
    }

    public void createEGVOSAWithVIN(String vin) {
        navigateToEGVOPolicyCreation();
        logger.info("Entering VIN number of GVO policy created so that system populates all other fields");
        sendText(vinDdTxt, vin);
        focusFirstInputField();
    }

    public void addExistingVINForEGVO() {
        navigateToEGVOPolicyCreation();
        String currentVIN = dataObject.getPolicyCenter().getVin();
        logger.info("Input VIN " + currentVIN);
        waitForElementVisible(vinDdTxt);
        clearText(vinDdTxt);
        sendText(vinDdTxt, currentVIN);
        focusFirstInputField();
        addStepInfoToReport("Filled the existing VIN " + currentVIN);
        waitForTextNotToBe(vehicleTypeLbl, "");
        clickCoverageTab();
    }

    public void issueEGVOPolicy() {
        waitForElementVisible(polInfoNextButton);
        click(polInfoNextButton);
        waitForElementVisible(policyReviewTxt);
        addStepInfoToReport("Review EGVO policy");
        click(quoteButton);
        waitForElementVisible(policyQuoteTxt);
        addStepInfoToReport("Quote EGVO policy");
        click(polInfoNextButton);
        waitForElementVisible(paymentTxt);
        addStepInfoToReport("Issue the EGVO policy");
        click(issuePolicyButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        click(viewPolicyLink);
        waitForElementVisible(policySummaryScreen);
        String egvoPolicyNoUI = getText(policyNoUITxt);
        logger.info("The EGVO policy number generated is: " + egvoPolicyNoUI);
        addStepInfoToReport("EGVO policy is created successfully");
    }

    public void createEGVOPolicy() {
        addExistingVINForEGVO();
        selectEGVOType();
        validateEGVOStandaloneCoverage();
        issueEGVOPolicy();
    }

    public void waitForPolicyInfoPage() {
        waitForElementVisible(polInfoTitleTxt);
    }

    public void removeVehicle() {
        click(vehicleCheckbox);
        waitForAttributeContains(vehicleCheckbox, "class", "checked");
        click(removeButton);
        waitForElementVisible(addVehicleButton);
    }

    public void reviewPolicy() {
        waitForElementVisible(policyReviewTxt);
        click(quoteButton);
        waitForElementVisible(policyQuoteTxt);
        addStepInfoToReport("Review policy");
    }

    public void issuePolicy() {
        makePayment();
        savePolicyInformation();
    }

    public void addVehicleBody() {
        click(addVehicleButton);
        waitForElementVisible(puwVehiclesBody);
        addStepInfoToReport("Added new vehicle detail form");
    }

    public void inputVin(String vinNo) {
        logger.info("Add vehicle information with VIN " + vinNo);
        sendText(vinDdTxt, vinNo);
        focusFirstInputField();
        dataObject.getPolicyCenter().setVin(vinNo);
        addStepInfoToReport("Added vehicle information with VIN " + vinNo);
    }

    public void inputVehicleVin() {
        String vinNo = faker.vehicle().vin();
        clearText(vinDdTxt);
        sendText(vinDdTxt, vinNo);
        focusFirstInputField();
        dataObject.getPolicyCenter().setVin(vinNo);
        logger.info("Add vehicle information with VIN " + vinNo);
        addStepInfoToReport("Added vehicle information with VIN " + vinNo);
    }

    public void inputVehicleDetailsInfo() {
        addVehicleBody();
        inputVehicleVin();
        inputVehicleMandatoryFields();
    }

    public void clickVINSearchIcon() {
        int maxRetries = 3;
        for (int i = 1; i <= maxRetries; i++) {
            try {
                click(newSubVinSearchIcon);
                assert isElementDisplayed(vehicleErrorMessage, 5);
                clickPageBody();
                addStepInfoToReport("Clicked VIN search icon");
                break;
            } catch (NoSuchElementException e) {
                if (i == maxRetries) {
                    throw e;
                } else {
                    logger.debug("Retried " + (i + 1) + " time(s) for clicking VIN search icon and wait for warning message appear");
                }
            }
        }
    }

    public void selectVehicleType() {
        setDropDown(vehicleTypeDdTxt, dataObject.getPolicyCenter().getVehicleType());
        logger.info("Vehicle type is " + dataObject.getPolicyCenter().getVehicleType());
    }

    public void setFirstRegistrationDate() {
        clearText(firstRegDateDtTxt);
        sendText(firstRegDateDtTxt, dataObject.getPolicyCenter().getRegDate());
        logger.info("Registration date is " + dataObject.getPolicyCenter().getRegDate());
    }

    public void selectMake() {
        setDropDown(makeDdTxt, dataObject.getPolicyCenter().getMake());
        logger.info("Make value input is " + getAttributeValue(makeDdTxt, "value"));
    }

    public void setModel() {
        if (Arrays.asList(makesHasModelSelector).toString().contains(dataObject.getPolicyCenter().getMake())) {
            setDropDown(modelTxt, dataObject.getPolicyCenter().getModel());
        } else {
            do {
                clearText(modelTxt);
                sendText(modelTxt, dataObject.getPolicyCenter().getModel());
            } while (getAttributeValue(modelTxt, "value").isEmpty());
        }
        logger.info("Model input is " + getAttributeValue(modelTxt, "value"));
    }

    public void setLicensePlate() {
        clearText(licensePlateTxt);
        sendText(licensePlateTxt, dataObject.getPolicyCenter().getLicensePlate());
        logger.info("License plate input is " + getAttributeValue(licensePlateTxt, "value"));
        searchLicensePlate();
    }

    public void setLicensePlate(String licensePlate) {
        do {
            clearText(licensePlateTxt);
            sendText(licensePlateTxt, licensePlate);
        } while (getAttributeValue(licensePlateTxt, "value").isEmpty());
        logger.info("License plate input is " + getAttributeValue(licensePlateTxt, "value"));
        searchLicensePlate();
    }

    public void searchLicensePlate() {
        if (!dataObject.getCountry().equals(Germany)) { //License plate is not mandatory in Germany
            waitForElementVisible(lpSearchButton);
            click(lpSearchButton);
            waitForElementVisible(vehicleErrorMessage);
            addStepInfoToReport("Clicked License plate search icon 1st time");
            click(lpSearchButton);
            waitForElementVisible(vehicleErrorMessage);
            clickPageBody();
            addStepInfoToReport("Clicked License plate search icon 2nd time");
        }
    }

    public void selectTypeOfEnergy() {
        do {
            setDropDown(typeOfEnergyDdTxt, dataObject.getPolicyCenter().getEnergyType());
        } while (getAttributeValue(typeOfEnergyDdTxt, "value").equals("<none>"));
        logger.info("Energy type selected is " + getAttributeValue(typeOfEnergyDdTxt, "value"));
    }

    public void setCarPower() {
        do {
            sendText(carPowerTxt, dataObject.getPolicyCenter().getCarPower());
        } while (getAttributeValue(carPowerTxt, "value").isEmpty());
        logger.info("Car power input is " + getAttributeValue(carPowerTxt, "value"));
    }

    public void selectVehicleSpecificities() {
        do {
            setDropDown(vehicleSpecificDdTxt, dataObject.getPolicyCenter().getVehicleSpecific());
        } while (getAttributeValue(vehicleSpecificDdTxt, "value").equals("<none>"));
        logger.info("The vehicle specific value provided is " + getAttributeValue(vehicleSpecificDdTxt, "value"));
    }

    public void setMileage() {
        do {
            sendText(mileageTxt, dataObject.getPolicyCenter().getMileage());
        } while (getAttributeValue(mileageTxt, "value").isEmpty());
        logger.info("Mileage value provided is " + getAttributeValue(mileageTxt, "value"));
    }

    public void setWarrantyKM() {
        do {
            sendText(warrantyKMTxt, dataObject.getPolicyCenter().getWarrantyKM());
        } while (getAttributeValue(warrantyKMTxt, "value").isEmpty());
        logger.info("Value of Maker warranty - kilometers provided is " + getAttributeValue(warrantyKMTxt, "value"));
        waitForElementVisible(polInfoNextButton);
    }

    public void setWarrantyEndDate() {
        clearText(warrantyEndDateTxt);
        sendText(warrantyEndDateTxt, dataObject.getPolicyCenter().getWarrantyEndDate());
        logger.info("Maker warranty end date provided is " + getAttributeValue(warrantyEndDateTxt, "value"));
    }

    public void setAuthorizedWeight() {
        clearText(authLoadedWtTxt);
        sendText(authLoadedWtTxt, dataObject.getPolicyCenter().getAuthorizedWeight());
        logger.info("Value of Authorized weight provided is " + getAttributeValue(authLoadedWtTxt, "value"));
    }

    private void inputVehicleMandatoryFields() {
        selectVehicleType();
        setFirstRegistrationDate();
        selectMake();
        setModel();
        setLicensePlate();
        clickVINSearchIcon();
        selectTypeOfEnergy();
        setCarPower();
        selectVehicleSpecificities();
        setMileage();
        setWarrantyKM();
        addStepInfoToReport("Filled out all mandatory fields of vehicle detail");
    }

    private void selectTypeFromList(String expectedType) {
        click(coverageTypeDdTxt);
        List<WebElement> availableTypes = findElements(coverageTypeList);
        availableTypes.remove(0); //Remove the <none> element
        logger.info("Number of available coverage type is " + availableTypes.size());
        for (WebElement availableType : availableTypes) {
            String typeValue = getText(availableType);
            logger.info("There's coverage type: " + typeValue);
        }
        setDropDown(coverageTypeDdTxt, expectedType);
        waitForAttributeNotToBe(coverageTypeDdTxt, "value", "<none>");
        String selectedType = getAttributeValue(coverageTypeDdTxt, "value");
        logger.info("The selected coverage type is " + selectedType);

        assert selectedType.equals(expectedType);
        addStepInfoToReport("Selected " + selectedType);
    }

    public void selectType() {
        String expectedType = dataObject.getPolicyCenter().getCoverageType();
        waitForElementVisible(offerDescriptionLabel);
        selectTypeFromList(expectedType);
        waitForPageLoadComplete();
    }

    public void selectFirstType() {
        select1stValueOfDropdown(coverageTypeDdTxt);
        waitForPageLoadComplete();
    }

    public void selectEGVOType() {
        String egvoCoverageType = dataObject.getPolicyCenter().getEgvoCoverageType();
        selectTypeFromList(egvoCoverageType);
    }

    public void validateEGVOStandaloneCoverage() {
        String egvoCoverageType = dataObject.getPolicyCenter().getEgvoCoverageType();
        waitForElementVisible(warrantyField);
        switch (egvoCoverageType) {
            case EGVOWTDS2_40, EGVOWTDS2_60, EGVOWTDS4_40, EGVOWTDS4_60 -> validateEGVOWT();
            case EGVODS2_100, EGVODSC7_150 -> validateEGVO_Spain_DSC();
            case EGVOMPremium2_40, EGVOMPremium2_60, EGVOMPremium4_40, EGVOMPremium4_60 -> validateEGVOM_Premium();
            case EGVOPremium2_100 -> validateEGVO_Premium2_100();
            case EGVOPremium5_150 -> validateEGVO_Premium5_150();
            case EGVOSpoticarPremium4_40, EGVOSpoticarPremium4_60 -> validateEGVO_Spoticar_Premium();
            case EGVOSpoticarPremium7_150 -> validateEGVO_Premium_7_150();
            case EGVOAdvanced10_200 -> validateEGVO_Advanced_10_200();
            case EGVOMaintenanceARC4_50 -> validateEGVO_Maintenance_ARC();
            case EGVOARC4_80 -> validateEGVO_ARC_Assistance();
            case EGVOARC4_150, EGVOARC7_150 -> validateEGVO_ARC();
            case GaranziaStandaloneARC4_80, GaranziaStandaloneDSC4_80, GaranziaStandaloneDSC7_150,
                    GaranziaStandaloneLancia4_80, GaranziaStandaloneLancia7_150 -> validateEGVO_Garanzia(warrantyDurationDropdown);
            case GaranziaStandaloneARC7_150 -> validateEGVO_Garanzia(durationWarrantyTxt);
            case EGVOMaintenanceDSC4_50 -> validateEGVO_MaintenanceDSC_4_50();
            case EGVODSC4_80, EGVODSC4_150, EGVODS5_150 -> validateEGVO_DSC();
            case EGVOCare4_40, EGVOCare4_60 -> validateEGVO_WL_CompleteCare();
            case EGVOCare7_150, EGVOCare10_200 -> validateEGVO_WL();
        }
        addStepInfoToReport("EGVO Standalone coverage validation completed");
    }

    public void validateCoverage() {
        selectType();
        waitForPageLoadComplete();
        click(polInfoNextButton);
    }

    public boolean isPolicyCreated() {
        waitForElementVisible(summaryPageTxt);
        return isElementDisplayed(summaryPageTxt);
    }

    public String getErrorMessageAtVehicleScreen() {
        logger.info("Get current error message at PUW vehicle screen");

        waitForElementVisible(vehicleErrorMessage);
        waitForTextNotToBe(vehicleErrorMessage, "");
        addStepInfoToReport("Get current error message at this moment in PUW Vehicles form");
        return getText(vehicleErrorMessage);
    }

    public void navigateToPUWVehicleScreen() {
        waitForElementVisible(polInfoTitleTxt);
        click(polInfoNextButton);
        waitForElementVisible(vehicleTitleTxt);
        addStepInfoToReport("Navigated to PUW Vehicles form");
    }

    public void goToReview() {
        click(polInfoNextButton);
        waitForElementVisible(policyReviewTxt);
        addStepInfoToReport("Navigated to Review Policy page");
    }

    public void goToPayment() {
        click(polInfoNextButton);
        waitForElementVisible(paymentTxt);
        addStepInfoToReport("Navigated to Payment page");
    }

    private void validateVehicleAgeLimit(int expectedAge) {
        String vehicleAgeLim = getText(vehicleAgeLimTxt);
        logger.info("Actual vehicle age limit is " + vehicleAgeLim);
        assert vehicleAgeLim.equalsIgnoreCase(String.valueOf(expectedAge));
    }

    private void validateMileageLimit(String maxMileage) {
        String mileageLim = getText(mileageLimTxt);
        logger.info("Actual mileage limit is " + mileageLim);
        assert mileageLim.equalsIgnoreCase(maxMileage);
    }

    private void validateEGVO_Premium_7_150() {
        validateVehicleAgeLimit(10);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVOWT() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationMaintainTxt, "24");
        setDropDown(durationMaintainTxt, "36");
        setDropDown(durationMaintainTxt, "48");
        setDropDown(durationMaintainTxt, "60");
        setDropDown(durationMaintainTxt, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration(), 2);
        setDropDown(mileageMaintainTxt, "15000");
        click(noWearAndTear);
    }

    private void validateEGVOM_Premium() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationMaintainTxt, "24");
        setDropDown(durationMaintainTxt, "36");
        setDropDown(durationMaintainTxt, "48");
        setDropDown(durationMaintainTxt, "60");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration(), 2);
        setDropDown(durationMaintainTxt, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(mileageMaintainTxt, "15000");
        setDropDown(mileageMaintainTxt, "20000");
    }

    private void validateEGVO_DSC() {
        validateVehicleAgeLimit(10);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_Spain_DSC() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_WL_CompleteCare() {
        waitForElementVisible(mileageMaintenanceDropdown);
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
        setDropDown(durationMaintenanceDropdown, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(mileageMaintenanceDropdown, dataObject.getPolicyCenter().getMaintenanceMileage());
    }

    private void validateEGVO_WL() {
        waitForElementVisible(durationWarrantyTxt);
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_MaintenanceDSC_4_50() {
        validateVehicleAgeLimit(10);
        validateMileageLimit("200.000");
        setDropDown(durationMaintainTxt, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration(), 2);
    }

    private void validateEGVO_Premium2_100() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_Premium5_150() {
        String regDate = dataObject.getPolicyCenter().getRegDate();
        int years = DateHelper.getNoOfYears(regDate.replace("/", "-"));
        long mileage = dataObject.getPolicyCenter().getMileage();
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        if ((years > 2 && years <= 4) && (mileage > 60000 && mileage <= 150000)) {
            setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
        } else if ((years == 5) && mileage <= 150000) {
            setDropDown(durationWarrantyTxt, "36");
        }
    }

    private void validateEGVO_Spoticar_Premium() {
        validateVehicleAgeLimit(10);
        validateMileageLimit("200.000");
        setDropDown(durationMaintainTxt, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration(), 2);
        setDropDown(mileageMaintainTxt, dataObject.getPolicyCenter().getMaintenanceMileage());
    }

    private void validateEGVO_Advanced_10_200() {
        validateVehicleAgeLimit(10);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_Maintenance_ARC() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationMaintAlfaRomeo, dataObject.getPolicyCenter().getMaintenanceDuration());
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
        clearText(maintenanceMileageTextbox);
        sendText(maintenanceMileageTextbox, dataObject.getPolicyCenter().getMaintenanceMileage());

        click(assistanceCbx);
        clickPageBody();
    }

    private void validateEGVO_ARC() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    private void validateEGVO_ARC_Assistance() {
        validateVehicleAgeLimit(9);
        validateMileageLimit("200.000");
        setDropDown(durationWarrantyTxt, dataObject.getPolicyCenter().getWarrantyDuration());
        click(assistanceCbx);
        clickPageBody();
    }

    private void validateEGVO_Garanzia(Dropdown dropdown) {
        setDropDown(dropdown, dataObject.getPolicyCenter().getWarrantyDuration());
    }

    public void clickQuoteTab() {
        click(quoteLeftPanelButton);
        waitForElementVisible(policyFileQuoteTitle);
    }

    public void goToRatingWorksheet() {
        click(ratingWorksheetButton);
        waitForElementVisible(ratingWsTable);
        if (worksheetPage == null) {
            worksheetPage = new WorksheetPage();
        }
    }

    public void expandAllNodes() {
        waitForPageLoadComplete(500);
        click(expandAllButton);
    }

    public String getPrice(String coverageTerm) {
        Label priceValue = Label.xpath("(((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//span[text()='BaseRate'])[1]/../../following-sibling::td)[1]/div");
        return getText(priceValue);
    }

    public String getBaseMargin(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Base Margin')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getDurationCoefficient(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Duration')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getManagementFee(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Management Fee')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getCommissions(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Commission')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getCommissionJV(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='"
                + coverageTerm
                + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Commission Standalone') or contains(text(),'table:Commission JV')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getTaxes(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Tax')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getLossRatio(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Loss Ratio')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getCombinedVariable(String coverageTerm) {
        Label td = Label.xpath("((//span[text()='" + coverageTerm + "']/ancestor::table)[3]/following-sibling::table//div[text()='combinedVariable' or contains(text(),'Combined Variable')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        if (value.contains("=")) {
            return value.split("=")[1].trim();
        } else {
            return value;
        }
    }

    public boolean validateCRSDisplay() {
        try {
            return isElementDisplayed(clientRelationServiceTxt, 5);
        } catch (NoSuchElementException e) {
            logger.info("Client Relation Service doesn't display");
            return false;
        }
    }

    public String getWarrantyWaitingPeriod(boolean dropdown) {
        if (!dropdown) {
            waitForElementVisible(waitingPeriod);
            waitForTextNotToBe(waitingPeriod, "");
            return getText(waitingPeriod);
        } else {
            waitForElementVisible(warrantyDurationDropdown);
            waitForAttributeToBeNotEmpty(warrantyDurationDropdown, "value");
            return getAttributeValue(warrantyDurationDropdown, "value");
        }
    }

    public String getClientRelationServicePrice() {
        Label td = Label.xpath("((//span[text()='" + ClientRelationServicePremium + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:ClientRelationService')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public String getClientRelationServiceTaxes() {
        Label td = Label.xpath("((//span[text()='" + ClientRelationServiceTaxes + "']/ancestor::table)[3]/following-sibling::table//div[contains(text(),'table:Tax')]/../following-sibling::td)[2]/div");
        String value = getText(td);
        return value.split("=")[1].trim();
    }

    public void saveDraft() {
        click(saveDraftButton);
        waitForPageLoadComplete();
        waitForTextNotToBe(createdByCell, "");
    }

    public void clickNextButton() {
        click(polInfoNextButton);
    }

    public boolean makeTypeListContainsOnly(Makes make) {
        boolean result;
        click(makeDdTxt);
        List<WebElement> availableMakes = findElements(makeTypeList);
        availableMakes.remove(0); //Remove the <none> element
        logger.info("Number of available makes is " + availableMakes.size());
        result = availableMakes.size() == 1 && getText(availableMakes.get(0)).equals(make.toString());
        clickPageBody();
        return result;
    }

    public boolean coverageDropdownContainsOnly(String partialText) {
        click(coverageTypeDdTxt);
        List<WebElement> availableTypes = findElements(coverageTypeList);
        availableTypes.remove(0); //Remove the <none> element
        logger.info("Number of available coverage type is " + availableTypes.size());
        boolean result = true;
        for (WebElement availableType : availableTypes) {
            logger.info("There's coverage type: " + getText(availableType));
            String typeValue = getText(availableType);
            result &= typeValue.contains(partialText);
        }
        addStepInfoToReport("Check if every available coverage type contains this text: " + partialText);
        clickPageBody();
        return result;
    }

    public void waitForSummaryScreen() {
        waitForElementVisible(policySummaryScreen);
        addStepInfoToReport("Wait for policy summary screen display");
    }

    public void goToMostRecentSubmissionScreen() {
        click(policyTab);
        waitForPageLoadComplete(500);
    }

    public String getManagementTypeValue() {
        return getText(managementTypeLbl);
    }

    public boolean verifyRate() {
        RateTable rateTable = null;
        for (Country country : countries) {
            if (country.getCountry().equals(getCurrentCountry())) {
                Rating rate = country.getRating();
                switch (dataObject.getPolicyCenter().getAdminLabel()) {
                    case SPOTICAR -> rateTable = rate.getSpoticar();
                    case ARC -> rateTable = rate.getArc();
                    case DSC -> rateTable = rate.getDsc();
                    case LC -> rateTable = rate.getLancia();
                    case WHITELABEL -> rateTable = rate.getWhitelabel();
                }
            }
        }
        boolean result = worksheetPage.verifyRating(rateTable);
        if (result) {
            logger.info("Rating and price validation successfully");
            addStepInfoToReport("Rating and price validation successfully");
        } else {
            logger.warn("Rating and price validation is failed");
            addStepInfoToReport("Rating and price validation failed");
        }
        return result;
    }

    public void viewUpdatedEGVOPolicy() {
        click(changeQuoteButton);
        click(wizardNextButton);
        click(changeIssuePolicyButton);
        switchToActiveElement();
        closePopup();
        waitForElementVisible(jobCompletedScreen);
        Link policyLink = Link.xpath("//div[contains(text(),'" + dataObject.getPolicyNumber() + "')]");
        click(policyLink);
        waitForElementVisible(policySummaryScreen);
    }

    public void clickPUWVehiclesTab() {
        click(puwVehiclesLeftPanelButton);
        waitForElementVisible(puwVehiclesTitle);
    }

    public String getMakerWarrantyEndDate() {
        waitForTextNotNull(makerWarrantyEndDateValue);
        return getText(makerWarrantyEndDateValue);
    }

    public String getWarrantyEndDateFromField() {
        return getAttributeValue(warrantyEndDateTxt, "value");
    }

    public String getSparePartsReimbursementLabel() {
        waitForElementVisible(sparePartsLabel);
        waitForTextNotNull(sparePartsLabel);
        return getText(sparePartsLabel);
    }

    public int getReimbursementPercentageValue() {
        waitForTextNotNull(sparePartsValue);
        return Integer.parseInt(getText(sparePartsValue).trim());
    }

    public boolean modelSelectorNotContains(String[] models) {
        click(modelTxt);
        List<WebElement> modelList = findElements(modelTypeList);
        List<String> currentModelValues = new ArrayList<>();
        for (WebElement model : modelList) {
            currentModelValues.add(getText(model));
        }
        clickPageBody();
        return Collections.disjoint(currentModelValues, Arrays.asList(models));
    }

    public String getCurrentOfferLabel() {
        waitForElementVisible(offerLabel);
        waitForTextNotNull(offerLabel);
        return getText(offerLabel);
    }

    public String getCurrentOfferLevel() {
        waitForElementVisible(offerLevel);
        waitForTextNotNull(offerLevel);
        return getText(offerLevel);
    }

    public ComplaintsActivity viewCurrentActivity(int activityIndex) {
        waitForElementVisible(currentActivities);
        click(findElements(activitySubject).get(activityIndex));
        ComplaintsActivity activity = new ComplaintsActivity();
        activity.setSubject(getText(subjectLabel));
        activity.setPriority(getText(priorityLabel));
        activity.setEscalationDate(getText(escalationDateLabel));
        activity.setComplaintTitle(getText(descriptionLabel));
        activity.setAssignTo(getText(assignedToLabel));
        activity.setComplaintsId(getText(complaintsId));
        activity.getNewNote().setRelatedTo(getText(relatedToLabel));
        activity.getNewNote().setComplainantName(getText(complainantName));
        activity.setDateReceived(getText(dateReceivedLabel));
        return activity;
    }
}
