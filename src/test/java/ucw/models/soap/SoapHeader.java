package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SoapHeader {
    @JacksonXmlProperty(localName = "gw_locale")
    private String gwLocale;
    @JacksonXmlProperty(localName = "gw_language")
    private String gwLanguage;
    @JacksonXmlProperty(localName = "authentication")
    private SoapAuthentication authentication;
}
