package com.application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.DTO.ContactDTO;
import com.application.model.Contact;
import com.application.services.ContactService;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit-contact")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ApiResponse> handleContactForm(@RequestBody ContactDTO contactDTO) {
        try {
            // Process the incoming contact form data and save it
            Contact savedContact = contactService.saveContact(contactDTO);
            
            // Return a success response with the saved contact details
            ApiResponse response = new ApiResponse("success", "Contact form submitted successfully", savedContact);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Handle any errors and return an error response
            ApiResponse response = new ApiResponse("error", "There was an error submitting your contact form. Please try again.", null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    public class ApiResponse {
        private String status;
        private String message;
        private Object data;
    
        public ApiResponse(String status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }
    
        // Getters and Setters
        public String getStatus() {
            return status;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    
        public Object getData() {
            return data;
        }
    
        public void setData(Object data) {
            this.data = data;
        }
    }
    
}