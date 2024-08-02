package ucw.models.testdata;

import lombok.Data;

@Data
public class BillingCenter {
    private String accountName, type, billingPlan, delinquencyPlan, paymentAllocation,
            firstName, lastName, fullName, tags, companyName, accountNoBilling;
    private int dayOfMonth;
    private double costChange;
}
