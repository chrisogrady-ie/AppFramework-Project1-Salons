package og.chris.dao;

import og.chris.entitites.Salon;

import java.util.List;
import java.util.Optional;

public interface SalonDao {
    int count();
    List<Salon> findAll(); // search all
    Optional<Salon> findById(int id); //search, primary key
    boolean deleteById(int id);
    void save(Salon salon);
    boolean editName(String newName, int id);
    boolean editDaysOpen(String daysOpen, int id);
    List<Salon> findSalonByName(String searchName);
    List<Salon> findSalonByNumDaysOpen(String numDays);
}
