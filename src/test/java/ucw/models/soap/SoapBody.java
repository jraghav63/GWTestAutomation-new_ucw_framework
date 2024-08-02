package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SoapBody {
    @JacksonXmlProperty(localName = "setServiceQuotePartsandOpt")
    private SetServiceQuotePartsandOpt setServiceQuotePartsandOpt;
}
