package ucw.models.testdata;

import lombok.Data;

@Data
public class Portal {
    private String producerCode, energyType, coverageType, make, model, licensePlate, regDate, warrantyKM,
            firstName, lastName, company, personalQuoteNo, commQuoteNo, personPTAccount, commPTAccount, quoteNo, fullName;
    private int carPower, years;
    private long mileage, vehicleMileage;
    private ComplaintsActivity complaintsActivity;
}
