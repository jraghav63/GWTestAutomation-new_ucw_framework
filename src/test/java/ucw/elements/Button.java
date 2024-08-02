package ucw.elements;

import org.openqa.selenium.By;

public class Button extends CustomElement {

    public Button(By by) {
        super(by);
    }

    public static Button id(String id) {
        return new Button(By.id(id));
    }

    public static Button xpath(String xpathString) {
        return new Button(By.xpath(xpathString));
    }

    public static Button partialLinkText(String linkText) {
        return new Button(By.partialLinkText(linkText));
    }

    public static Button name(String nameString) {
        return new Button(By.name(nameString));
    }

    public static Button cssSelector(String nameString) {
        return new Button(By.cssSelector(nameString));
    }
}
