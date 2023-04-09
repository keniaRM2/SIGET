package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.EmailDetails;
import utez.edu.mx.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    // Se obtiene el objeto JavaMailSender a través de la inyección de dependencias
    @Autowired
    private JavaMailSender javaMailSender;

    // Se recupera el valor de la propiedad spring.mail.username del archivo application.yml
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails emailDetails) {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(sender);
            mail.setTo(emailDetails.getRecipient());
            mail.setText(emailDetails.getMsgBody());
            mail.setSubject(emailDetails.getSubject());
            javaMailSender.send(mail);
            return "Correo enviado";
        }catch (Exception e){
            return "Error al enviar correo";
        }
    }
}
