package og.chris.service;

import og.chris.dao.dto.Contract;
import og.chris.entitites.Stylist;
import og.chris.service.exceptions.StylistMalformedException;
import og.chris.service.exceptions.StylistNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StylistService {
    int count();
    List<Stylist> findAllInSalon(int salonId) throws StylistNotFoundException;
    Optional<Stylist> findById(int id);
    boolean deleteById(int id) throws StylistNotFoundException;
    Optional<Stylist> save(Stylist stylist) throws StylistMalformedException;
    boolean moveStylist(int stylistId, int salonId) throws StylistNotFoundException;
    int averageStylistSalary(int salonId);
    //Get all stylists along with the name of the salon for which they work - use a record.
    List<Contract> findAllWithSalon();
}
