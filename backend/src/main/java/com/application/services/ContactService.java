package com.application.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.DTO.ContactDTO;
import com.application.model.Contact;
import com.application.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setMessage(contactDTO.getMessage());
        

        return contactRepository.save(contact);
    }
}

