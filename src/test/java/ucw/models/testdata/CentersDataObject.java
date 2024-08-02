package ucw.models.testdata;

import lombok.Data;
import ucw.enums.Countries;

@Data
public class CentersDataObject {
    private String contactLastName, contactFirstName,
            producerCode, companyName, accountNumber, policyNumber, fullName;
    private PolicyCenter policyCenter;
    private BillingCenter billingCenter;
    private ClaimCenter claimCenter;
    private ContactManager contactManager;
    private Portal portal;
    private Countries country;
}
