package com.fpt.g52.microsoft.model;

import com.fpt.g52.microsoft.model.DTO.HelloWorkDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "hellowork")
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorkEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String context;

    public HelloWorkEntity(String context) {
        this.context = context;
    }
}
