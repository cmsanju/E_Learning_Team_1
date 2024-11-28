package com.application.controller;
import com.application.model.MailData;
import com.application.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import com.application.model.User;
import com.application.model.Professor;
import com.application.repository.UserRepository;
import com.application.repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;



@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired private EmailService emailService;
    @Autowired private UserRepository userRepository;
    @Autowired private ProfessorRepository professorRepository;

    @PostMapping("/sendmail")
    public String
    sendMail(@RequestBody MailData details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/attachmentmail")
    public String sendMailWithAttachment(
            @RequestBody MailData details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }


    // welcome mail

    @PostMapping("/sendmail/welcome")
    public ResponseEntity<Map<String, String>> sendWelcomeMail(@RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            logger.info("Received welcome email request for userId: {}", userId);
            
            User user = userRepository.findByEmail(userId);
            if (user == null) {
                user = userRepository.findByUserid(userId);
            }
            
            if (user != null) {
                logger.info("Sending welcome email to user: {}", user.getEmail());
                MailData mailData = new MailData();
                mailData.setRecipient(user.getEmail());
                mailData.setSubject("Welcome to E-Learning!");
                mailData.setMsgBody("Dear " + user.getUsername() + ",\n\n" +
                                  "Welcome to E-Learning! We're thrilled to have you join our learning community.\n\n" +
                                  "Here's what you can look forward to:\n" +
                                  "• Access to high-quality courses from expert instructors\n" +
                                  "• Interactive learning materials and assignments\n" +
                                  "• Self-paced learning that fits your schedule\n" +
                                  "• Certificates upon course completion\n" +
                                  "• 24/7 access to course content\n\n" +
                                  "Start your learning journey today!\n\n" +
                                  "Best regards,\nE-Learning Team");
                
                String result = emailService.sendSimpleMail(mailData);
                logger.info("Email sent successfully to user: {}", user.getEmail());
                Map<String, String> response = new HashMap<>();
                response.put("message", "Email sent successfully");
                return ResponseEntity.ok(response);
            }

            Optional<Professor> professor = professorRepository.findFirstByEmail(userId);
            if (professor.isPresent()) {
                logger.info("Sending welcome email to professor: {}", professor.get().getEmail());
                MailData mailData = new MailData();
                mailData.setRecipient(professor.get().getEmail());
                mailData.setSubject("Welcome to E-Learning as Professor!");
                mailData.setMsgBody("Dear Professor " + professor.get().getProfessorname() + ",\n\n" +
                                  "Welcome to E-Learning! We're delighted to have you join our teaching community.\n\n" +
                                  "As a professor, you'll enjoy:\n" +
                                  "• Easy-to-use course creation tools\n" +
                                  "• Student progress tracking\n" +
                                  "• Interactive teaching features\n" +
                                  "• Analytics and reporting tools\n" +
                                  "• Direct communication with students\n\n" +
                                  "Ready to start creating engaging courses?\n\n" +
                                  "Best regards,\nE-Learning Team");
                
                String result = emailService.sendSimpleMail(mailData);
                logger.info("Email sent successfully to professor: {}", professor.get().getEmail());
                Map<String, String> response = new HashMap<>();
                response.put("message", "Email sent successfully");
                return ResponseEntity.ok(response);
            }
            
            logger.error("No user or professor found with ID: {}", userId);
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            logger.error("Error sending welcome email: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

