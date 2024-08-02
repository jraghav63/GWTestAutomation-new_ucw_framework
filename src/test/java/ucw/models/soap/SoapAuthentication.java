package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SoapAuthentication {
    @JacksonXmlProperty(localName = "username")
    private String username;
    @JacksonXmlProperty(localName = "password")
    private String password;
}
