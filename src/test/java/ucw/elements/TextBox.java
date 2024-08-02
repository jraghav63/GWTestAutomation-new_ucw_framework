package ucw.elements;

import org.openqa.selenium.By;

public class TextBox extends CustomElement {

    public TextBox(By by) {
        super(by);
    }

    public static TextBox id(String id) {
        return new TextBox(By.id(id));
    }

    public static TextBox xpath(String xpathString) {
        return new TextBox(By.xpath(xpathString));
    }

    public static TextBox name(String nameString) {
        return new TextBox(By.name(nameString));
    }
}
