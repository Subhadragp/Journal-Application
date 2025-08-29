package com.subcodes.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        emailService.sendEmail(
                "suryaghosh1406@gmail.com",
                "Just a TestMethod",
                "Bola na ek test method hai, Subject se samajh nahi aata kya?? pura mail kholke dekhna hai inko!!");
    }
}
