package com.appstore.contactboot.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("main")
public class DataInitFile implements ApplicationRunner {

    private ContactRepository contactDAO;

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public DataInitFile(ContactRepository personDAO) {
        this.contactDAO = personDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = contactDAO.count();

        if (count == 0) {
            Contact p1 = new Contact();

            p1.setFullName("John");

            //
            Contact p2 = new Contact();

            p2.setFullName("Smith");

            contactDAO.save(p1);
            contactDAO.save(p2);
        }

    }

}
