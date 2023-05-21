/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.utils;

import fpt.com.vn.Spring_Boot_API.entity.EmailEntity;
import fpt.com.vn.Spring_Boot_API.model.Email;

/**
 * Lớp convert chung
 */
public class ConvertUtils {

    /**
     * Convert từ entity sang model
     *
     * @param emailEntity email entity
     * @return email model
     */
    public static Email convertGmailFromGmailEntity(EmailEntity emailEntity) {
        Email email = new Email();
        email.setId(emailEntity.getId());
        email.setSubject(emailEntity.getSubject());
        email.setMessage(emailEntity.getMessage());
        return email;
    }
}
