package ucw.exceptions;

import org.testng.SkipException;

public class PolicyCenterLoginException extends SkipException {
    public PolicyCenterLoginException(String msg) {
        super(msg);
    }
}
