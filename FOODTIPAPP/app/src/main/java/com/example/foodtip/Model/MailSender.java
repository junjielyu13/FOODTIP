package com.example.foodtip.Model;

import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    //private static Logger logger = Logger.getLogger(String.valueOf(MailSender.class));

    public static Authenticator authenticator;
    private Session session;
    private MimeMessage message;
    private Transport transport;
    private String mailHost = null;
    private String sender_username = null;
    private String sender_password = null;

    Properties properties;
    public MailSender() {
        properties = new Properties();
    }
    public void sendMail(String title, String content,List <String> receivers){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    initSmtpParams();
                    sendHtmlEmail(title,content,receivers);
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }).start();

    }
    private boolean initSmtpParams(){
        mailHost = "smtp.gmail.com";
        sender_username = "foodtipub@gmail.com";
        sender_password = "123456ub,.";

        properties.put("mail.smtp.host",mailHost);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.checkserveridentify","false");
        properties.put("mail.smtp.ssl.trust",mailHost);
        properties.put("mail.smtp.port", "465");
        authenticator = new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender_username,sender_password);
            }
        };
        session = Session.getInstance(properties,authenticator);
        message = new MimeMessage(session);
        return true;
    }

    private boolean sendHtmlEmail(String title, String htmlContent,List <String> receivers){
        try{
            InternetAddress from = new InternetAddress(sender_username);
            message.setFrom(from);

            InternetAddress [] sendTo = new InternetAddress[receivers.size()];
            for (int i = 0; i < receivers.size(); i++){
                sendTo[i] = new InternetAddress(receivers.get(i));
            }
            message.setRecipients(MimeMessage.RecipientType.TO,sendTo);

            message.setSubject(title);

            Multipart multipart = new MimeMultipart();

            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(htmlContent, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            message.setContent(multipart);
            message.saveChanges();

            transport = session.getTransport("smtp");
            transport.connect(mailHost,sender_username,sender_password);
            transport.sendMessage(message,message.getAllRecipients());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }finally {
            {
                if(transport != null){
                    try{
                        transport.close();
                    }catch (MessagingException e){
                    }
                }
            }
        }
        return true;
    }
}
