package com.appstore.contactboot.repository;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.appstore.contactboot.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class PredicateHelper {
    public Predicate<Contact> getContactPredicate(String nameFilter) {
        Pattern pattern = Pattern.compile(nameFilter);
        Predicate<Contact> contactPredicate = c -> {
            Matcher matcher = pattern.matcher(c.getFullName());
            return !matcher.matches();
        };
        return contactPredicate;
    }
}
