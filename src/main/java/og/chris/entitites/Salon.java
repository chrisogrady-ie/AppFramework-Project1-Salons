package og.chris.entitites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {
    private int id;
    private String name, address, phone, daysOpen;
}
