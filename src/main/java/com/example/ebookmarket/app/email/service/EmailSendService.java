package com.example.ebookmarket.app.email.service;

import com.example.ebookmarket.app.email.dto.SendEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final JavaMailSender mailSender;


    public void sendForJoin(String email) {

        SendEmailDto emailDto = SendEmailDto.builder()
                .address(email)
                .title("EBOOKS - MARKET 가입 축하 이메일입니다.")
                .message("안녕하세요. EBOOKS - MARKET 가입을 축하드립니다.")
                .build();

        send(emailDto);

    }

    public String sendForFindPassword(String email) {

        String tempPassword = makeTempPassword();

        SendEmailDto emailDto = SendEmailDto.builder()
                .address(email)
                .title("EBOOKS - MARKET 임시 비밀번호 이메일입니다.")
                .message("안녕하세요. EBOOKS - MARKET 임시 비밀번호는 🔑 %s입니다.".formatted(tempPassword))
                .build();

        send(emailDto);

        return tempPassword;

    }

    private String makeTempPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "@").substring(0, 10);
    }

    private void send(SendEmailDto emailDto) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDto.getAddress());
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getMessage());
        message.setFrom("chwil2989@gmail.com");
        message.setReplyTo("chwil2989@gmail.com");
        mailSender.send(message);

    }


}
