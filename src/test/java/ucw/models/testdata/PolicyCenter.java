package ucw.models.testdata;

import lombok.Data;

@Data
public class PolicyCenter {
    private String adminLabel, dateOfBirth, primaryEmail, addressType, productName, managementType, vehicleType,
            vin, vinChange, regDate, warrantyEndDate, energyType, coverageType, make, model,
            vehicleSpecific, licensePlate, regDateChange, energyChange, billingMethod, reinstateEffectiveDate,
            egvoCoverageType, prodCodeTypeForEGVOStandalone, cancelEffectiveDate, mandetSEPA, commPolicyNo,
            dealerNameUpdated, dealerAddress, dealerAddress2, dealerCity, dealerProvince,
            userFirstName, userMiddleName, userLastName;
    private int carPower, immobilizationDays, duration, replacementPercentage,
            collateralVehicle, collateralTarget, expectedVOP, lossRatio, maintenanceDuration,
            warrantyDuration, authorizedWeight;
    private long mileage, mileageUpdated, warrantyKM, maintenanceMileage;
    private double costChange;
}
