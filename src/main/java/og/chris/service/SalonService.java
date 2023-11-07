package og.chris.service;

import og.chris.entitites.Salon;
import og.chris.service.exceptions.SalonMalformedException;
import og.chris.service.exceptions.SalonNotFoundException;

import java.util.List;

public interface SalonService {
    int count();
    Salon findById(int id) throws SalonNotFoundException;
    List<Salon> findAll();
    boolean deleteById (int id) throws SalonNotFoundException;
    Salon addSalon(Salon salon);
    boolean changeName(String newName, int id);
    void changeOpenDays(String newSchedule, int id) throws SalonMalformedException, SalonNotFoundException;
    List<Salon> findSalonByName(String searchName);
    List<Salon> findSalonsOpenAllDays(String searchDays) throws SalonMalformedException;
}
