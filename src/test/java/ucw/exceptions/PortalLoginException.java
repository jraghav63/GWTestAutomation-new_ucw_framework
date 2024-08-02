package ucw.exceptions;

import org.testng.SkipException;

public class PortalLoginException extends SkipException {
    public PortalLoginException(String msg) {
        super(msg);
    }
}
