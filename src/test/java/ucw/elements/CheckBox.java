package ucw.elements;

import org.openqa.selenium.By;

public class CheckBox extends CustomElement {

    public CheckBox(By by) {
        super(by);
    }

    public static CheckBox id(String id) {
        return new CheckBox(By.id(id));
    }

    public static CheckBox xpath(String xpathString) {
        return new CheckBox(By.xpath(xpathString));
    }
}
