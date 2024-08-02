package ucw.elements;

import org.openqa.selenium.By;

public class Table extends CustomElement {
    public Table(By by) {
        super(by);
    }

    public static Table id(String id) {
        return new Table(By.id(id));
    }

    public static Table xpath(String xpathString) {
        return new Table(By.xpath(xpathString));
    }
}
