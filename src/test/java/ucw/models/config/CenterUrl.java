package ucw.models.config;

import lombok.Data;
import ucw.enums.Environments;

@Data
public class CenterUrl {
    private Environments env;
    private String policyCenterUrl, billingCenterUrl,
            contactManagerUrl, claimCenterUrl, portalUrl, producerCode, accountNoBilling;
    private ServiceBox serviceBox;
}
