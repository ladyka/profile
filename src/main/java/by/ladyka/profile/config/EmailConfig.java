package by.ladyka.profile.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@Getter
@Setter
public class EmailConfig {
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private Integer mailPort;
    @Value("${mail.protocol}")
    private String mailProtocol;
    @Value("${mail.default-encoding}")
    private String mailEncoding;
    @Value("${mail.username}")
    private String mailUsername;
    @Value("${mail.password}")
    private String mailPassword;


    @Bean
    @Qualifier("ladyka")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl() {

            @Override
            public void send(MimeMessage...mimeMessages) throws MailException {
                Arrays.asList(mimeMessages).forEach(mimeMessage -> {
                    try {
                        mimeMessage.setFrom(new InternetAddress(mailUsername, "LADYKA.BY"));
                    } catch (MessagingException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                doSend(mimeMessages, null);
            }
        };
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);
        props.put("mail.transport.protocol", mailProtocol);
        props.put("mail.debug", "true");
        props.setProperty("mail.smtp.allow8bitmime", "true");
        props.setProperty("mail.smtps.allow8bitmime", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });
        mailSender.setSession(session);
        return mailSender;
    }
}