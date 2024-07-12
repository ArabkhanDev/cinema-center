package group.aist.cinema.service;

import group.aist.cinema.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    JavaMailSender mailSender;

    public void sendTicketEmail(String to, Ticket ticket) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Ticket");
        // TODO: sending qr
        message.setText("Your ticket details: " + ticket.toString());
        mailSender.send(message);
    }

}
