import og.chris.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@ActiveProfiles("test")
public class LanguageTests {
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

}
