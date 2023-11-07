package og.chris.service;

import lombok.SneakyThrows;
import og.chris.dao.SalonDao;
import og.chris.entitites.Salon;
import og.chris.service.exceptions.SalonIdAlreadyExists;
import og.chris.service.exceptions.SalonMalformedException;
import og.chris.service.exceptions.SalonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceImpl implements SalonService{

    @Autowired
    SalonDao sDao;

    @Override
    public int count() {
        return sDao.count();
    }

    @Override
    @SneakyThrows
    public Salon findById(int id) throws SalonNotFoundException {
        return sDao.findById(id).orElseThrow(()-> new SalonNotFoundException("Salon with id "+id+" was not found"));
    }

    @Override
    public List<Salon> findAll() {
        return sDao.findAll();
    }

    @Override
    public boolean deleteById(int id) throws SalonNotFoundException {
        if (sDao.findById(id).isEmpty())
            throw new SalonNotFoundException("Salon with id " + id + "does not exist");
        return sDao.deleteById(id);
    }

    @Override
    @SneakyThrows
    public Salon addSalon(Salon salon) {
        if(salon.getName().isBlank())
            throw new SalonMalformedException("Salon name must bit be blank");
        if(salon.getAddress().isBlank())
            throw new SalonMalformedException("Salon address may not be blank");
        if(salon.getPhone().isBlank())
            throw new SalonMalformedException("Salon phone may not be blank");
        if(salon.getDaysOpen().compareTo("") == 0)
            throw new SalonMalformedException("Salon must be open at least 1 day a week");
        if(salon.getId() < 1)
            throw new SalonMalformedException("Salon id is invalid");
        if(sDao.findById(salon.getId()).isPresent())
            throw new SalonIdAlreadyExists("Salon with id " + salon.getId() + "already exists");
        sDao.save(salon);
        return sDao.findById(salon.getId()).get();
    }

    @Override
    @SneakyThrows
    public boolean changeName(String newName, int id) {
        if (newName.isBlank())
            throw new SalonMalformedException("Salon name must not be blank");
        if (sDao.findById(id).isEmpty())
            throw new SalonNotFoundException("Salon with id " + id + "does not exist");
        return sDao.editName(newName, id);
    }

    @Override
    public void changeOpenDays(String newSchedule, int id) throws SalonMalformedException, SalonNotFoundException {
        if (newSchedule.isBlank())
            throw new SalonMalformedException("Salon schedule must not be blank");
        if (sDao.findById(id).isEmpty())
            throw new SalonNotFoundException("Salon with id " + id + "does not exist");
        sDao.editDaysOpen(newSchedule, id);
    }

    @Override
    public List<Salon> findSalonByName(String searchName) {
        return sDao.findSalonByName(searchName);
    }

    @Override
    public List<Salon> findSalonsOpenAllDays(String searchDays) throws SalonMalformedException {
        if (searchDays.isBlank())
            throw new SalonMalformedException("Days must not be blank");
        return sDao.findSalonByNumDaysOpen(searchDays);
    }
}
