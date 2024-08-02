package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SBEntry {
    @JacksonXmlProperty(localName = "Comment")
    private String comment;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "Discount")
    private String discount;
    @JacksonXmlProperty(localName = "PriceTTC")
    private String priceTTC;
    @JacksonXmlProperty(localName = "Quantity")
    private String quantity;
    @JacksonXmlProperty(localName = "Ref")
    private String ref;
    @JacksonXmlProperty(localName = "UnitaryPrice")
    private String unitaryPrice;
    @JacksonXmlProperty(localName = "VAT")
    private String vat;
    @JacksonXmlProperty(localName = "WorklineTTC")
    private String worklineTTC;
    @JacksonXmlProperty(localName = "WorklineType")
    private String worklineType;
    @JacksonXmlProperty(localName = "isPart")
    private boolean part;
    private String descriptionStyle;
}
