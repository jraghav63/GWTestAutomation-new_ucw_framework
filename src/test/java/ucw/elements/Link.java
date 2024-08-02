package ucw.elements;

import org.openqa.selenium.By;

public class Link extends CustomElement {

    public Link(By by) {
        super(by);
    }

    public static Link id(String id) {
        return new Link(By.id(id));
    }

    public static Link xpath(String xpathString) {
        return new Link(By.xpath(xpathString));
    }
}
