package ucw.models.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class SerQuoteData {
    @JacksonXmlProperty(localName = "Operations")
    private List<SBEntry> operations;
    @JacksonXmlProperty(localName = "Parts")
    private List<SBEntry> parts;
    @JacksonXmlProperty(localName = "PolicyNumber")
    private String policyNumber;
    @JacksonXmlProperty(localName = "RemiseHTTotal")
    private String remiseHTTotal;
    @JacksonXmlProperty(localName = "RemiseHT_MO")
    private String remiseHTMO;
    @JacksonXmlProperty(localName = "RemiseHT_PR")
    private String remiseHTPR;
    @JacksonXmlProperty(localName = "TVATotalHT_MO")
    private String tVATotalHTMO;
    @JacksonXmlProperty(localName = "TVATotalHT_PR")
    private String tVATotalHTPR;
    @JacksonXmlProperty(localName = "TVATotalHT_Total")
    private String tVATotalHTTotal;
    @JacksonXmlProperty(localName = "TotalHTTotal")
    private String totalHTTotal;
    @JacksonXmlProperty(localName = "TotalHT_MO")
    private String totalHTMO;
    @JacksonXmlProperty(localName = "TotalHT_PR")
    private String totalHTPR;
    @JacksonXmlProperty(localName = "TotalTTC_MO")
    private String totalTTCMO;
    @JacksonXmlProperty(localName = "TotalTTC_PR")
    private String totalTTCPR;
    @JacksonXmlProperty(localName = "TotalTTC_Total")
    private String totalTTCTotal;
    @JacksonXmlProperty(localName = "VIN")
    private String vin;
}
