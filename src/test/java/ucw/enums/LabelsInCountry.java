package ucw.enums;

import java.util.HashMap;
import java.util.Map;

import static ucw.enums.Countries.*;
import static ucw.enums.Labels.*;
import static ucw.enums.Labels.DSC;

public class LabelsInCountry {
    public static final Map<Countries, String[]> labels = new HashMap<>();
    static {
        labels.put(Germany, new String[]{SPOTICAR});
        labels.put(Italy,   new String[]{SPOTICAR, ARC, DSC, LC});
        labels.put(Spain,   new String[]{SPOTICAR, ARC, DSC});
        labels.put(France,  new String[]{SPOTICAR, ARC, DSC, WHITELABEL});
    }
}
