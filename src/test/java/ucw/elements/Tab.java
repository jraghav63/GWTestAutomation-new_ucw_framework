package ucw.elements;

import org.openqa.selenium.By;

public class Tab extends CustomElement {

    public Tab(By by) {
        super(by);
    }

    public static Tab id(String id) {
        return new Tab(By.id(id));
    }

    public static Tab xpath(String xpathString) {
        return new Tab(By.xpath(xpathString));
    }
}
