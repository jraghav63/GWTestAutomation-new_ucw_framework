package ucw.elements;

import org.openqa.selenium.By;

public class Form extends CustomElement {

    public Form(By by) {
        super(by);
    }

    public static Form id(String id) {
        return new Form(By.id(id));
    }

    public static Form xpath(String xpathString) {
        return new Form(By.xpath(xpathString));
    }

    public static Form tagName(String name) {
        return new Form(By.tagName(name));
    }

    public static Form cssSelector(String name) {
        return new Form(By.cssSelector(name));
    }
}
