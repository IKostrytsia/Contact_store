package com.appstore.contactboot.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("embedded")
public class DataInit implements ApplicationRunner {

    static final int WORD_COUNT = 100;

    private ContactRepository contactDAO;

    @Autowired
    public DataInit(ContactRepository personDAO) {
        this.contactDAO = personDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

            String[] randomStrings = new String[WORD_COUNT];
            Random random = new Random();
            for(int i = 0; i < WORD_COUNT; i++)
            {
                char[] word = new char[random.nextInt(8)+3];
                for(int j = 0; j < word.length; j++)
                {
                    word[j] = (char)('a' + random.nextInt(26));
                }
                randomStrings[i] = new String(word);
                Contact contact = new Contact();
                contact.setFullName(new String(word));
                contactDAO.save(contact);
            }

        long count = contactDAO.count();

        if (count == 0) {
//            Contact p1 = new Contact();
//            p1.setFullName("Johnembedded");
//
//            Contact p2 = new Contact();
//            p2.setFullName("Smithembedded");
//
//            Contact p3 = new Contact();
//            p3.setFullName("AAname");
//
//            Contact p4 = new Contact();
//            p4.setFullName("Rtklrcvbvnb");
//
//            Contact p5 = new Contact();
//            p5.setFullName("rtwertfgewgrgfew");
//
//            Contact p6 = new Contact();
//            p6.setFullName("ghadshfsjgjdgf");
//
//            Contact p7 = new Contact();
//            p7.setFullName("fghmhryujgfvg");
//
//            Contact p8 = new Contact();
//            p8.setFullName("zbvxnxcq");
//
//            Contact p9 = new Contact();
//            p9.setFullName("Asdfafdasgasd");
//
//            Contact p10 = new Contact();
//            p10.setFullName("Aqqqqqqqqqqqqe");
//
//            contactDAO.save(p1);
//            contactDAO.save(p2);
//            contactDAO.save(p3);
//            contactDAO.save(p4);
//            contactDAO.save(p5);
//            contactDAO.save(p6);
//            contactDAO.save(p7);
//            contactDAO.save(p8);
//            contactDAO.save(p9);
//            contactDAO.save(p10);
        }

    }

}
