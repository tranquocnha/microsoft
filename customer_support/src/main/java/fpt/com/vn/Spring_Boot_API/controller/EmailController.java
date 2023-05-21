/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.controller;

import fpt.com.vn.Spring_Boot_API.entity.EmailEntity;
import fpt.com.vn.Spring_Boot_API.model.Email;
import fpt.com.vn.Spring_Boot_API.service.EmailService;
import fpt.com.vn.Spring_Boot_API.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Email controller
 */
@RequestMapping("/api")
@RestController
public class EmailController {

    /**
     * Email service
     */
    @Autowired
    private EmailService emailService;

    /**
     * Lấy danh sách email
     *
     * @return
     */
    @GetMapping("/email")
    public ResponseEntity<List<Email>> getEmails() {
        List<EmailEntity> gmailEntities = emailService.getGmails();
        if (gmailEntities != null && gmailEntities.size() > 0) {
            List<Email> emails = new ArrayList<>();
            for (EmailEntity gmailEntity : gmailEntities) {
                emails.add(ConvertUtils.convertGmailFromGmailEntity(gmailEntity));
            }
            return new ResponseEntity<List<Email>>(emails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Gởi email
     *
     * @param email: thông tin email
     * @return Thành công / thất bại
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/send_email")
    public ResponseEntity<?> sendEmail(@RequestBody Email email) {
        boolean result = this.emailService.sendEmail(email.getFrom(),
                email.getSubject(), email.getMessage());
        System.out.println("result: " + result);
        if (result) {
            return  ResponseEntity.ok("Email send successfully.");
        } else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Email send fail.");
        }
    }
}
