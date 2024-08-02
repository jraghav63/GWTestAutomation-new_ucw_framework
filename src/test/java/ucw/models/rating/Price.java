package ucw.models.rating;

import lombok.Data;

import java.util.List;

@Data
public class Price {
    private String managementType, duration, value;
    private List<BaseRate> baseRates;
}
