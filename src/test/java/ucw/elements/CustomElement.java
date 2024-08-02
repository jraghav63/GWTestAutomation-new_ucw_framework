package ucw.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CustomElement extends By {
    private final By by;
    public CustomElement(By by) {
        this.by = by;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(by);
    }

    @Override
    public String toString() {
        return "Element: " + by.toString();
    }
}
