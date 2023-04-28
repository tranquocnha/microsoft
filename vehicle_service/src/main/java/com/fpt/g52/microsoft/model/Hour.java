package com.fpt.g52.microsoft.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "hour")
@Data
public class Hour {

    @Id
    private int hourId;
    private String hour;
}
