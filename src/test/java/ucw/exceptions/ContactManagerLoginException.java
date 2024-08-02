package ucw.exceptions;

import org.testng.SkipException;

public class ContactManagerLoginException extends SkipException {
    public ContactManagerLoginException(String msg) {
        super(msg);
    }
}
