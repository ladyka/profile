package by.ladyka.profile.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendJoinEmail(String email, String regToken) {
        System.out.println("/join/confirm?email="+ email + "&token=" + regToken);
    }
}
