/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.service;

import fpt.com.vn.Spring_Boot_API.entity.EmailEntity;
import fpt.com.vn.Spring_Boot_API.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email service
 */
@Service
public class EmailService {

    /**
     * Email repository
     */
    @Autowired
    private EmailRepository gmailRepository;

    /**
     * Lấy ra danh sách email của khách hàng.
     *
     * @return danh sách email.
     */
    public List<EmailEntity> getGmails() {
        return (List<EmailEntity>) gmailRepository.findAll();
    }

    /**
     * Gởi email.
     *
     * @param subject: tiêu đề email.
     * @param message: nội dung mail.
     * @return: thành công / thất bại.
     */
    public boolean sendEmail(String from, String subject, String message)
    {

        boolean send = false;
        String senderEmail = "systemcarsharing@gmail.com"; // your gmail email id
        String senderPassword = "dgzhmhnfmtcuiohk"; // your gmail id password

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setup host and mail server
        properties.put("mail.smtp.auth", "true"); // enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable TLS-protected connection
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", "587"); // 587 is TLS port number

        // get the session object and pass username and password
        Session session = Session.getDefaultInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message
            msg.setFrom(new InternetAddress(from)); // adding sender email id to msg object
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(senderEmail)); // adding recipient to msg object
            msg.setSubject(subject); // adding subject to msg object
            msg.setText(message); // adding text to msg object

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email sent successfully.");
            send = true; // Set the "send" variable to true after successfully sending emails

        }catch(Exception e){
            System.out.println("EmailService File Error"+ e);
        }
        return send;
    }
}
