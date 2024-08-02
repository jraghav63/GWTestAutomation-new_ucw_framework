package ucw.models.testdata;

import lombok.Data;

@Data
public class ClaimCenter {
    private String lossCause, companyName, firstName, lastName, fullName, tags, claimNo, email,
            location, street, vat, vatInUI, commercialName, typeCheck;

    private long vehicleMileage;
    private int quoteAmount, quoteAmount1, quoteRef, invoiceRef, paymentAmount, invoiceAmount1;
}
