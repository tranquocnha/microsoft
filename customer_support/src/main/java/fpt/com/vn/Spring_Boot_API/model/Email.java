/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gmail
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private int id;
    private String from;
    private String subject;
    private String message;
}
