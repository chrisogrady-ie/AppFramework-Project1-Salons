import lombok.SneakyThrows;
import og.chris.Config;
import og.chris.entitites.Salon;
import og.chris.entitites.Stylist;
import og.chris.service.StylistService;
import og.chris.service.exceptions.*;
import og.chris.service.SalonService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ServiceTests {
    @Autowired
    SalonService salonService;

    @Autowired
    StylistService stylistService;

    @Test
    @Order(1)
    public void testSalonCount(){
        Assertions.assertEquals(3, salonService.count());
    }

    @Test
    @Order(2)
    public void testSalonFindAll(){
        Assertions.assertEquals(3, salonService.findAll().size());
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void testSalonFindByIdFound(){
        Salon salon = salonService.findById(1);
        Assertions.assertNotNull(salon);
        Assertions.assertEquals("SalonA", salon.getName());
    }

    @Test
    @Order(4)
    public void testSalonByIdNotFound(){
        Assertions.assertThrows(SalonNotFoundException.class, ()-> {
            salonService.findById(9999);});
    }

//    @Test
//    @Order(5)
//    public void testDeleteByIdNotFound(){
//        Assertions.assertThrows(SalonNotFoundException.class, ()->{
//            salonService.deleteById(1);
//        });
//    }

    @Test
    @Order(6)
    @SneakyThrows
    public void testDeleteById(){
        int idNum = 3;
        Assertions.assertNotNull(salonService.findById(idNum));
        salonService.deleteById(idNum);
        Assertions.assertThrows(SalonNotFoundException.class, ()-> {
            salonService.findById(idNum);
        });
    }

    @Test
    @Order(7)
    @SneakyThrows
    public void testNewSalonOk(){
        int oldCount = salonService.count();
        Salon salon = new Salon(4, "salon service add", "adress111", "phone 1111", "1110111");
        Salon newSalon = salonService.addSalon(salon);
        Assertions.assertEquals(oldCount+1, salonService.count());
        Assertions.assertNotNull(newSalon);
        Assertions.assertEquals("salon service add", newSalon.getName());
    }

    @Test
    @Order(8)
    @SneakyThrows
    public void testNewSalonBadName(){
        Salon salon = new Salon(12, "", "address", "phone", "1110000");
        Assertions.assertThrows(SalonMalformedException.class, ()->{
           salonService.addSalon(salon);
        });
    }

    @Test
    @Order(9)
    @SneakyThrows
    public void testNewSalonBadAddress(){
        Salon salon = new Salon(12, "test", "", "phone", "1110000");
        Assertions.assertThrows(SalonMalformedException.class, ()->{
            salonService.addSalon(salon);
        });
    }

    @Test
    @Order(10)
    @SneakyThrows
    public void testNewSalonBadPhone(){
        Salon salon = new Salon(12, "test", "address", "", "1100000");
        Assertions.assertThrows(SalonMalformedException.class, ()->{
            salonService.addSalon(salon);
        });
    }

    @Test
    @Order(11)
    @SneakyThrows
    public void testNewSalonBadDaysOpen(){
        Salon salon = new Salon(12, "test", "address", "phone", "");
        Assertions.assertThrows(SalonMalformedException.class, ()->{
            salonService.addSalon(salon);
        });
    }

    @Test
    @Order(12)
    public void testNewSalonBadId(){
        Salon salon = new Salon(3, "test", "address", "number", "1111111");
        Assertions.assertThrows(SalonIdAlreadyExists.class, ()->{
            salonService.addSalon(salon);
        });
    }

    @Test
    @Order(13)
    @SneakyThrows
    public void testEditSalonIdNotExists(){
        Assertions.assertThrows(SalonNotFoundException.class, ()->{
            salonService.changeName("New name", 22222);
        });
    }

    @Test
    @Order(14)
    @SneakyThrows
    public void testEditSalonBadName(){
        Assertions.assertThrows(SalonMalformedException.class, ()->{
            salonService.changeName("", 2);
        });
    }

    @Test
    @Order(15)
    @SneakyThrows
    public void testEditSalonAllOk(){
        Assertions.assertTrue(
            salonService.changeName("New name", 2));
    }

    @Test
    @Order(16)
    public void testFindBySalonNameNotFound() {
        List<Salon> searchName = salonService.findSalonByName("SalonA");
        Assertions.assertEquals(
                1, searchName.size()
        );
    }

    @Test
    @Order(51)
    public void testStylistCount(){
        Assertions.assertEquals(4, stylistService.count());
    }

    @Test
    @Order(52)
    public void testFindAllInSalon() throws StylistNotFoundException {
        Assertions.assertEquals(2, stylistService.findAllInSalon(1).size());
    }

    @Test
    @Order(53)
    public void testSaveStylist() throws StylistNotFoundException, StylistMalformedException {
        int oldCount = stylistService.count();
        Stylist stylist = new Stylist(5, "Johnny", "091827", 150, 1);
        stylistService.save(stylist);
        Assertions.assertEquals(oldCount+1, stylistService.count());
    }

    @Test
    @Order(54)
    public void testMoveStylist() throws StylistNotFoundException, StylistMalformedException {
        int oldSalonCount = stylistService.findAllInSalon(2).size();
        int newSalonCount= stylistService.findAllInSalon(1).size();
        stylistService.moveStylist(2, 1);
        Assertions.assertEquals(0, oldSalonCount - 1);
        Assertions.assertEquals(3, newSalonCount + 1);
    }

    @Test
    @Order(55)
    public void testAverageSalary(){
        Assertions.assertEquals(200, stylistService.averageStylistSalary(1));
    }

    @Test
    @Order(55)
    public void testAllWithinSalon(){
        Assertions.assertEquals(4, stylistService.findAllWithSalon().size());
    }
}
