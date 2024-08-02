package ucw.models.rating;

import lombok.Data;
import java.util.List;

@Data
public class BaseRate {
    private String type, value;
    private List<CarPower> carPowers;
    private List<Model> models;
}
