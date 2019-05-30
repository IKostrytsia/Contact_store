package com.appstore.contactboot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.appstore.contactboot.entity.Contact;
import com.appstore.contactboot.repository.ContactRepository;
import com.appstore.contactboot.repository.PredicateHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppstoreBootApplicationTests {
	@Autowired
	ContactRepository contactRepository;

	@Autowired
	PredicateHelper predicateHelper;

	private List<Contact> filteredContacts = new ArrayList<>();

//	@Test
//	public void saveTest() {
//		Contact contact1 = new Contact();
//		contact1.setFullName("TestContact");
//		contactRepository.save(contact1);
//		Assert.assertNotNull(contactRepository.findContactsByFullName("TestContact"));
//	}

	@Test
	public void negativeTest() {
		String nameFilter = "^.*[abcdefghijklmnopqrstuvwxyz].*$";
		filterContacts (nameFilter);
		Assert.assertTrue(filteredContacts.isEmpty());
	}

	@Test
	public void positiveTest() {
		String nameFilter = "^A.*$";
		filterContacts (nameFilter);
		Assert.assertFalse(filteredContacts.isEmpty());
	}

	private void filterContacts (String nameFilter) {
		Slice<Contact> slice = null;
		Pageable pageable = PageRequest.of(0, 100);
		while (true) {
			slice = contactRepository.findAll(pageable);
			filteredContacts.addAll(slice.get().filter(predicateHelper.getContactPredicate(nameFilter)).collect(Collectors.toList()));
			if (!slice.hasNext()) {
				break;
			}
			pageable = slice.nextPageable();
		}
	}

}
