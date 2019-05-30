package com.appstore.contactboot.init;

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
        for (int i = 0; i < WORD_COUNT; i++) {
            char[] word = new char[random.nextInt(8) + 3];
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
            Contact contact = new Contact();
            contact.setFullName(new String(word));
            contactDAO.save(contact);
        }
    }
}

