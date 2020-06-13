package by.ladyka.profile.service;

import by.ladyka.profile.config.ApplicationSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Qualifier("ladyka")
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final ApplicationSettings settings;

    public void sendJoinEmail(String email, String regToken) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setSubject("Подтверждение регистрации");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(email);
            helper.setText(buildSingInText(email, regToken), true);
            new Thread(() -> emailSender.send(message)).start();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private String buildSingInText(String email, String regToken) {
        final Locale.Builder builder = new Locale.Builder();
        final Context ctx = new Context(builder.build());
        ctx.setVariable("token", regToken);
        ctx.setVariable("siteUrl", settings.getDomain());
        ctx.setVariable("email", email);
        System.out.println("/join/confirm?email="+ email + "&token=" + regToken);
        return templateEngine.process("email/join.confirm.html", ctx);
    }
}
