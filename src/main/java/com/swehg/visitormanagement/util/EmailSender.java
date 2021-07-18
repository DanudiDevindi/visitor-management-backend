package com.swehg.visitormanagement.util;

import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author hp
 */

@Component
public class EmailSender {

    private static final String from = "kavindu.swlc@gmail.com";
    private static final String password = "admin@swlc123";

    public static void send(String to,String sub,String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        try {
            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyy");
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);



//            message.setText(ms);




            message.setContent(msg,"text/html");






            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {

            System.out.println(e);

            throw new RuntimeException(e);
        }

    }
}
