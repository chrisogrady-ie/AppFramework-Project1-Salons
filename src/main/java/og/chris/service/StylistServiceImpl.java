package og.chris.service;

import og.chris.dao.StylistDao;
import og.chris.dao.dto.Contract;
import og.chris.entitites.Stylist;
import og.chris.service.exceptions.SalonMalformedException;
import og.chris.service.exceptions.StylistMalformedException;
import og.chris.service.exceptions.StylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StylistServiceImpl implements StylistService{

    @Autowired
    StylistDao stylistDao;

    @Override
    public int count() {
        return stylistDao.count();
    }

    @Override
    public List<Stylist> findAllInSalon(int salonId) throws StylistNotFoundException {
        //if (stylistDao.findById(salonId).isEmpty())
         //   throw new StylistNotFoundException("Stylist with id " + salonId + "does not exist");
        return stylistDao.findAllInSalon(salonId);
    }

    @Override
    public Optional<Stylist> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) throws StylistNotFoundException {
        if(stylistDao.findById(id).isEmpty())
            throw new StylistNotFoundException("Stylist not found");
        return stylistDao.deleteById(id);
    }

    @Override
    public Optional<Stylist> save(Stylist stylist) throws StylistMalformedException {
        if(stylist.getStylistName().isBlank())
            throw new StylistMalformedException("Stylist name must bit be blank");
        if(stylist.getSalary() < 0)
            throw new StylistMalformedException("Stylist salary must be positive");
        stylistDao.save(stylist);
        return stylistDao.findById(stylist.getStylistId());
    }

    @Override
    public boolean moveStylist(int stylistId, int salonId) throws StylistNotFoundException {
        if(stylistDao.findById(stylistId).isEmpty())
            throw new StylistNotFoundException("Stylist not found");
        return stylistDao.moveStylist(stylistId, salonId);
    }

    @Override
    public int averageStylistSalary(int salonId) {
        return stylistDao.averageStylistSalary(salonId);
    }

    @Override
    public List<Contract> findAllWithSalon() {
        return stylistDao.findAllWithSalon();
    }
}
