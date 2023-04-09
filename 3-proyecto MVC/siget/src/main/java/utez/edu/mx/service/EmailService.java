package utez.edu.mx.service;

import utez.edu.mx.dao.model.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
}
