package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@JacksonXmlRootElement(localName = "Envelope")
@Data
public class Envelope {
    @JacksonXmlProperty(localName = "Header")
    private SoapHeader header;
    @JacksonXmlProperty(localName = "Body")
    private SoapBody body;
}
