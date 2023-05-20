package com.fpt.g52.carsharing.booking.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Column(name = "user_id", nullable = false)
    private String id;

    @Column(name = "user_name")
    private String name;
}

