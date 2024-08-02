package ucw.exceptions;

import org.testng.SkipException;

public class BillingCenterLoginException extends SkipException {
    public BillingCenterLoginException(String msg) {
        super(msg);
    }
}
