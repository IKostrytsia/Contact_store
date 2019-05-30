package com.appstore.contactboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.appstore.contactboot.entity.Contact;
import com.appstore.contactboot.exception.ContactNotFoundException;
import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.repository.PredicateHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ContactSearchService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PredicateHelper predicateHelper;

    @Async
    public CompletableFuture<List<Contact>> findAsyncByFilter (String nameFilter) {
        List<Contact> filteredContacts = new ArrayList<>();

        Slice<Contact> slice = null;
        Pageable pageable = PageRequest.of(0, 100);
        while (true) {
            slice = contactRepository.findAll(pageable);
            int number = slice.getNumber();
            int numberOfElements = slice.getNumberOfElements();
            int size = slice.getSize();
            System.out.printf("slice info - page number %s, numberOfElements: %s, size: %s%n", number, numberOfElements, size);
            filteredContacts.addAll(slice.get().filter(predicateHelper.getContactPredicate(nameFilter)).collect(Collectors.toList()));
            if (!slice.hasNext()) {
                break;
            }
            pageable = slice.nextPageable();
        }

        if (filteredContacts.isEmpty()) {
            throw new ContactNotFoundException("No one Contact matches nameFilter pattern");
        }
        return CompletableFuture.completedFuture(filteredContacts);
    }
}
