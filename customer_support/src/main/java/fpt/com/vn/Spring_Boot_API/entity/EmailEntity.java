/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Email
 */
@Getter
@Setter
@Entity
@Table(name = "email")
public class EmailEntity implements Serializable {
    /**
     * id email
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Tiêu đề email
     */
    @Column(name = "subject")
    private String subject;

    /**
     * Nội dung email
     */
    @Column(name = "message")
    private String message;

    /**
     * Email người gởi
     */
    @Column(name = "email_from")
    private String emailFrom;
}
