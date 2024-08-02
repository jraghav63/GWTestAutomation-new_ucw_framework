package ucw.elements;

import org.openqa.selenium.By;

public class Label extends CustomElement {

    public Label(By by) {
        super(by);
    }

    public static Label id(String id) {
        return new Label(By.id(id));
    }

    public static Label xpath(String xpathString) {
        return new Label(By.xpath(xpathString));
    }

    public static Label cssSelector(String selector) {
        return new Label(By.cssSelector(selector));
    }
}
