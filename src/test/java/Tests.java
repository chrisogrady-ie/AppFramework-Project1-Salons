import og.chris.Config;
import og.chris.dao.SalonDao;
import og.chris.dao.SalonDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class Tests {
    @Autowired
    ApplicationContext ac;

    @Test
    public void testGerman(){
        String mes = ac.getMessage("welcome", null, Locale.GERMAN);
        Assertions.assertEquals("wie gehts", mes);
    }

    @Test
    public void testMessagesBean(){
        Assertions.assertNotNull(ac.getBean(MessageSource.class));
    }

    @Test
    public void testSalonCount(){
        SalonDao sdao = ac.getBean(SalonDaoImpl.class);
        Assertions.assertEquals(3, sdao.count());
    }


}
