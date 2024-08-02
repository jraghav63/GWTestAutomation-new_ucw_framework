package ucw.models.rating;

import lombok.Data;
import java.util.List;

@Data
public class CoverageTerm {
    private List<Price> prices;
    private List<Duration> durations;
    private List<ManagementFee> managementFees;
    private List<Commission> commissions;
    private List<Tax> taxes;
    private List<WaitingPeriod> waitingPeriods;
    private List<CombinedVariable> combinedVariables;
    private List<LossRatio> lossRatios;
}
