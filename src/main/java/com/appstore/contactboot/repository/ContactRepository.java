package com.appstore.contactboot.repository;


import java.util.List;

import com.appstore.contactboot.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource (collectionResourceRel = "contact", path = "contact")
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByFullName (@Param("fullName") String fullName);

}
