package com.qrcheckin.qrcheckin.Services;

import com.qrcheckin.qrcheckin.Config.AppConfig;
import com.qrcheckin.qrcheckin.Exception.HomeException;
import com.qrcheckin.qrcheckin.Models.Student;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailerService {

    private final JavaMailSender mailSender;
    private final AppConfig config;

    public MailerService(JavaMailSender mailSender, AppConfig config){
        this.mailSender = mailSender;
        this.config = config;
    }

    public void sendStudentQr(Student student){
        try {
            File qrFile = new File(config.getPath() + "/src/main/resources/storage/qr_codes/" + student.getQr());

            if (!qrFile.exists()) {
                throw new IllegalArgumentException("QR file does not exist: " + qrFile.getAbsolutePath());
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(student.getEmail());
            helper.setSubject("Your QR Code");
            helper.setText("Dear " + student.getName() + ",\n\nPlease find your QR code attached.\n\nBest regards,\nQRCheckIn");
            helper.setFrom("qr-checkin@gmail.com");
            FileSystemResource file = new FileSystemResource(qrFile);
            helper.addAttachment("qr-code.png", file);

            mailSender.send(message);

        } catch (MessagingException  | IllegalArgumentException e) {
            var homeException = new HomeException(e.getMessage(),"Failed to send your QR try later");
            homeException.initCause(e);
            throw homeException;
        }
    }
}
