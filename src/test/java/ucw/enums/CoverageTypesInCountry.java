package ucw.enums;

import java.util.HashMap;
import java.util.Map;

import static ucw.enums.Countries.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.*;

public class CoverageTypesInCountry {
    public static final Map<Countries, Map<String, String[]>> types = new HashMap<>();
    public static final Map<String, String[]> franceLabels = new HashMap<>();
    public static final Map<String, String[]> spainLabels = new HashMap<>();
    public static final Map<String, String[]> italyLabels = new HashMap<>();
    public static final Map<String, String[]> germanyLabels = new HashMap<>();
    static {
        franceLabels.put(SPOTICAR, new String[] {
                GVOPremium2_60,
                GVOPremium5_100_12,
                GVOPremium5_100_24,
                GVOPremium7_150_12,
                GVOPremium7_150_24,
                GVOAdvanced10_200_8,
                GVOAdvanced10_200_12,
                GVOEssential15_200_6,
                GVOEssential15_200_12
        });

        franceLabels.put(ARC, new String[] {
                GVOARCertfied4_80,
                GVOARCertfied7_150
        });

        franceLabels.put(DSC, new String[] {
                GVODSC4_80,
                GVODSC7_150
        });

        franceLabels.put(WHITELABEL, new String[] {
                GVOCarePremium2_60,
                GVOCarePremium7_150_12M,
                GVOCarePremium7_150_24M,
                GVOCarePlus10_200,
                GVOCare15_200
        });
    }

    static {
        spainLabels.put(SPOTICAR, new String[] {
                GVOPremium2_60,
                GVOPremium2_100,
                GVOPremium5_150,
                GVOPremium7_150,
                GVOPremium7_200,
                GVOAdvanced10_150,
                GVOAdvanced10_200
        });
        spainLabels.put(ARC, new String[] {
                GVOARC4_80,
                GVOARC7_150
        });
        spainLabels.put(DSC, new String[] {
                GVODSC4_80,
                GVODSC7_150
        });
    }

    static {
        italyLabels.put(SPOTICAR, new String[] {
                GaranziaSpoticarPremium12, GaranziaSpoticarPremium24,
                GaranziaSpoticarAdvanced12, GaranziaSpoticarAdvanced24,
                GaranziaSpoticarEssential1_12
        });
        italyLabels.put(ARC, new String[] {
                GaranziaARC4_80_24M,
                GaranziaARC7_150_12M,
                GaranziaARC7_150_24M
        });
        italyLabels.put(DSC, new String[] {
                GaranziaDS4_80_24M,
                GaranziaDS7_150_12M,
                GaranziaDS7_150_24M
        });
        italyLabels.put(LC, new String[] {
                GaranziaLancia4_80_24M,
                GaranziaLancia7_150_12M,
                GaranziaLancia7_150_24M
        });
    }

    static {
        germanyLabels.put(SPOTICAR, new String[] {
                GWSpoticarPremium1_12M, GWSpoticarPremium1_24M,
                GWSpoticarPremium2_12M, GWSpoticarPremium2_24M,
                GWSpoticarAdvanced12M, GWSpoticarAdvanced24M
        });
    }

    static {
        types.put(France, franceLabels);
        types.put(Spain, spainLabels);
        types.put(Italy, italyLabels);
        types.put(Germany, germanyLabels);
    }
}
