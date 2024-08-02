package ucw.elements;

import org.openqa.selenium.By;

public class Items extends CustomElement {

    public Items(By by) {
        super(by);
    }

    public static Items id(String id) {
        return new Items(By.id(id));
    }

    public static Items xpath(String xpathString) {
        return new Items(By.xpath(xpathString));
    }
}
