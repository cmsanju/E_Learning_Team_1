package com.application.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.application.model.Contact;

public  interface ContactRepository extends JpaRepository<Contact, Long>{
    
}




