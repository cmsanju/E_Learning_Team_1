package com.application.services;

import com.application.model.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(MailData details) {
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up the necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail: " + e.getMessage();
        }
    }

    @Override
    public String sendMailWithAttachment(MailData details) {
        try {
            // Creating a mime message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Helper for mime message to handle attachments
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            File attachment = new File(details.getAttachment());
            if (attachment.exists()) {
                FileSystemResource fileResource = new FileSystemResource(attachment);
                mimeMessageHelper.addAttachment(fileResource.getFilename(), fileResource);
            } else {
                return "Attachment file not found.";
            }

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully with Attachment.";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail with attachment: " + e.getMessage();
        }
    }
}
