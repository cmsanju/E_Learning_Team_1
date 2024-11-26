package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.application.services.EnrollmentService;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

//    @PostMapping("/{Enrolleduserid}/send-email/{userEmail}")
//    public String sendEnrollmentEmail(@PathVariable String Enrolleduserid, @PathVariable String userEmail) {
//        try {
//            enrollmentService.sendEnrollmentEmail(Enrolleduserid, userEmail);
//            return "Email sent successfully!";
//        } catch (Exception e) {
//            return "Error while sending email: " + e.getMessage();
//        }
//    }

    @PostMapping("/{userid}/send-email")
    @CrossOrigin(origins = "http://localhost:4200")
    public String sendEnrollmentEmail(@PathVariable String userid) {
        try {
            enrollmentService.sendEnrollmentEmail(userid);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }

}