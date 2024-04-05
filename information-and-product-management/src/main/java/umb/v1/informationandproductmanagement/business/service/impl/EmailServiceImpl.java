package umb.v1.informationandproductmanagement.business.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.service.interfaces.IEmailService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String recipientEmail, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("judimicamava.umb@gmail.com", "Soporte");
            helper.setTo(recipientEmail);

            helper.setSubject(subject);

            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.info("an error occurred in email send...");
            throw new ApiException("No fue posible enviar el correo electronico", 500);
        }


        mailSender.send(message);
    }
}
