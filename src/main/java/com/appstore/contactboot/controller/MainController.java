package com.appstore.contactboot.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.appstore.contactboot.exception.ContactNotFoundException;
import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.entity.Contact;
import com.appstore.contactboot.repository.PredicateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MainController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PredicateHelper predicateHelper;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        Iterable<Contact> all = contactRepository.findAll();
        StringBuilder sb = new StringBuilder();
        all.forEach(p -> sb.append(p.getFullName() + "<br>"));
        return sb.toString();
    }

    @GetMapping("/contact")
    public List<Contact> getContact(@RequestParam(value = "nameFilter") String nameFilter) {
            List<Contact> filteredContacts = new ArrayList<>();

//            Pattern pattern = Pattern.compile(nameFilter);
//            Predicate<Contact> contactPredicate = c -> {
//                Matcher matcher = pattern.matcher(c.getFullName());
//                return !matcher.matches();
//            };

            Slice<Contact> slice = null;
            Pageable pageable = PageRequest.of(0, 100);
            while (true) {
                slice = contactRepository.findAll(pageable);
                int number = slice.getNumber();
                int numberOfElements = slice.getNumberOfElements();
                int size = slice.getSize();
                System.out.printf("slice info - page number %s, numberOfElements: %s, size: %s%n", number, numberOfElements, size);
//                filteredContacts.addAll(slice.get().filter(contactPredicate).collect(Collectors.toList()));
                filteredContacts.addAll(slice.get().filter(predicateHelper.getContactPredicate(nameFilter)).collect(Collectors.toList()));
                if (!slice.hasNext()) {
                    break;
                }
                pageable = slice.nextPageable();
            }

            if (filteredContacts.isEmpty()) throw new ContactNotFoundException("No one Contact matches nameFilter pattern");
            return filteredContacts;
        }
}

