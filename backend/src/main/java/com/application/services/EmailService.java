package com.application.services;


import com.application.model.MailData;

public interface EmailService {


    String sendSimpleMail(MailData details);
    String sendMailWithAttachment(MailData details);
}

