package ucw.pages.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.models.rating.*;

import static ucw.enums.CoverageTerms.*;
import static ucw.enums.Labels.*;

public class WorksheetPage extends PolicyPage {
    private static final Logger logger = LogManager.getLogger(WorksheetPage.class);
    private static String currentCoverageType, currentManagementType, currentVehicleSpecific, currentVehicleType, currentModel;
    private static int currentCarPower;

    private static RateTable rateTable;
    public boolean verifyRating(RateTable table) {
        boolean result = true;
        rateTable = table;
        currentCoverageType = dataObject.getPolicyCenter().getCoverageType();
        currentManagementType = dataObject.getPolicyCenter().getManagementType();
        currentVehicleSpecific = dataObject.getPolicyCenter().getVehicleSpecific();
        currentVehicleType = dataObject.getPolicyCenter().getVehicleType();
        currentModel = dataObject.getPolicyCenter().getModel();
        currentCarPower = dataObject.getPolicyCenter().getCarPower();
        result &= worksheetPage.verifyPrice();
        result &= worksheetPage.verifyManagementFee();
        result &= worksheetPage.verifyCommissions();
        result &= worksheetPage.verifyTaxes();
        result &= worksheetPage.verifyCombinedVariable();
        result &= worksheetPage.verifyLossRatio();
        return result;
    }

    public boolean verifyPrice() {
        boolean result = true;
        switch (dataObject.getPolicyCenter().getAdminLabel()) {
            case SPOTICAR -> result &= verifyPriceBaseOnCarPower();
            case ARC, DSC -> result &= verifyPriceBaseOnModel();
            case LC -> result &= verifyPriceBaseOnCoverageType();
        }
        String actualAssistancePrice = getPrice(AssistancePremium);
        for (Price expectedAssistancePrice : rateTable.getAssistance().getPrices()) {
            if (expectedAssistancePrice.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Assistance price: " + actualAssistancePrice + ", expected: " + expectedAssistancePrice.getValue());
                result &= actualAssistancePrice.equals(expectedAssistancePrice.getValue());
                break;
            }
        }
        return result;
    }

    public boolean verifyManagementFee() {
        boolean result = true;
        String actualManagementFee = getManagementFee(WarrantyManagementFee);
        String actualAssistanceFee = getManagementFee(AssistanceManagementFee);
        for (ManagementFee expectedWManagementFee : rateTable.getWarranty().getManagementFees()) {
            if (expectedWManagementFee.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Warranty management fee: " + actualManagementFee + ", expected: " + expectedWManagementFee.getValue());
                result &= actualManagementFee.equals(expectedWManagementFee.getValue());
                break;
            }
        }
        for (ManagementFee expectedAManagementFee : rateTable.getAssistance().getManagementFees()) {
            if (expectedAManagementFee.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Assistance management fee: " + actualAssistanceFee + ", expected: " + expectedAManagementFee.getValue());
                result &= actualAssistanceFee.equals(expectedAManagementFee.getValue());
                break;
            }
        }
        return result;
    }

    public boolean verifyCommissions() {
        boolean result = true;
        String actualWarrantyCommission = getCommissions(WarrantyCommission);
        String actualAssistanceCommission = getCommissions(AssistanceCommission);
        for (Commission expectedWarrantyCommission : rateTable.getWarranty().getCommissions()) {
            if (expectedWarrantyCommission.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Warranty commission: " + actualWarrantyCommission + ", expected: " + expectedWarrantyCommission.getValue());
                result &= actualWarrantyCommission.equals(expectedWarrantyCommission.getValue());
                break;
            }
        }

        for (Commission expectedAssistanceCommission : rateTable.getAssistance().getCommissions()) {
            if (expectedAssistanceCommission.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Assistance commission: " + actualAssistanceCommission + ", expected: " + expectedAssistanceCommission.getValue());
                result &= actualAssistanceCommission.equals(expectedAssistanceCommission.getValue());
                break;
            }
        }
        return result;
    }

    public boolean verifyTaxes() {
        boolean result = true;
        String actualWarrantyTaxes = getTaxes(WarrantyTaxes);
        String actualAssistanceTaxes = getTaxes(AssistanceTaxes);
        for (Tax expectedWarrantyTaxes : rateTable.getWarranty().getTaxes()) {
            if (expectedWarrantyTaxes.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Warranty taxes: " + actualWarrantyTaxes + ", expected: " + expectedWarrantyTaxes.getValue());
                result &= actualWarrantyTaxes.equals(expectedWarrantyTaxes.getValue());
                break;
            }
        }

        for (Tax expectedAssistanceTaxes : rateTable.getAssistance().getTaxes()) {
            if (expectedAssistanceTaxes.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Assistance taxes: " + actualAssistanceTaxes + ", expected: " + expectedAssistanceTaxes.getValue());
                result &= actualAssistanceTaxes.equals(expectedAssistanceTaxes.getValue());
                break;
            }
        }
        return result;
    }

    public boolean verifyCombinedVariable() {
        boolean result = false;
        switch (getCurrentCountry()) {
            case Spain -> {
                switch (dataObject.getPolicyCenter().getAdminLabel()) {
                    case SPOTICAR -> result = verifyCVBaseOnVehicleSpecific();
                    case ARC, DSC -> result = verifyCVBaseOnManagementType();
                }
            }
            case France, Italy -> result = verifyCVBaseOnManagementType();
            case Germany -> result = verifyCVBaseOnVehicleType();
        }
        return result;
    }

    private boolean verifyCVBaseOnVehicleSpecific() {
        boolean result = true;
        String actualWarrantyCV = getCombinedVariable(WarrantyPremium);
        for (CombinedVariable expectedWCV : rateTable.getWarranty().getCombinedVariables()) {
            if (expectedWCV.getManagementType().equals(currentManagementType) && expectedWCV.getVehicleSpecific().equals(currentVehicleSpecific)) {
                logger.info("Actual Warranty combined variable: " + actualWarrantyCV + ", expected: " + expectedWCV.getValue());
                result &= actualWarrantyCV.equals(expectedWCV.getValue());
                break;
            }
        }
        String actualAssistanceCV = getCombinedVariable(AssistancePremium);
        for (CombinedVariable expectedACV : rateTable.getAssistance().getCombinedVariables()) {
            if (expectedACV.getManagementType().equals(currentManagementType) && expectedACV.getVehicleSpecific().equals(currentVehicleSpecific)) {
                logger.info("Actual Assistance combined variable: " + actualAssistanceCV + ", expected: " + expectedACV.getValue());
                result &= actualAssistanceCV.equals(expectedACV.getValue());
                break;
            }
        }
        return result;
    }

    private boolean verifyCVBaseOnVehicleType() {
        String actualWarrantyCV = getCombinedVariable(WarrantyPremium);
        boolean result = true;
        for (CombinedVariable expectedWCV : rateTable.getWarranty().getCombinedVariables()) {
            if (expectedWCV.getManagementType().equals(currentManagementType) && expectedWCV.getVehicleType().equals(currentVehicleType)) {
                logger.info("Actual Warranty combined variable: " + actualWarrantyCV + ", expected: " + expectedWCV.getValue());
                result &= actualWarrantyCV.equals(expectedWCV.getValue());
                break;
            }
        }
        String actualAssistanceCV = getCombinedVariable(AssistancePremium);
        for (CombinedVariable expectedACV : rateTable.getAssistance().getCombinedVariables()) {
            if (expectedACV.getManagementType().equals(currentManagementType) && expectedACV.getVehicleType().equals(currentVehicleType)) {
                logger.info("Actual Assistance Combined variable: " + actualAssistanceCV + ", expected: " + expectedACV.getValue());
                result &= actualAssistanceCV.equals(expectedACV.getValue());
                break;
            }
        }
        return result;
    }

    private boolean verifyCVBaseOnManagementType() {
        boolean result = true;
        if (rateTable.getWarranty().getCombinedVariables() != null) {
            String actualWarrantyCV = getCombinedVariable(WarrantyPremium);
            for (CombinedVariable expectedWarrantyCV : rateTable.getWarranty().getCombinedVariables()) {
                if (expectedWarrantyCV.getManagementType().equals(currentManagementType)) {
                    logger.info("Actual Warranty combined variable: " + actualWarrantyCV + ", expected: " + expectedWarrantyCV.getValue());
                    result &= actualWarrantyCV.equals(expectedWarrantyCV.getValue());
                    break;
                }
            }
        } else if (rateTable.getAssistance().getCombinedVariables() != null) {
            String actualAssistanceCV = getCombinedVariable(AssistancePremium);
            for (CombinedVariable expectedAssistanceCV : rateTable.getAssistance().getCombinedVariables()) {
                if (expectedAssistanceCV.getManagementType().equals(currentManagementType)) {
                    logger.info("Actual Assistance combined variable: " + actualAssistanceCV + ", expected: " + expectedAssistanceCV.getValue());
                    result &= actualAssistanceCV.equals(expectedAssistanceCV.getValue());
                    break;
                }
            }
        }
        return result;
    }

    public boolean verifyLossRatio() {
        String actualAssistanceLossRatio = getLossRatio(AssistancePremium);
        for (LossRatio expectedAssistanceLossRatio : rateTable.getAssistance().getLossRatios()) {
            if (expectedAssistanceLossRatio.getManagementType().equals(currentManagementType)) {
                logger.info("Actual Assistance loss ratio: " + actualAssistanceLossRatio + ", expected: " + expectedAssistanceLossRatio.getValue());
                return actualAssistanceLossRatio.equals(expectedAssistanceLossRatio.getValue());
            }
        }
        return false;
    }

    private boolean verifyPriceBaseOnCarPower() {
        String actualWarrantyPrice = getPrice(WarrantyPremium);
        for (Price expectedWarrantyPrice : rateTable.getWarranty().getPrices()) {
            if (expectedWarrantyPrice.getManagementType().equals(currentManagementType)) {
                for (BaseRate expectedBR : expectedWarrantyPrice.getBaseRates()) {
                    if (expectedBR.getType().equals(currentCoverageType)) {
                        for (CarPower expectedCP : expectedBR.getCarPowers()) {
                            if (Integer.parseInt(expectedCP.getMin()) <= currentCarPower && Integer.parseInt(expectedCP.getMax()) >= currentCarPower) {
                                logger.info("Actual Warranty price: " + actualWarrantyPrice + ", expected: " + expectedCP.getValue());
                                return actualWarrantyPrice.equals(expectedCP.getValue());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean verifyPriceBaseOnModel() {
        String actualWarrantyPrice = getPrice(WarrantyPremium);
        for (Price expectedWarrantyPrice : rateTable.getWarranty().getPrices()) {
            if (expectedWarrantyPrice.getManagementType().equals(currentManagementType)) {
                for (BaseRate expectedBR : expectedWarrantyPrice.getBaseRates()) {
                    if (expectedBR.getType().equals(currentCoverageType)) {
                        for (Model expectedModel : expectedBR.getModels()) {
                            if (expectedModel.getName().equals(currentModel)) {
                                logger.info("Actual Warranty price: " + actualWarrantyPrice + ", expected: " + expectedModel.getValue());
                                return actualWarrantyPrice.equals(expectedModel.getValue());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean verifyPriceBaseOnCoverageType() {
        String actualWarrantyPrice = getPrice(WarrantyPremium);
        for (Price expectedWarrantyPrice : rateTable.getWarranty().getPrices()) {
            if (expectedWarrantyPrice.getManagementType().equals(currentManagementType)) {
                for (BaseRate expectedBR : expectedWarrantyPrice.getBaseRates()) {
                    if (expectedBR.getType().equals(currentCoverageType)) {
                        logger.info("Actual Warranty price: " + actualWarrantyPrice + ", expected: " + expectedBR.getValue());
                        return actualWarrantyPrice.equals(expectedBR.getValue());
                    }
                }
            }
        }
        return false;
    }
}
