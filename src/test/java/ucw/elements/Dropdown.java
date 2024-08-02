package ucw.elements;

import org.openqa.selenium.By;

public class Dropdown extends CustomElement {

    public Dropdown(By by) {
        super(by);
    }

    public static Dropdown id(String id) {
        return new Dropdown(By.id(id));
    }

    public static Dropdown xpath(String xpathString) {
        return new Dropdown(By.xpath(xpathString));
    }
}
