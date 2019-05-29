package com.appstore.contactboot.repository;


import java.util.List;

import com.appstore.contactboot.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    public List<Contact> findByFullNameLike(String name);

    public Contact findContactsByFullName(String name);

}
