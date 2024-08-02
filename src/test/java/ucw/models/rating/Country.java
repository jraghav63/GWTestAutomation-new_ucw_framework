package ucw.models.rating;

import lombok.Data;
import ucw.enums.Countries;

@Data
public class Country {
    private Countries country;
    private Rating rating;
}
