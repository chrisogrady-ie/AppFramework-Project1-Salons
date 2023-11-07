package og.chris.entitites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stylist {
    private int stylistId;
    private String stylistName, phone;
    private int salary, salonId;
}
