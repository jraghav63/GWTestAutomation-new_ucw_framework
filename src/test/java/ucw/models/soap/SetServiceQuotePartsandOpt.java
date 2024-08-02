package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SetServiceQuotePartsandOpt {
    @JacksonXmlProperty(localName = "serQuoteData")
    private SerQuoteData serQuoteData;
}
