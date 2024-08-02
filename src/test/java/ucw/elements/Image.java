package ucw.elements;

import org.openqa.selenium.By;

public class Image extends CustomElement {

    public Image(By by) {
        super(by);
    }

    public static Image id(String id) {
        return new Image(By.id(id));
    }

    public static Image xpath(String xpathString) {
        return new Image(By.xpath(xpathString));
    }
}
