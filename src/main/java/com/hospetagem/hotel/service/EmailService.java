package com.hospetagem.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
       private JavaMailSender javaMailSender;

    public void enviarEmail(String para, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom("gustavogabrielrodrigues123@gmail.com");
        email.setTo("gustavogabrielrodrigues123@gmail.com");
        email.setSubject(assunto);
        email.setText(mensagem);

        javaMailSender.send(email);
    }

}
