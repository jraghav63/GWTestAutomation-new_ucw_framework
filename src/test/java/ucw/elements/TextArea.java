package ucw.elements;

import org.openqa.selenium.By;

public class TextArea extends CustomElement {

    public TextArea(By by) {
        super(by);
    }

    public static TextArea id(String id) {
        return new TextArea(By.id(id));
    }

    public static TextArea xpath(String xpathString) {
        return new TextArea(By.xpath(xpathString));
    }
}
