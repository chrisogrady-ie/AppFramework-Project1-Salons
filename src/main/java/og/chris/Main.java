package og.chris;

import og.chris.dao.SalonDao;
import og.chris.dao.SalonDaoImpl;
import og.chris.entitites.Salon;
import og.chris.entitites.Stylist;
import og.chris.service.SalonService;
import og.chris.service.StylistService;
import og.chris.service.exceptions.SalonMalformedException;
import og.chris.service.exceptions.SalonNotFoundException;
import og.chris.service.exceptions.StylistMalformedException;
import og.chris.service.exceptions.StylistNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SalonNotFoundException, SalonMalformedException, StylistNotFoundException, StylistMalformedException {
        System.setProperty("spring.profiles.active", "dev");
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        SalonService salonService = ac.getBean(SalonService.class);
        StylistService stylistService = ac.getBean(StylistService.class);
        System.out.println(ac.getMessage("welcome", null, Locale.getDefault()));

        //Get all salons
        salonService.findAll().forEach(System.out::println);

        //Create new salon, provide all data and return it
        System.out.println("\nCreating new Salon");
        Salon newSalon = salonService.addSalon(new Salon(
                4, "Newly added Salon", "Abc street", "08100", "1111111"));
        salonService.findAll().forEach(System.out::println);

        //Get all salons by a name
        System.out.println("\nGet Salon by name - Salon A");
        System.out.println(salonService.findSalonByName("SalonA"));

        //Get a salon by a primary key
        System.out.println("\nGet Salon by id - 2");
        System.out.println(salonService.findById(2));

        //Update a salon, modifying open days
        System.out.println("\nChanging open days - Salon 2");
        salonService.changeOpenDays("1111111", 2);
        System.out.println(salonService.findById(2));

        //Delete a salon and all it's stylists
        System.out.println("\nDeleting - Salon 2");
        salonService.deleteById(2);
        salonService.findAll().forEach(System.out::println);

        //List all salons open 7 days a week
        System.out.println("\nAll Salons open 7 days");
        System.out.println(salonService.findSalonsOpenAllDays("1111111"));

        //
        //
        //
        //
        //Get all stylists in a salon
        System.out.println("\nAll stylists in salon 1");
        System.out.println(stylistService.findAllInSalon(1));

        //Add a stylist, adding them to a salon
        System.out.println("\nAdding new stylist to Salon 1");
        stylistService.save(new Stylist(5, "Johnny", "091827", 150, 1));
        System.out.println(stylistService.findAllInSalon(1));

        //Move a stylist from one salon to another
        System.out.println("\nMoving stylist from Salon 3 to 1");
        stylistService.moveStylist(4, 1);
        System.out.println(stylistService.findAllInSalon(1));

        //Delete a stylist
        System.out.println("\nDeleting stylist 1 by id");
        stylistService.deleteById(1);
        System.out.println(stylistService.findAllInSalon(1));

        //Average salary of stylists in a salon
        System.out.println("\nGetting average salary of Stylists in Salon 1");
        System.out.println(stylistService.averageStylistSalary(1));

        //list all stylists within a salon and the salons name
        System.out.println("\nFetching stylists with salon name");
        System.out.println(stylistService.findAllWithSalon());
    }
}

// http://localhost:8082
// jdbc:h2:mem:testdb