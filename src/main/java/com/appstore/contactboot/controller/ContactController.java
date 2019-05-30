package com.appstore.contactboot.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.appstore.contactboot.entity.Contact;
import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.service.ContactSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class ContactController {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository repo) {
        contactRepository = repo;
    }

    @Autowired
    private ContactSearchService contactSearchService;

    @RequestMapping(method = GET, value = "/contact/search/listContacts")
    public @ResponseBody
    ResponseEntity<?> getContacts() {
        List<Contact> contacts = contactRepository.findAll();
        Resources<Contact> resources = new Resources<Contact>(contacts);
        resources.add(linkTo(methodOn(ContactController.class).getContacts()).withSelfRel());
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(method = GET, value = "/contact/search/byfilter")
    public @ResponseBody
    CompletableFuture<List<Contact>> getContactsFiltered(@RequestParam(value = "nameFilter") String nameFilter) {
        return contactSearchService.findAsyncByFilter(nameFilter);
    }

}
