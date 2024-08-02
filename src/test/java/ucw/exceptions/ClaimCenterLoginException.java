package ucw.exceptions;

import org.testng.SkipException;

public class ClaimCenterLoginException extends SkipException {
    public ClaimCenterLoginException(String msg) {
        super(msg);
    }
}
