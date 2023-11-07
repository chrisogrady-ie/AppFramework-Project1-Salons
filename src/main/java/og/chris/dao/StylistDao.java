package og.chris.dao;

import og.chris.dao.dto.Contract;
import og.chris.entitites.Stylist;

import java.util.List;
import java.util.Optional;

public interface StylistDao {
    int count();
    List<Stylist> findAllInSalon(int salonId);
    Optional<Stylist> findById(int id);
    boolean deleteById(int id);
    void save(Stylist stylist);
    boolean moveStylist(int stylistId, int salonId);
    int averageStylistSalary(int salonId);
    //Get all stylists along with the name of the salon for which they work - use a record.
    List<Contract> findAllWithSalon();
}
