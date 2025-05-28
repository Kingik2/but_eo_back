package org.example.but_eo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public String sendVerificationCode(String toEmail) {
        if (!isValidEmail(toEmail)) {
            System.out.println("전송 이메일 : " + toEmail);
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }

        String code = createCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[But_Eo] 이메일 인증 코드");
        message.setText("인증 코드: " + code);
        mailSender.send(message);
        return code;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.matches(regex, email);
    }

    private String createCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}