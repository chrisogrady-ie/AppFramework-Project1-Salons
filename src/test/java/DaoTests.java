import og.chris.Config;
import og.chris.dao.SalonDao;
import og.chris.dao.StylistDao;
import og.chris.entitites.Salon;
import og.chris.entitites.Stylist;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class DaoTests {
    @Autowired
    SalonDao salonDao;
    @Autowired
    StylistDao stylistDao;

    @Test
    @Order(1)
    public void testSalonCount(){
        Assertions.assertEquals(3, salonDao.count());
    }

    @Test
    @Order(2)
    public void testSalonFIndAll(){
        Assertions.assertEquals(3, salonDao.findAll().size());
    }

    @Test
    @Order(3)
    public void testSalonFindById(){
        Optional<Salon> salon = salonDao.findById(1);
        Assertions.assertTrue(salon.isPresent());
        Assertions.assertEquals("SalonA", salon.get().getName());
    }

    @Test
    @Order(4)
    public void testSalonFindByIdNotFound(){
        Optional<Salon> salonOptional = salonDao.findById(9999);
        Assertions.assertFalse(salonOptional.isPresent());
    }


    @Test
    @Order(98)
    public void testDeleteById(){
        Assertions.assertTrue(salonDao.deleteById(1));
    }

    @Test
    @Order(99)
    public void testDeleteByIdNotFound(){
        Assertions.assertFalse(salonDao.deleteById(1000));
    }

    @Test
    @Order(5)
    public void testSave(){
        int oldCount = salonDao.count();
        Salon salon = new Salon(4, "salonInserted", "1213 highway", "09746389", "1111111");
        salonDao.save(salon);
        Assertions.assertEquals(oldCount+1, salonDao.count());
        Assertions.assertTrue(salonDao.findById(4).isPresent());
        Assertions.assertEquals("salonInserted", salonDao.findById(4).get().getName());
    }

    @Test
    @Order(6)
    public void testSaveClashPK(){
        Salon salon = new Salon(1, "salonInserted", "1213 highway", "09746389", "1111111");
        Assertions.assertThrows(DuplicateKeyException.class, ()->{
            salonDao.save(salon);
        });
    }

    @Test
    @Order(7)
    public void testChangeName(){
        Assertions.assertTrue(salonDao.editName("A new salon name", 2));
        Assertions.assertEquals("A new salon name", salonDao.findById(2).get().getName());
    }

    @Test
    @Order(8)
    public void testSalonFindByName(){
        List<Salon> salon = salonDao.findSalonByName("SalonA");
        Assertions.assertEquals(1, salon.size());
    }

    @Test
    @Order(8)
    public void testSalonChangeDaysOpen(){
        Assertions.assertTrue(salonDao.editDaysOpen("1011011", 2));
        Assertions.assertEquals("1011011", salonDao.findById(2).get().getDaysOpen());
    }

    @Test
    @Order(9)
    public void testSalonGetSalonsOpenEveryDay(){
        List<Salon> salon = salonDao.findSalonByNumDaysOpen("1111111");
        Assertions.assertEquals(1, salon.size());
    }

    @Test
    @Order(51)
    public void testStylistCount(){
        Assertions.assertEquals(4, stylistDao.count());
    }

    @Test
    @Order(52)
    public void testStylistFindById(){
        Optional<Stylist> stylist = stylistDao.findById(1);
        Assertions.assertTrue(stylist.isPresent());
        Assertions.assertEquals("Jean", stylist.get().getStylistName());
    }

    @Test
    @Order(53)
    public void testStylistFIndAllInSalon(){
        List<Stylist> stylists = stylistDao.findAllInSalon(1);
        Assertions.assertEquals(1, stylists.size());
    }

    @Test
    @Order(54)
    public void testStylistSave(){
        int oldCount = stylistDao.count();
        Stylist stylist = new Stylist(5, "Billy",
                "09746389", 12000, 2);
        stylistDao.save(stylist);
        Assertions.assertEquals(oldCount+1, stylistDao.count());
        Assertions.assertTrue(stylistDao.findById(5).isPresent());
        Assertions.assertEquals("Billy", stylistDao.findById(5).get().getStylistName());
    }

    @Test
    @Order(55)
    public void testStylistChangeSalon(){
        Assertions.assertTrue(stylistDao.moveStylist(1, 2));
        Assertions.assertEquals(2, stylistDao.findById(1).get().getSalonId());
    }

    @Test
    @Order(56)
    public void testAverageStylistSalary(){
        Assertions.assertEquals(200, stylistDao.averageStylistSalary(1));
    }

    @Test
    @Order(56)
    public void testFindAllWithSalon(){
        Assertions.assertEquals(4, stylistDao.findAllWithSalon());
    }

    @Test
    @Order(100)
    public void testDeleteStylistById(){
        Assertions.assertTrue(stylistDao.deleteById(1));
    }

}
