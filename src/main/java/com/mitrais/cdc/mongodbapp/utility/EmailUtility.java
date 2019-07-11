package com.mitrais.cdc.mongodbapp.utility;

import com.mitrais.cdc.mongodbapp.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Getter
@Setter
public class EmailUtility {

    private User user;

    public boolean sendEmail (String email, String token, String username, String contents, String subject) {
        log.info("Inside sendEmail1");
        Map<String, String> data = new HashMap<String, String>();
        data.put("email", email);
        data.put("token", token);
        data.put("username", username);
        data.put("subject", subject);

        try {
            if(sendEmail(data, contents)) {
                return true;
            }else {
                return false;
            }
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    public boolean sendEmail(User user, String token, String contents) {

        Map<String, String> data = new HashMap<String, String>();
        data.put("email", user.getEmail());
        data.put("token", token);


        try {
            if(sendEmail(data, contents)) {
                return true;
            }else {
                return false;
            }
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    public boolean sendEmail(Map<String, String> data, String contents) throws AddressException, MessagingException, IOException {

        log.info("Inside sendEmail2");
        String token = data.get("token");
        String email = data.get("email");
        String username = data.get("username");
        String subject = data.get("subject");

        try {
            log.info("Token-Inside sendEmail2: "+token);
            log.info("email-Inside sendEmail2: "+email);

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("cdcbootcamp@gmail.com", "CdcJavaBootcamp");
                }
            });

            Message message = new MimeMessage(session);
            /*message.setFrom(new InternetAddress(emailProperties.getEmail(), false));*/
            message.setFrom(new InternetAddress("admin@onestopclick.com", false));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(subject, "text/html");
            message.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(contents, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}