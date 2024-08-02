package ucw.elements;

import org.openqa.selenium.By;

public class Title extends CustomElement {

    public Title(By by) {
        super(by);
    }

    public static Title id(String id) {
        return new Title(By.id(id));
    }

    public static Title xpath(String xpathString) {
        return new Title(By.xpath(xpathString));
    }
}
