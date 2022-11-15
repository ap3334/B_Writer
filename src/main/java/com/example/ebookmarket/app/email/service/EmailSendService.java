package com.example.ebookmarket.app.email.service;

import com.example.ebookmarket.app.email.dto.SendEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
